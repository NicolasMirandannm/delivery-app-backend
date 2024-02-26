package delivery.deliveryapp.application.product.details;

import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.domain.services.productDetails.ProductDetailsDomainService;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.exceptions.http.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProductDetailsAppServiceTest {
  
  @InjectMocks
  ProductDetailsAppServiceImpl productDetailsService;
  
  @Mock
  ProductRepository productRepository;
  
  @Mock
  ProductDetailsDomainService productDetailsDomainService;
  
  UniqueIdentifier productIdentifier;
  Product product;
  
  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    
    productIdentifier = UniqueIdentifier.create();
    product = ProductBuilder
      .aProduct()
      .withId(productIdentifier)
      .build();
    
    Mockito.when(productRepository.findBy(productIdentifier)).thenReturn(product);
  }
  
  @Test
  void should_find_product_by_id() {
    String productId = this.productIdentifier.value();
    
    productDetailsService.getProductDetailsBy(productId);
    
    Mockito.verify(productRepository).findBy(this.productIdentifier);
  }
  
  @Test
  void should_detail_the_product() {
    String productId = this.productIdentifier.value();
    
    productDetailsService.getProductDetailsBy(productId);
    
    Mockito.verify(productDetailsDomainService).detailBy(product);
  }
  
  @Test
  void should_throw_exception_when_product_not_found() {
    String productId = this.productIdentifier.value();
    Mockito.when(productRepository.findBy(this.productIdentifier)).thenReturn(null);
    var expectedMessage = "Product not found.";
    
    Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
      productDetailsService.getProductDetailsBy(productId);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
  
  @Test
  void should_throw_exception_when_product_id_is_null() {
    var expectedMessage = "Product id is required.";
    
    Exception exception = Assertions.assertThrows(ApplicationException.class, () -> {
      productDetailsService.getProductDetailsBy(null);
    });
    
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }
}
