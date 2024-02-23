package delivery.deliveryapp.infra.filestorage;

import com.amazonaws.services.s3.model.S3Object;

public interface R2Storage {
  void putObject(String bucketName, String key, String content);
  String getObject(String bucketName, String key);
}
