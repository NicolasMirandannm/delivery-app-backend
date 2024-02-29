package delivery.deliveryapp.application.product.creation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServingSizeDto {
  private String name;
  private String description;
  private Double price;
}
