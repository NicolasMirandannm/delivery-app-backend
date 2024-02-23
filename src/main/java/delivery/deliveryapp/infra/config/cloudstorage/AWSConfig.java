package delivery.deliveryapp.infra.config.cloudstorage;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import software.amazon.awssdk.auth.credentials.*;

import java.net.URI;

@Configuration
public class AWSConfig {
 
  @Value("${cloudflare.r2.accessKey}")
  private String accessKey;
 
  @Value("${cloudflare.r2.secretKey}")
  private String secretKey;
  
  @Value("${cloudflare.r2.cloudflareAccountId}")
  private String cloudflareAccountId;
  
  public AWSCredentials credentials() {
    
    return new BasicAWSCredentials(
      accessKey,
      secretKey
    );
  }
  
  public EndpointConfiguration getEndPointConfiguration() {
    var host = "https://" + cloudflareAccountId + ".r2.cloudflarestorage.com";
    return new EndpointConfiguration(host, "auto");
  }
  
  @Bean
  public AmazonS3 s3client() {
    return AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials()))
      .withEndpointConfiguration(getEndPointConfiguration())
      .build();
  }
  
  @Bean
  public S3Presigner s3Presigner() {
    return S3Presigner
      .builder()
      .region(Region.of("auto"))
      .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
      .endpointOverride(URI.create("https://" + cloudflareAccountId + ".r2.cloudflarestorage.com"))
      .build();
  }
}
