package delivery.deliveryapp.infra.rest.product;

import delivery.deliveryapp.application.product.details.ProductDetailsAppService;
import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductDetailsRest {
  
  private final ProductDetailsAppService productDetailsAppService;
  
  @GetMapping("detail/{id}")
  public ResponseEntity<ProductDetailsDto> detailBy(@PathVariable("id") String id) {
    return ResponseEntity.ok(productDetailsAppService.getProductDetailsBy(id));
  }
  
}
