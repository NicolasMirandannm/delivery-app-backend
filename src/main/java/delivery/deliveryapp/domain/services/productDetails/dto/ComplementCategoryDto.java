package delivery.deliveryapp.domain.services.productDetails.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ComplementCategoryDto {

    String title;
    List<String> complements;
    List<SizeDto> sizes;

    public ComplementCategoryDto(String title, List<String> items, List<SizeDto> sizes) {
        this.title = title;
        this.sizes = sizes;
        this.complements = items;
    }
}
