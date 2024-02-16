package delivery.deliveryapp.portsAndAdapters.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.builder.ServingSizeBuilder;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.domain.services.productDetails.dto.ComplementCategoryDto;
import delivery.deliveryapp.domain.services.productDetails.dto.PriceDto;
import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;
import delivery.deliveryapp.domain.services.productDetails.dto.SizeDto;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import delivery.deliveryapp.shared.exceptions.http.NotFoundException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductDetailsIntegrationTest {
  
  @Autowired
  MockMvc mockMvc;
  
  @Autowired
  ObjectMapper objectMapper;
  
  @Autowired
  ProductRepository productRepository;
  
  UniqueIdentifier productId;
  
  @BeforeEach
  void setup() {
    var product = ProductBuilder
      .aProduct()
      .withId(UniqueIdentifier.create())
      .withName("Ice cream")
      .withIsCustomizable(true)
      .withDescription("200ml of a delicious ice cream")
      .withImageUri("images/ice-cream.jpg")
      .withServingSizes(List.of(
        ServingSizeBuilder
          .aServingSize()
          .withId()
          .withName("Unique")
          .withDescription("200ML of ice cream.")
          .withPrice(15.0)
          .withActivedComplements(true)
          .withComplementCategories(List.of(
            ComplementCategory.create("Toppings", 2, List.of(
              ComplementItem.create("Chocolate", UniqueIdentifier.create(), UnitOfMeasurement.create(MeasurementType.MILILITER, 10.0)),
              ComplementItem.create("Strawberry", UniqueIdentifier.create(), UnitOfMeasurement.create(MeasurementType.MILILITER, 10.0))
            ))
          ))
          .build()
          )
      ).build();
    
    productId = product.getId();
    productRepository.create(product);
  }
  
  @AfterEach
  void tearDown() {
    productRepository.deleteBy(productId);
  }
  
  @Test
  void should_return_product_details() throws Exception {
    var productId = this.productId.value();
    var complementCategories = List.of(
      new ComplementCategoryDto("Toppings", List.of("Chocolate", "Strawberry"), List.of(
        new SizeDto("Unique", 2)
      ))
    );
    var prices = List.of(
      new PriceDto("Unique", 15.0)
    );
    var detailedProductJson = objectMapper.writeValueAsString(new ProductDetailsDto(productId, "Ice cream", "200ml of a delicious ice cream", "images/ice-cream.jpg", complementCategories, prices));
  
    mockMvc.perform(MockMvcRequestBuilders.get("/product/detail/{productId}", productId))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().json(detailedProductJson));
  }
  
  @Test
  void should_return_not_found_when_product_id_doesnt_match_any_product() throws Exception {
    mockMvc
      .perform(MockMvcRequestBuilders.get("/product/detail/{productId}", UniqueIdentifier.create().value()).content("application/json"))
      .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
      .andExpect(result -> assertEquals("Product not found.", result.getResolvedException().getMessage()));
  }
  
  @Test
  void should_return_not_found_when_product_id_in_request_is_null() throws Exception {
    mockMvc
      .perform(MockMvcRequestBuilders.get("/product/detail/{productId}", "").content("application/json"))
      .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
  
  @Test
  void should_return_bad_request_when_product_id_has_a_invalid_format() throws Exception {
    mockMvc
      .perform(MockMvcRequestBuilders.get("/product/detail/{productId}", "invalid id").content("application/json"))
      .andExpect(result -> assertTrue(result.getResolvedException() instanceof DomainException))
      .andExpect(result -> assertEquals("UUID -> invalid id <- invalid.", result.getResolvedException().getMessage()));
  }
}
