package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateComplementCategoryDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateComplementItemDto;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class CreateComplementCategoryTest {
  
  @InjectMocks
  CreateComplementCategory createComplementCategory;
  
  CreateComplementCategoryDto complementCategoryDto;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    var feedstockId = UniqueIdentifier.create().value();
    var complementItemDto = new CreateComplementItemDto("Banana", feedstockId, MeasurementType.GRAM, 50.0);
    complementCategoryDto = new CreateComplementCategoryDto("Fruits", 5, List.of(complementItemDto));
    
  }
  
  @Test
  void should_creata_a_complement_category() {
    var complementCategory = createComplementCategory.create(complementCategoryDto);
    
    Assertions.assertNotNull(complementCategory.getId());
    Assertions.assertEquals(complementCategoryDto.getCategoryName(), complementCategory.getCategoryName());
    Assertions.assertEquals(complementCategoryDto.getAmountAvailable(), complementCategory.getAmountAvailable());
    Assertions.assertEquals(complementCategoryDto.getComplements().size(), complementCategory.getComplements().size());
  }
  
  @Test
  void should_throw_an_exception_when_create_complement_category_dto_is_null() {
    var expectedMessage = "could not create a complement category because createComplementCategoryDto is null.";
    
    Exception exception = Assertions.assertThrows(ApplicationException.class, () -> {
      createComplementCategory.create(null);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_create_complement_item_list_is_null() {
    var expectedMessage = "could not create a complement category because complement item list is null.";
    
    Exception exception = Assertions.assertThrows(ApplicationException.class, () -> {
      complementCategoryDto.setComplements(null);
      createComplementCategory.create(complementCategoryDto);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
}
