package delivery.deliveryapp.portsAndAdapters.integration;

import delivery.deliveryapp.domain.builder.*;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductDetailsIntegrationTest {
  
  @Autowired
  MockMvc mockMvc;
  
//  @Autowired
//  ProductRepository productRepository;
  
  @BeforeEach
  void setup() {
    var product = ProductBuilder
      .aProduct()
      .withId(UniqueIdentifier.create())
      .withName("Ice cream")
      .withIsCustomizable(true)
      .withDescription("A delicious ice cream")
      .withImageUri("images/ice-cream.jpg")
      .withServingSizes(List.of(
        ServingSizeBuilder
          .aServingSize()
          .withId()
          .withName("Small")
          .withDescription("A small ice cream")
          .withActivedComplements(true)
          .withComplementCategories(List.of(
            ComplementCategory.create("Toppings", 2, List.of(
              ComplementItem.create("Chocolate", UniqueIdentifier.create(), UnitOfMeasurement.create(MeasurementType.MILILITER, 10.0))
            ))
          ))
          .build()
          )
      ).build();
    //todo salvar produto no repositorio e implementar teste
  }
  
  @AfterEach
  void tearDown() {
    //todo deletar produtos
  }
  
}
