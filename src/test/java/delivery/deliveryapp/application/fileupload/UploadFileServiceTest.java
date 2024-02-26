package delivery.deliveryapp.application.fileupload;

import delivery.deliveryapp.infra.filestorage.R2FileStorageService;
import delivery.deliveryapp.shared.UniqueIdentifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileServiceTest {
  
  @InjectMocks
  private UploadFileService uploadFileService;
  
  @Mock
  private R2FileStorageService<String> fileStorageService;
  
  private MultipartFile file;
  private UniqueIdentifier fileId;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    file = new MockMultipartFile("file", "file.txt", "text/plain", "file content".getBytes());
    fileId = UniqueIdentifier.create();
    try {
      MockedStatic<UniqueIdentifier> uniqueIdentifierMock = Mockito.mockStatic(UniqueIdentifier.class);
      uniqueIdentifierMock.when(UniqueIdentifier::create).thenReturn(fileId);
    } catch (Exception ignored) {}
  }
  
  @AfterEach
  void tearDown() {
    Mockito.framework().clearInlineMocks();
  }

  @Test
  void should_create_a_unique_identifier_for_the_uploaded_file() {
    var fileNameExpected = fileId.value() + "_file.txt";

    var uploadedFileName = uploadFileService.upload(file);

    Assertions.assertEquals(fileNameExpected, uploadedFileName);
  }

  @Test
  void should_store_the_uploaded_file() {
    var fileNameExpected = fileId.value() + "_file.txt";

    uploadFileService.upload(file);

    Mockito.verify(fileStorageService).store(fileNameExpected, file);
  }
}
