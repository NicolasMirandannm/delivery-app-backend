package delivery.deliveryapp.domain.services.productCatalog;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;

import java.util.List;

public interface IProductCatalogService {
    public List<ProductCatalogItem> assemble(List<ProductCategory> productCategory, List<Product> products);
}
