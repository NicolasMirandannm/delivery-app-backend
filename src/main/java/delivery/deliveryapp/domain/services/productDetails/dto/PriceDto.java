package delivery.deliveryapp.domain.services.productDetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {
    private String sizeDescription;
    private Double price;
}