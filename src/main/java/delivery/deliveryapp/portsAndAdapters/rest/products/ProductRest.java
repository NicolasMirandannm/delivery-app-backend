package delivery.deliveryapp.portsAndAdapters.rest.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.portsAndAdapters.filesystem.StorageService;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<String> create(
            @RequestParam("createProductDto") String createProductDto,
            @RequestParam("file") MultipartFile file
    ) {
        CreateProductDto productDto = null;
        try {
            productDto = mapper.readValue(createProductDto, CreateProductDto.class);
        } catch (Exception e) {
            InfraException.throwException(e.getMessage());
        }
        productDto.setImageURI(file.getOriginalFilename());
        createProduct.create(productDto);
        storageService.store(file);
        return ResponseEntity.ok("Product successfully created.");
    }
}
