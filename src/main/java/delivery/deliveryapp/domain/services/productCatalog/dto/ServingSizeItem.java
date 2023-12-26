package delivery.deliveryapp.domain.services.productCatalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServingSizeItem {
    private String name;
    private Double price;
}
