package delivery.deliveryapp.domain.services.productCatalog;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItemDto;

import java.util.List;

public interface IProductCatalogService {
    public List<ProductCatalogItemDto> assemble(List<ProductCategory> productCategory, List<Product> products);
}
