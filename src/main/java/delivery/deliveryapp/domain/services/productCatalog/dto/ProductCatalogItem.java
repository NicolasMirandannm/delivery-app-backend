package delivery.deliveryapp.domain.services.productCatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCatalogItem {
    private String categoryId;
    private String categoryName;
    private List<ProductItem> products;
}
