package delivery.deliveryapp.infra.database.mapper.product;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.infra.database.repository.mapper.implementations.product.ComplementItemMapper;
import delivery.deliveryapp.infra.database.schemas.ComplementItemSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ComplementItemMapperTest {
  @InjectMocks
  ComplementItemMapper complementItemMapper;
  
  ComplementItem complementItem;
  
  ComplementItemSchema complementItemSchema;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    complementItem = ComplementItem.create(
      UniqueIdentifier.create(),
      "banana",
      UniqueIdentifier.create(),
      UnitOfMeasurement.create(MeasurementType.GRAM, 50.0)
    );
    
    complementItemSchema = new ComplementItemSchema(
      complementItem.getIdValue(),
      complementItem.getFeedstockId().value(),
      "banana",
      MeasurementType.GRAM,
      50.0
    );
  }
  
  @Test
  void should_map_a_complement_item_schema_to_domain_entity() {
    var complementItemMapped = complementItemMapper.toDomain(complementItemSchema);
    
    Assertions.assertEquals(complementItem, complementItemMapped);
  }
  
  @Test
  void should_map_a_complement_item_to_schema() {
    var complementItemSchemaMapped = complementItemMapper.toPersistence(complementItem);
    
    Assertions.assertEquals(complementItemSchema, complementItemSchemaMapped);
  }
  
  @Test
  void should_throw_an_exception_when_complement_item_is_null() {
    var expectedMessage = "could not map a complement item entity to schema of persistence.";
    
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      complementItemMapper.toPersistence(null);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_complement_item_schema_is_null() {
    var expectedMessage = "could not map a complement item schema to domain entity.";
    
    Exception exception = Assertions.assertThrows(InfraException.class, () -> {
      complementItemMapper.toDomain(null);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
}
