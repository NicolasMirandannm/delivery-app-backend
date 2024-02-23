package delivery.deliveryapp.infra.database.mapper.product;

import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.repository.mapper.implementations.product.ComplementCategoryMapper;
import delivery.deliveryapp.infra.database.schemas.ComplementCategorySchema;
import delivery.deliveryapp.infra.database.schemas.ComplementItemSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ComplementCategoryMapperTest {
  
  @InjectMocks
  ComplementCategoryMapper complementCategoryMapper;
  
  @Mock
  Mapper<ComplementItem, ComplementItemSchema> complementItemMapper;
  
  ComplementCategory complementCategory;
  
  ComplementCategorySchema complementCategorySchema;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    complementCategory = ComplementCategory.create(
      UniqueIdentifier.create(),
      "Fruits",
      5,
      new ArrayList<ComplementItem>()
    );
    
    complementCategorySchema = new ComplementCategorySchema(
      complementCategory.getIdValue(),
      "Fruits",
      5,
      new ArrayList<ComplementItemSchema>()
    );
  }
  
  @Test
  void should_map_complement_category_to_schema_of_persistence() {
    var complementCategorySchemaMapped = complementCategoryMapper.toPersistence(complementCategory);
    
    Assertions.assertEquals(complementCategorySchema, complementCategorySchemaMapped);
  }
  
  @Test
  void should_map_complement_category_schema_to_domain_entity() {
    var complementCategoryMapped = complementCategoryMapper.toDomain(complementCategorySchema);
    
    Assertions.assertEquals(complementCategory, complementCategoryMapped);
  }
  
  @Test
  void should_throw_an_exception_when_complement_category_schema_is_null_in_map_to_domain() {
    var expectedMessage = "could not map null complement category schema to domain.";
    
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      complementCategoryMapper.toDomain(null);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_complement_category_is_null_in_map_to_persistence() {
    var expectedMessage = "could not map null complement category to schema of persistence.";
    
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      complementCategoryMapper.toPersistence(null);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
}
