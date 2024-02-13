package delivery.deliveryapp.application.product.creation.dtos;

import delivery.deliveryapp.domain.enums.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateComplementItemDto {
  private String name;
  private String feedstockId;
  private MeasurementType measureType;
  private Double amountOfMeasure;
}
