package delivery.deliveryapp.domain.services.productDetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDto {
    private String productId;
    private String name;
    private String description;
    private String imagePath;
    private List<ComplementCategoryDto> complementCategories;
    private List<PriceDto> prices;
}
