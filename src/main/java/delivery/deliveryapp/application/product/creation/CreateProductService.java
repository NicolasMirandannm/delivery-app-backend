package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.fileupload.UploadFile;
import delivery.deliveryapp.application.product.creation.dto.ComplementDto;
import delivery.deliveryapp.application.product.creation.dto.ProductDto;
import delivery.deliveryapp.application.product.creation.dto.ServingSizeDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.service.CreationService;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateProductService implements CreationService<ProductDto, Product> {
  
  private final UploadFile uploadFile;
  private final ProductRepository productRepository;
  
  @Override
  public Product create(ProductDto productDto) {
    ApplicationException.whenIsNull(productDto, "cannot create a product without product creation data.");
    validateProductSizes(productDto);
    
    var imageKey = uploadFile.upload(productDto.getImage());
    
    var product = Product.createNew(
      productDto.getName(),
      productDto.getDescription(),
      productDto.getHasActiveComplements(),
      imageKey,
      UniqueIdentifier.createFrom(productDto.getProductCategoryId())
    );
    
    for (ServingSizeDto servingSizeDto : productDto.getServingSizes()) {
      var complementDtos = productDto.getComplements();
      var servingSize = createServingSizeBy(servingSizeDto, complementDtos);
      product.addAServingSize(servingSize);
    }
    
    productRepository.create(product);
    return product;
  }
  
  private void validateProductSizes(ProductDto productDto) {
    var servingSizes = productDto.getServingSizes().stream().map(ServingSizeDto::getName).toList();
    if (servingSizes.stream().distinct().count() != servingSizes.size()) {
      ApplicationException.throwException("A product cannot have two serving sizes with the same name");
    }
    
    if (productDto.getHasActiveComplements()) {
      ApplicationException.whenIsNull(productDto.getComplements(), "A product with active complements must have at least one complement");
      productDto.getComplements().forEach(complementDto -> {
        var complementSizes = complementDto.getAmountBySize().stream().map(ComplementDto.AmountBySize::getSizeDescription).toList();
        if (complementSizes.size() != servingSizes.size())
          ApplicationException.throwException("A complement must have the same amount of sizes as the product");
        if (complementSizes.stream().noneMatch(servingSizes::contains))
          ApplicationException.throwException("A complement must have the same sizes as the product");
      });
    }
  }
  
  private ServingSize createServingSizeBy(ServingSizeDto servingSizeDto, List<ComplementDto> complementDtos) {
    var sizeName = servingSizeDto.getName();
    var sizeDescription = servingSizeDto.getDescription();
    var sizePrice = servingSizeDto.getPrice();
    var complements = complementDtos.stream().map(complementDto -> createComplementsBy(sizeName, complementDto)).toList();
    
    return ServingSize.createNew(
      sizeName,
      sizeDescription,
      !complementDtos.isEmpty(),
      sizePrice,
      complements
    );
  }
  
  private ComplementCategory createComplementsBy(String sizeName, ComplementDto complementDto) {
    var categoryName = complementDto.getCategoryName();
    var amountAvailable = complementDto.getAmountBySize().stream().filter(amountBySize ->
      amountBySize.getSizeDescription().equals(sizeName))
      .findFirst()
      .orElseThrow(() -> new InfraException("Complement size not found"))
      .getAmountAvailable();
    var complementaryItems = complementDto.getComplementaryItems().stream().map(item ->
      ComplementItem.createNew(
        item.getName(),
        UnitOfMeasurement.create(item.getMeasureType(), item.getAmountOfMeasure()
      )
    )).toList();
    
    return ComplementCategory.create(
      categoryName,
      amountAvailable,
      complementaryItems
    );
  }
}
