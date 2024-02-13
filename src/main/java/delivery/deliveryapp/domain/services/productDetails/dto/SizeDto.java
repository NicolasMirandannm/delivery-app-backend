package delivery.deliveryapp.domain.services.productDetails.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SizeDto {
    private String sizeDescription;
    private Integer amountAvailableToSelect;
    public SizeDto(String sizeDescription, Integer amountAvailableToSelect) {
        this.sizeDescription = sizeDescription;
        this.amountAvailableToSelect = amountAvailableToSelect;
    }
}
