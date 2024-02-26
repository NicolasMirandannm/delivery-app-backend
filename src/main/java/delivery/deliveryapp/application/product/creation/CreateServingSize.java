package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateComplementCategoryDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateServingSize implements CreationService<CreateServingSizeDto, ServingSize> {

    private final CreationService<CreateComplementCategoryDto, ComplementCategory> createComplementCategory;
    
    public ServingSize create(CreateServingSizeDto servingSizeDto) {
        ApplicationException.whenIsNull(servingSizeDto, "serving size dto is null");
        ApplicationException.whenIsNull(servingSizeDto.getComplementCategories(), "servingSizeDto has a null complement categories.");
        
        var servingSizeName = servingSizeDto.getName();
        var description = servingSizeDto.getDescription();
        var complementsIsActive = servingSizeDto.getComplementsIsActive();
        var price = servingSizeDto.getPrice();
        var complementsCategories = servingSizeDto.getComplementCategories()
          .stream().map(createComplementCategory::create).toList();

        return ServingSize.createNew(servingSizeName, description, complementsIsActive, price, complementsCategories);
    }
}
