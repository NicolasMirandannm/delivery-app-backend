package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.fileupload.UploadFile;
import delivery.deliveryapp.application.product.creation.dto.ComplementDto;
import delivery.deliveryapp.application.product.creation.dto.ComplementItemDto;
import delivery.deliveryapp.application.product.creation.dto.ProductDto;
import delivery.deliveryapp.application.product.creation.dto.ServingSizeDto;
import static org.junit.jupiter.api.Assertions.*;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;


import java.util.List;

public class CreateProductServiceTest {
 
  @InjectMocks
  private CreateProductService createProductService;
  
  @Mock
  private UploadFile uploadFile;
  
  @Mock
  private ProductRepository productRepository;
  
  private ServingSizeDto servingSizeDto;
  private ProductDto productDto;
  private ComplementDto complementDto;
  private ComplementItemDto complementItem;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    servingSizeDto = new ServingSizeDto("Unique", "unique size of a product", 10.0);
    productDto = new ProductDto(
      "name",
      "description",
      UniqueIdentifier.create().value(),
      false,
      List.of(servingSizeDto), List.of()
    );
    var imageFile = new MockMultipartFile("image", "image.jpg", "image/jpeg", "image".getBytes());
    productDto.setImage(imageFile);
    
    complementItem = new ComplementItemDto("Banana", MeasurementType.GRAM,20.0);
    complementDto = new ComplementDto("fruits", List.of(new ComplementDto.AmountBySize("Unique", 2)), List.of(complementItem));
    
    
    Mockito.when(uploadFile.upload(productDto.getImage())).thenReturn("key");
  }
  
  @Test
  void should_create_a_product_without_custom_complements() {
    var productCreated = createProductService.create(productDto);
    
    assertEquals(productDto.getName(), productCreated.getName());
    assertEquals(productDto.getDescription(), productCreated.getDescription());
    assertEquals(productDto.getProductCategoryId(), productCreated.getProductCategoryId());
    assertEquals(productCreated.getIsCustomizable(), false);
    assertEquals(productCreated.getIsActived(), true);
  }
  
  @Test
  void should_throw_an_exception_when_product_dto_is_null() {
    var expectedMessage = "cannot create a product without product creation data.";
   
    var exception = assertThrows(ApplicationException.class, () -> createProductService.create(null));
   
    assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_get_a_image_key_for_product_creation() {
    var productCreated = createProductService.create(productDto);
    
    assertEquals("key", productCreated.getImageURI());
  }
  
  @Test
  void should_throw_an_exception_when_product_has_serving_sizes_with_the_same_name() {
    productDto.setServingSizes(List.of(servingSizeDto, servingSizeDto));
    
    var expectedMessage = "A product cannot have two serving sizes with the same name";
    
    var exception = assertThrows(ApplicationException.class, () -> createProductService.create(productDto));
    
    assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_product_has_active_complements_and_have_differents_sizes_of_serving() {
    complementDto.getAmountBySize().get(0).setSizeDescription("Different");
    productDto.setHasActiveComplements(true);
    productDto.setComplements(List.of(complementDto));
    productDto.setServingSizes(List.of(servingSizeDto));

    var expectedMessage = "A complement must have the same sizes as the product";

    var exception = assertThrows(ApplicationException.class, () -> createProductService.create(productDto));

    assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_product_complements_has_sizes_duplicateds() {
    complementDto = new ComplementDto("fruits", List.of(
      new ComplementDto.AmountBySize("Unique", 2),
      new ComplementDto.AmountBySize("Unique", 2)
    ), List.of(complementItem));
    
    productDto.setHasActiveComplements(true);
    productDto.setComplements(List.of(complementDto));
    productDto.setServingSizes(List.of(servingSizeDto));
    
    var expectedMessage = "A complement must have the same amount of sizes as the product";
    
    var exception = assertThrows(ApplicationException.class, () -> createProductService.create(productDto));
    
    assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_product_has_active_complements_and_complement_list_is_null() {
    productDto.setHasActiveComplements(true);
    productDto.setComplements(null);
    productDto.setServingSizes(List.of(servingSizeDto));
    
    var expectedMessage = "A product with active complements must have at least one complement";
    
    var exception = assertThrows(ApplicationException.class, () -> createProductService.create(productDto));
    
    assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_create_a_serving_sizes_with_complements_and_size_from_dto() {
    productDto.setHasActiveComplements(true);
    productDto.setComplements(List.of(complementDto));
    productDto.setServingSizes(List.of(servingSizeDto));
    
    var productCreated = createProductService.create(productDto);
    var servingSizeCreated = productCreated.getServingSizes().get(0);;
    
    assertEquals(servingSizeDto.getName(), servingSizeCreated.getName());
    assertEquals(servingSizeDto.getDescription(), servingSizeCreated.getDescription());
    assertEquals(servingSizeDto.getPrice(), servingSizeCreated.getPrice());
  }
  
  @Test
  void should_map_complement_item_from_dto() {
    productDto.setHasActiveComplements(true);
    productDto.setComplements(List.of(complementDto));
    productDto.setServingSizes(List.of(servingSizeDto));
    
    var productCreated = createProductService.create(productDto);
    var complementCreated = productCreated.getServingSizes().get(0).getComplementCategories().get(0);;
    
    assertEquals(complementDto.getAmountBySize().get(0).getAmountAvailable(), complementCreated.getAmountAvailable());
    assertEquals(complementDto.getCategoryName(), complementCreated.getCategoryName());
    assertEquals(1, complementCreated.getComplements().size());
  }
  
  @Test
  void should_save_the_product_created_in_repository() {
    var createdProduct = createProductService.create(productDto);
    
    Mockito.verify(productRepository, Mockito.times(1)).create(createdProduct);
  }
}
