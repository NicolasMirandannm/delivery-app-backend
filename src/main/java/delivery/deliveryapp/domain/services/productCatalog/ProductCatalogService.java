package delivery.deliveryapp.domain.services.productCatalog;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItemDto;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductCatalogService implements IProductCatalogService {

    @Override
    public List<ProductCatalogItemDto> assemble(List<ProductCategory> productCategory, List<Product> products) {
        var catalog = new HashMap<String, ProductCatalogItemDto>();
        for (var category : productCategory) {
            if (category.isActive()) {
                var catalogItem = new ProductCatalogItemDto();
                catalogItem.setCategoryId(category.getIdValue());
                catalogItem.setCategoryName(category.getCategoryName());
                catalogItem.setProducts(new ArrayList<ProductDto>());
                catalog.put(catalogItem.getCategoryId(), catalogItem);
            }
        }

        for (var product : products) {
            if (product.isActive()) {
                var categoryId = product.getProductCategoryId().value();
                var productItem = new ProductDto();
                productItem.setId(product.getIdValue());
                productItem.setName(product.getName());
                catalog.get(categoryId).getProducts().add(productItem);
            }
        }

        return catalog.values().stream().toList();
    }
}
