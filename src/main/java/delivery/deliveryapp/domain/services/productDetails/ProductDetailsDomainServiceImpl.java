package delivery.deliveryapp.domain.services.productDetails;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.services.productDetails.dto.ComplementCategoryDto;
import delivery.deliveryapp.domain.services.productDetails.dto.PriceDto;
import delivery.deliveryapp.domain.services.productDetails.dto.ProductDetailsDto;
import delivery.deliveryapp.domain.services.productDetails.dto.SizeDto;
import delivery.deliveryapp.shared.exceptions.DomainException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDetailsDomainServiceImpl implements ProductDetailsDomainService {
  
  @Override
  public ProductDetailsDto detailBy(Product product) {
    DomainException.whenIsNull(product, "could not detail a null product.");
    DomainException.whenIsEmptyOrNull(product.getServingSizes(), "could not detail a product with a empty sizes.");
    
    var id = product.getIdValue();
    var name = product.getName();
    var description = product.getDescription();
    var imagePath = product.getImageURI();
    var complementCategories = assembleComplementCategories(product.getServingSizes());
    var prices = product.getServingSizes().stream().map((servingSize -> {
      var size = servingSize.getName();
      var value = servingSize.getPrice();
      return new PriceDto(size, value);
    })).toList();
    
    return new ProductDetailsDto(id, name, description, imagePath, complementCategories, prices);
  }
  
  private List<ComplementCategoryDto> assembleComplementCategories(List<ServingSize> productSizes) {
    var productSize = productSizes.get(0);
    DomainException.whenIsEmptyOrNull(productSize.getComplementCategories(), "could not detail a product with a null complement categories in sizes.");
    
    if (!productSize.hasActiveComplements())
      return List.of();
    
    return productSize.getComplementCategories().stream().map((complementCategory -> {
      var categoryName = complementCategory.getCategoryName();
      var complements = complementCategory.getComplements().stream().map((ComplementItem::getName)).toList();
      
      var sizes = productSizes.stream().map((size) -> {
        var category = size.getComplementCategories()
          .stream()
          .filter((complement -> complement.getCategoryName().equals(complementCategory.getCategoryName())))
          .findFirst()
          .orElse(null);
        
        var sizeDescription = size.getName();
        var amountAvailableToSelect = category != null ? category.getAmountAvailable() : 0;
        return new SizeDto(sizeDescription, amountAvailableToSelect);
      }).toList();
      
      return new ComplementCategoryDto(categoryName, complements, sizes);
    })).toList();
  }
}
