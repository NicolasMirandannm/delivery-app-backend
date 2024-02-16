package delivery.deliveryapp.application.product.details;

import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.domain.services.productDetails.ProductDetailsDomainService;
import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.exceptions.http.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailsAppServiceImpl implements ProductDetailsAppService {
  
  private final ProductRepository productRepository;
  private final ProductDetailsDomainService productDetailsDomainService;
  
  @Override
  public ProductDetailsDto getProductDetailsBy(String productId) {
    ApplicationException.whenIsNull(productId, "Product id is required.");
    
    var id = UniqueIdentifier.createFrom(productId);
    var product = productRepository.findBy(id);
    
    if (product == null) {
      throw new NotFoundException("Product not found.");
    }
    return productDetailsDomainService.detailBy(product);
  }
}
