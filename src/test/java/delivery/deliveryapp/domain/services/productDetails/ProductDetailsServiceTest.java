package delivery.deliveryapp.domain.services.productDetails;

import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.services.productDetails.dto.ComplementCategoryDto;
import delivery.deliveryapp.domain.services.productDetails.dto.PriceDto;
import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;
import delivery.deliveryapp.domain.services.productDetails.dto.SizeDto;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsServiceTest {
  
  @InjectMocks
  private ProductDetailsService productDetailsService;
  
  private Product product;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    var unitOfMeasure = UnitOfMeasurement.create(MeasurementType.GRAM, 100.0);
    var complementItensCategory1 = List.of(
      ComplementItem.create(UniqueIdentifier.create(), "banana", UniqueIdentifier.create(), unitOfMeasure),
      ComplementItem.create(UniqueIdentifier.create(), "mango", UniqueIdentifier.create(), unitOfMeasure),
      ComplementItem.create(UniqueIdentifier.create(), "grape", UniqueIdentifier.create(), unitOfMeasure)
    );
    var complementItensCategory2 = List.of(
      ComplementItem.create(UniqueIdentifier.create(), "chocolate", UniqueIdentifier.create(), unitOfMeasure),
      ComplementItem.create(UniqueIdentifier.create(), "choco balls", UniqueIdentifier.create(), unitOfMeasure)
    );
    
    var smallSize = ServingSize.createNew(
      "small",
      "small size",
      true,
      10.0,
      List.of(
        ComplementCategory.create(
          UniqueIdentifier.create(),
          "category 1",
          5,
          complementItensCategory1
        ),
        ComplementCategory.create(
          UniqueIdentifier.create(),
          "category 2",
          3,
          complementItensCategory2
        )
      ),
      new ArrayList<>()
    );
    
    var largeSize = ServingSize.createNew(
      "large",
      "large size",
      true,
      15.0,
      List.of(
        ComplementCategory.create(
          UniqueIdentifier.create(),
          "category 1",
          7,
          complementItensCategory1
        ),
        ComplementCategory.create(
          UniqueIdentifier.create(),
          "category 2",
          5,
          complementItensCategory2
        )
      ),
      new ArrayList<>()
    );
    
    product = ProductBuilder
      .aProduct()
      .withName("Ice cream")
      .withDescription("Ice cream description")
      .withImageUri("imageUrl")
      .withServingSizes(List.of(smallSize, largeSize))
      .build();
  }
  
  @Test
  void should_detail_a_product_item_with_complement_category_in_sizes() {
    var productId = product.getIdValue();
    var complementCategories = List.of(
      new ComplementCategoryDto("category 1", List.of("banana", "mango", "grape"), List.of(
        new SizeDto("small", 5),
        new SizeDto("large", 7)
      )),
      new ComplementCategoryDto("category 2", List.of("chocolate", "choco balls"), List.of(
        new SizeDto("small", 3),
        new SizeDto("large", 5)
      ))
    );
    var prices = List.of(
      new PriceDto("small", 10.0),
      new PriceDto("large", 15.0)
    );
    var detailedProductExpected = new ProductDetailsDto(productId, "Ice cream", "Ice cream description", "imageUrl", complementCategories, prices);
    
    var detailedProduct = productDetailsService.detailBy(product);
    
    Assertions.assertEquals(detailedProductExpected, detailedProduct); // todo ajustar
  }
  
  @Test
  void should_throw_an_exception_when_product_is_null() {
    var expectedMessage = "could not detail a null product.";
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> productDetailsService.detailBy(null));
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_product_sizes_is_null() {
    var expectedMessage = "could not detail a product with a empty sizes.";
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      product.setServingSizes(null);
      productDetailsService.detailBy(product);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_complement_categories_in_sizes_is_null() {
    var expectedMessage = "could not detail a product with a null complement categories in sizes.";
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      product.getServingSizes().get(0).setComplementCategories(null);
      productDetailsService.detailBy(product);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_an_exception_when_serving_sizes_is_empty() {
    var expectedMessage = "could not detail a product with a empty sizes.";
    
    Exception exception = Assertions.assertThrows(DomainException.class, () -> {
      product.setServingSizes(new ArrayList<>());
      productDetailsService.detailBy(product);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
}
