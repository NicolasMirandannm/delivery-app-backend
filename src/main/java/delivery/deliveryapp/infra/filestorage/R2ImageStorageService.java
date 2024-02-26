package delivery.deliveryapp.infra.filestorage;

import ch.qos.logback.core.util.FileUtil;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import delivery.deliveryapp.infra.cache.ImageStorageCacheRepository;
import delivery.deliveryapp.infra.cache.entities.ImageStorageCache;
import delivery.deliveryapp.infra.config.cloudstorage.AWSConfig;
import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class R2ImageStorageService implements R2FileStorageService<String> {
  
  @Value("${r2.storage.image-bucket}")
  private String bucketName;
  
  private final AWSConfig awsConfig;
  private final ImageStorageCacheRepository imageStorageCache;
  
  @Override
  public void store(String key, MultipartFile content) {
    try {
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(content.getSize());
      metadata.setContentType(content.getContentType());
      
      awsConfig.s3client().putObject(new PutObjectRequest(bucketName, key, content.getInputStream(), metadata)
        .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (Exception e) {
      InfraException.throwException("Error storing object in R2 -> " + e.getMessage());
    }
  }
  
  @Override
  public String getObject(String key) {
    var imageInCache = imageStorageCache.findById(key);
    if (imageInCache.isPresent())
      return imageInCache.get().getUrl();
    
    try (S3Presigner presigner = awsConfig.s3Presigner()) {
      var ttl = Duration.ofMinutes(30);
      var objectRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();
      
      var presignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(ttl)
        .getObjectRequest(objectRequest)
        .build();
      
      var presignedRequest = presigner.presignGetObject(presignRequest);
      var presignedUrl = presignedRequest.url().toExternalForm();
      
      imageStorageCache.save(new ImageStorageCache(key, presignedUrl, ttl.toSeconds()));
      return presignedUrl;
    }
    catch (Exception e) {
      InfraException.throwException("Error getting object from R2 -> " + e.getMessage());
      return null;
    }
  }
}
