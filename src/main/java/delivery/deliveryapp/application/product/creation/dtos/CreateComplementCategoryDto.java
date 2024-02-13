package delivery.deliveryapp.application.product.creation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateComplementCategoryDto {
  private String categoryName;
  private Integer amountAvailable;
  private List<CreateComplementItemDto> complements;
}
