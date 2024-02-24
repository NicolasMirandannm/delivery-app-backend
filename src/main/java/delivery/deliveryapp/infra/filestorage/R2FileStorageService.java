package delivery.deliveryapp.infra.filestorage;

import org.springframework.web.multipart.MultipartFile;

public interface R2FileStorageService<T> {
  void store(String key, MultipartFile content);
  T getObject(String key);
}
