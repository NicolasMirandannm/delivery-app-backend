package delivery.deliveryapp.infra.rest.files;

import delivery.deliveryapp.infra.config.filesystem.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("files")
public class FileRestController {
    private final StorageService storageService;
    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        byte[] imageBytes = storageService.loadFile(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}