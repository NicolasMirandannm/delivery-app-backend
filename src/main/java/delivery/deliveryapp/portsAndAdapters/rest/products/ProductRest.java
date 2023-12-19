package delivery.deliveryapp.portsAndAdapters.rest.products;

import delivery.deliveryapp.application.product.creation.ICreateProduct;
import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("product")
public class ProductRest {
    private final ICreateProduct createProduct;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateProductDto createProductDto) {
        createProduct.create(createProductDto);
        return ResponseEntity.ok("Product successfully created.");
    }
}
