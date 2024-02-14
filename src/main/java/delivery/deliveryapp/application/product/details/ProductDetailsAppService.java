package delivery.deliveryapp.application.product.details;

import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;

public interface ProductDetailsAppService {
    ProductDetailsDto getProductDetailsBy(String productId);
}
