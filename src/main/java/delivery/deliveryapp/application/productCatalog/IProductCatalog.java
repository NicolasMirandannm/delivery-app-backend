package delivery.deliveryapp.application.productCatalog;

import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;

import java.util.List;

public interface IProductCatalog {
    public List<ProductCatalogItem> show();
}
