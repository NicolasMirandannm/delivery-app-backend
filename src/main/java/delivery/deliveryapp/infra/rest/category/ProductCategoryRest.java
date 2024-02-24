package delivery.deliveryapp.infra.rest.category;

import delivery.deliveryapp.application.productCategory.CreateProductCategoryDto;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("product-category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCategoryRest {

    private final CreationService<CreateProductCategoryDto, ProductCategory> createProductCategory;

    @Async
    @PostMapping
    public void create(@RequestBody() CreateProductCategoryDto createProductCategoryDto) {
        createProductCategory.create(createProductCategoryDto);
    }
}
