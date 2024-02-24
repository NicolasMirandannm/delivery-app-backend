package delivery.deliveryapp.application.fileupload;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {
    String upload(MultipartFile file);
}
