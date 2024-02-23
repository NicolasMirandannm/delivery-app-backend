package delivery.deliveryapp.infra.config.filesystem;

import delivery.deliveryapp.shared.exceptions.InfraException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        if (properties.getLocation().trim().isEmpty()) {
            InfraException.throwException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            InfraException.throwException("Could not initialize storage");
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                InfraException.throwException("Failed to store empty file.");
            }

            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(Objects.requireNonNull(file.getOriginalFilename()))
            ).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                InfraException.throwException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            InfraException.throwException("Failed to store file. -> " + e.getMessage());
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                InfraException.throwException("Could not read file: " + filename);
            }
            return null;
        } catch (Exception e) {
            InfraException.throwException(e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] loadFile(String filename) {
        Resource file = loadAsResource(filename);

        if (file == null || !file.exists()) {
            InfraException.throwException("file not fount");
        }
        try {
            InputStream in = file.getInputStream();
            return in.readAllBytes();
        } catch (Exception e) {
            InfraException.throwException(e.getMessage());
            return null;
        }
    }

    private Path load(String filename) {
        return rootLocation.resolve(filename);
    }
}
