package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateFeedstockBaseConsumptionDto;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.springframework.stereotype.Service;

@Service
public class CreateFeedstockBaseConsumption implements CreationService<CreateFeedstockBaseConsumptionDto, FeedstockBaseConsumption> {
    public FeedstockBaseConsumption create(CreateFeedstockBaseConsumptionDto baseConsumptionDto) {
        ApplicationException.whenIsNull(baseConsumptionDto, "feedstock base consumption dto is null");

        var feedstockId = UniqueIdentifier.createFrom(baseConsumptionDto.getFeedstockId());
        var quantity = baseConsumptionDto.getQuantity();
        var unitOfMeasurement = UnitOfMeasurement.create(baseConsumptionDto.getMeasureType(), baseConsumptionDto.getAmount());
        return FeedstockBaseConsumption.createNew(feedstockId, quantity, unitOfMeasurement);
    }
}
