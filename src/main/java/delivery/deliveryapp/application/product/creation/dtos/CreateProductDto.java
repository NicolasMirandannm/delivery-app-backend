package delivery.deliveryapp.application.product.creation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateProductDto {
    private String name;
    private String description;
    private MultipartFile image;
    private String productCategoryId;
    private List<CreateServingSizeDto> servingSizes;

    public CreateProductDto(String name, String description, String productCategoryId, List<CreateServingSizeDto> servingSizes) {
        this.name = name;
        this.description = description;
        this.productCategoryId = productCategoryId;
        this.servingSizes = servingSizes;
    }
}
