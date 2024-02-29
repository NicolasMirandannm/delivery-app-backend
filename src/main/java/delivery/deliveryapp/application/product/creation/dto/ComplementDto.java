package delivery.deliveryapp.application.product.creation.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplementDto {
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AmountBySize {
    private String sizeDescription;
    private Integer amountAvailable;
  }
  private String categoryName;
  private List<AmountBySize> amountBySize;
  private List<ComplementItemDto> complementaryItems;
}
