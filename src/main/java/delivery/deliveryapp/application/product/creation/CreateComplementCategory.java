package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateComplementCategoryDto;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.springframework.stereotype.Component;

@Component
public class CreateComplementCategory implements CreationService<CreateComplementCategoryDto, ComplementCategory> {
  
  @Override
  public ComplementCategory create(CreateComplementCategoryDto createComplementCategoryDto) {
    ApplicationException.whenIsNull(createComplementCategoryDto, "could not create a complement category because createComplementCategoryDto is null.");
    ApplicationException.whenIsNull(createComplementCategoryDto.getComplements(), "could not create a complement category because complement item list is null.");
    
    var categoryId = UniqueIdentifier.create();
    var categoryName = createComplementCategoryDto.getCategoryName();
    var amountAvailable = createComplementCategoryDto.getAmountAvailable();
    var complements = createComplementCategoryDto.getComplements().stream().map((dto) -> {
      var complementId = UniqueIdentifier.create();
      var name = dto.getName();
      var baseConsumptionPerUnity = UnitOfMeasurement.create(dto.getMeasureType(), dto.getAmountOfMeasure());
      return ComplementItem.create(complementId, name, baseConsumptionPerUnity);
    }).toList();
    
    return ComplementCategory.create(categoryId, categoryName, amountAvailable, complements);
  }
}
