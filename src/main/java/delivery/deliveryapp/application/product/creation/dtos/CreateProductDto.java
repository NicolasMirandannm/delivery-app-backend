package delivery.deliveryapp.application.product.creation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    private String name;
    private String productCategoryId;
    private List<CreateServingSizeDto> servingSizes;
}
