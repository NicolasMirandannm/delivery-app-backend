package delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.IMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.FeedstockBaseConsumptionSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.springframework.stereotype.Component;

@Component
public class FeedstockBaseConsumptionMapper implements IMapper<FeedstockBaseConsumption, FeedstockBaseConsumptionSchema> {
    @Override
    public FeedstockBaseConsumption toDomain(FeedstockBaseConsumptionSchema feedstockBaseConsumptionSchema) {
        InfraException.whenIsNull(feedstockBaseConsumptionSchema, "feedstock base consumption schema is null");

        var id = UniqueIdentifier.createFrom(feedstockBaseConsumptionSchema.getId());
        var feedstockId = UniqueIdentifier.createFrom(feedstockBaseConsumptionSchema.getFeedstockId());
        var quantity = feedstockBaseConsumptionSchema.getQuantity();
        var consumptionPerUnity = assembleConsumptionPerUnity(feedstockBaseConsumptionSchema);
        return FeedstockBaseConsumption.create(id, feedstockId, quantity, consumptionPerUnity);
    }

    @Override
    public FeedstockBaseConsumptionSchema toPersistence(FeedstockBaseConsumption feedstockBaseConsumption) {
        InfraException.whenIsNull(feedstockBaseConsumption, "feedstock base consumption entity is null");

        var id = feedstockBaseConsumption.getIdValue();
        var feedstockId = feedstockBaseConsumption.getFeedstockId().value();
        var quantity = feedstockBaseConsumption.getQuantity();
        var measureType = feedstockBaseConsumption.getConsumptionPerUnity().type();
        var amount = feedstockBaseConsumption.getConsumptionPerUnity().amount();
        return new FeedstockBaseConsumptionSchema(id,feedstockId,quantity,measureType,amount);
    }

    private UnitOfMeasurement assembleConsumptionPerUnity(FeedstockBaseConsumptionSchema feedstockBaseConsumptionSchema) {
        var measureType = feedstockBaseConsumptionSchema.getMeasureType();
        var amount = feedstockBaseConsumptionSchema.getAmount();
        return UnitOfMeasurement.create(measureType, amount);
    }
}
