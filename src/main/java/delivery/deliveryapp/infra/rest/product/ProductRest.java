package delivery.deliveryapp.infra.rest.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.infra.config.filesystem.StorageService;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.service.CreationService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("product")
public class ProductRest {
    private final CreationService<CreateProductDto, Product> createProduct;
    private final StorageService storageService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Async
    @PostMapping
    public void create(
            @RequestParam("createProductDto") String createProductDto,
            @RequestParam("productImage") MultipartFile productImage
    ) {
        CreateProductDto productDto = null;
        try {
            productDto = mapper.readValue(createProductDto, CreateProductDto.class);
        } catch (Exception e) {
            InfraException.throwException(e.getMessage());
        }
        productDto.setImage(productImage);
        createProduct.create(productDto);
    }

    @GetMapping
    public ResponseEntity<Product> findById(@PathParam("id") String id) {
        return null;
    }
}
