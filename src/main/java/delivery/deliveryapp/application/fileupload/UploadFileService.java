package delivery.deliveryapp.application.fileupload;

import delivery.deliveryapp.infra.filestorage.R2FileStorageService;
import delivery.deliveryapp.shared.UniqueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadFileService implements UploadFile {
  
  private final R2FileStorageService<String> fileStorageService;
  
  @Override
  public String upload(MultipartFile file) {
    var fileName = UniqueIdentifier.create().value() + "_" + file.getOriginalFilename();
    fileStorageService.store(fileName, file);
    return fileName;
  }
}
