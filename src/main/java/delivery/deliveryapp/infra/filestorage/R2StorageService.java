package delivery.deliveryapp.infra.filestorage;

import delivery.deliveryapp.infra.cache.ImageStorageCacheRepository;
import delivery.deliveryapp.infra.cache.entities.ImageStorageCache;
import delivery.deliveryapp.infra.config.cloudstorage.AWSConfig;
import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class R2StorageService implements R2Storage {
  
  private final AWSConfig awsConfig;
  private final ImageStorageCacheRepository imageStorageCache;
  
  @Override
  public void putObject(String bucketName, String key, String content) {
    //todo create a parser of imafe multipart file to file for put object -> https://issackpaul95.medium.com/aws-s3-bucket-file-upload-with-spring-boot-719ede4e7f78
    awsConfig.s3client().putObject(bucketName, key, new File(content));
  }
  
  @Override
  public String getObject(String bucketName, String key) {
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
