package delivery.deliveryapp.domain.services.productCatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {
    private String id;
    private String name;
    private String description;
    private String imageURI;
    private List<ServingSizeItem> servingSizes;
}
