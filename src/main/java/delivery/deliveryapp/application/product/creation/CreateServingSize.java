package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateFeedstockBaseConsumptionDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateServingSize implements CreationService<CreateServingSizeDto, ServingSize> {

    private final CreationService<CreateFeedstockBaseConsumptionDto, FeedstockBaseConsumption> createFeedstockBaseConsumption;

    public ServingSize create(CreateServingSizeDto servingSizeDto) {
        ApplicationException.whenIsNull(servingSizeDto, "serving size dto is null");
        ApplicationException.whenIsNull(servingSizeDto.getFeedstocksBaseConsumptions(), "servingSizeDto has a null feedstocksBaseConsumptionDto");

        var servingSizeName = servingSizeDto.getName();
        var description = servingSizeDto.getDescription();
        var complementsIsActive = servingSizeDto.getComplementsIsActive();
        var amountOfComplements = servingSizeDto.getAmountOfComplements();
        var price = servingSizeDto.getPrice();
        var complementTypeId = UniqueIdentifier.createFrom(servingSizeDto.getComplementTypeId());
        var feedstocksBaseConsumption = servingSizeDto.getFeedstocksBaseConsumptions()
                .stream().map(createFeedstockBaseConsumption::create).collect(Collectors.toList());

        return ServingSize.createNew(servingSizeName, description, complementsIsActive, amountOfComplements, price, complementTypeId, feedstocksBaseConsumption);
    }
}
