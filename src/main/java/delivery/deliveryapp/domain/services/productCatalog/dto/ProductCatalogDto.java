package delivery.deliveryapp.domain.services.productCatalog.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductCatalogDto {
    private String categoryId;
    private String categoryName;
    private List<ProductDto> products;
}
