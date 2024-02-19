package delivery.deliveryapp.domain.services.productCatalog;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductItem;
import delivery.deliveryapp.domain.services.productCatalog.dto.ServingSizeItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductCatalogService implements IProductCatalogService {

    @Override
    public List<ProductCatalogItem> assemble(List<ProductCategory> productCategory, List<Product> products) {
        var catalog = new HashMap<String, ProductCatalogItem>();
        for (var category : productCategory) {
            if (category.isActive()) {
                var catalogItem = assembleProductCatalogItem(category);
                catalog.put(catalogItem.getCategoryId(), catalogItem);
            }
        }

        for (var product : products) {
            if (product.isActive()) {
                var categoryId = product.getProductCategoryId();
                var servingSizes = product.getServingSizes().stream().map(this::assembleServingSizeItem).toList();
                var productItem = assembleProductItem(product, servingSizes);
                catalog.get(categoryId).getProducts().add(productItem); // todo testar para quando tiver um produto sem categoria
            }
        }

        return catalog.values().stream().toList();
    }

    private ProductCatalogItem assembleProductCatalogItem(ProductCategory category) {
        var catalogItem = new ProductCatalogItem();
        catalogItem.setCategoryId(category.getIdValue());
        catalogItem.setCategoryName(category.getCategoryName());
        catalogItem.setProducts(new ArrayList<ProductItem>());
        return catalogItem;
    }

    private ServingSizeItem assembleServingSizeItem(ServingSize servingSize) {
        var servingSizeItem = new ServingSizeItem();
        servingSizeItem.setName(servingSize.getName());
        servingSizeItem.setPrice(servingSize.getPrice());
        return servingSizeItem;
    }

    private ProductItem assembleProductItem(Product product, List<ServingSizeItem> servingSizeItens) {
        var productItem = new ProductItem();
        productItem.setId(product.getIdValue());
        productItem.setName(product.getName());
        productItem.setDescription(product.getDescription());
        productItem.setImageURI(product.getImageURI());
        productItem.setServingSizes(servingSizeItens);
        return productItem;
    }
}
