package delivery.deliveryapp.infra.rest.products;

import delivery.deliveryapp.application.productCatalog.IProductCatalog;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("product-catalog")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCatalogRest {

    private final IProductCatalog productCatalog;

    @GetMapping
    public ResponseEntity<List<ProductCatalogItem>> show() {
        return ResponseEntity.ok(productCatalog.show());
    }
}
