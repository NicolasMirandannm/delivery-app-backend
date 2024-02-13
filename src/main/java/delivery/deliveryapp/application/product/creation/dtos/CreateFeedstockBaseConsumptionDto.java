package delivery.deliveryapp.application.product.creation.dtos;

import delivery.deliveryapp.domain.enums.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFeedstockBaseConsumptionDto {
    private String feedstockId;
    private Integer quantity;
    private MeasurementType measureType;
    private Double amount;
}
