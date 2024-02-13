package delivery.deliveryapp.domain.services.productDetails;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.services.productDetails.dto.ComplementCategoryDto;
import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;

import java.util.List;

public interface ProductDetailsDomainService {
    ProductDetailsDto detailBy(Product product);
}
