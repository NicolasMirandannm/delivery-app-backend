package delivery.deliveryapp.infra.rest.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import delivery.deliveryapp.application.product.creation.dto.ProductDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("product")
public class ProductRest {
    
    private final CreationService<ProductDto, Product> createProduct;
    private final ObjectMapper mapper = new ObjectMapper();

    @Async
    @PostMapping
    public void create(
            @RequestParam("productDto") String productDto,
            @RequestParam("productImage") MultipartFile productImage
    ) {
        ProductDto productDtoMapped = null;
        try {
            productDtoMapped = mapper.readValue(productDto, ProductDto.class);
        } catch (Exception e) {
            InfraException.throwException(e.getMessage());
        }
        productDtoMapped.setImage(productImage);
        createProduct.create(productDtoMapped);
    }
}
