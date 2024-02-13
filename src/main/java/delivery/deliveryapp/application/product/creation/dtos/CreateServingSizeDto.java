package delivery.deliveryapp.application.product.creation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServingSizeDto {
    private String name;
    private String description;
    private Boolean complementsIsActive;
    private Double price;
    private List<CreateComplementCategoryDto> complementCategories;
    private List<CreateFeedstockBaseConsumptionDto> feedstocksBaseConsumptions;
}
