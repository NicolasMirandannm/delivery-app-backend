package delivery.deliveryapp.infra.config.cloudstorage;

import com.amazonaws.services.s3.model.S3Object;

import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.time.Duration;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class R2StorageService implements R2Storage {
  
  private final AWSConfig awsConfig;
  
  @Override
  public void putObject(String bucketName, String key, String content) {
    //todo create a parser of imafe multipart file to file for put object -> https://issackpaul95.medium.com/aws-s3-bucket-file-upload-with-spring-boot-719ede4e7f78
    awsConfig.s3client().putObject(bucketName, key, new File(content));
  }
  
  @Override
  public String getObject(String bucketName, String key) {
    
    try (S3Presigner presigner = awsConfig.s3Presigner()) {
      var objectRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(key)
        .build();
      
      var presignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(10))
        .getObjectRequest(objectRequest)
        .build();
      
      var presignedRequest = presigner.presignGetObject(presignRequest);
      
      return presignedRequest.url().toExternalForm();
    }
    catch (Exception e) {
      InfraException.throwException("Error getting object from R2 -> " + e.getMessage());
      return null;
    }
  }
}
