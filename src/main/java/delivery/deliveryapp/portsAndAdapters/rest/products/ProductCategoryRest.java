package delivery.deliveryapp.portsAndAdapters.rest.products;

import delivery.deliveryapp.application.productCategory.CreateProductCategoryDto;
import delivery.deliveryapp.application.productCategory.ICreateProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("product-category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCategoryRest {

    private final ICreateProductCategory createProductCategory;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody() CreateProductCategoryDto createProductCategoryDto) {
        createProductCategory.create(createProductCategoryDto);
        return ResponseEntity.ok("product category successfully created.");
    }
}
