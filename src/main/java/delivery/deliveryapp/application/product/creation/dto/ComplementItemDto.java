package delivery.deliveryapp.application.product.creation.dto;

import delivery.deliveryapp.domain.enums.MeasurementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplementItemDto {
  private String name;
  private MeasurementType measureType;
  private Double amountOfMeasure;
}
