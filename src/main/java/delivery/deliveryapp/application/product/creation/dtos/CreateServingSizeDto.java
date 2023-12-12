package delivery.deliveryapp.application.product.creation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServingSizeDto {
    private String name;
    private String description;
    private Boolean complementsIsActive;
    private Integer amountOfComplements;
    private String complementTypeId;
}
