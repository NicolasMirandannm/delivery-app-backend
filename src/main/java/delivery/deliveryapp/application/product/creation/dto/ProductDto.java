package delivery.deliveryapp.application.product.creation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {
  private String name;
  private String description;
  private String productCategoryId;
  private Boolean hasActiveComplements;
  private List<ServingSizeDto> servingSizes;
  private List<ComplementDto> complements;
  private MultipartFile image;
  
  public ProductDto(String name, String description, String productCategoryId, Boolean hasActiveComplements, List<ServingSizeDto> servingSizes, List<ComplementDto> complements) {
    this.name = name;
    this.description = description;
    this.productCategoryId = productCategoryId;
    this.hasActiveComplements = hasActiveComplements;
    this.servingSizes = servingSizes;
    this.complements = complements;
  }
}
