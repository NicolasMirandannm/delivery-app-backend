package delivery.deliveryapp.application.productCatalog;

import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItemDto;

import java.util.List;

public interface IProductCatalog {
    public List<ProductCatalogItemDto> show();
}
