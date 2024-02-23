package delivery.deliveryapp.infra.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.schemas.ComplementItemSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.springframework.stereotype.Component;

@Component
public class ComplementItemMapper implements Mapper<ComplementItem, ComplementItemSchema> {

    @Override
    public ComplementItem toDomain(ComplementItemSchema complementItemSchema) {
        InfraException.whenIsNull(complementItemSchema, "could not map a complement item schema to domain entity.");
        
        var id = UniqueIdentifier.createFrom(complementItemSchema.getId());
        var feedstockId = UniqueIdentifier.createFrom(complementItemSchema.getFeedstockId());
        var name = complementItemSchema.getName();
        var measure = UnitOfMeasurement.create(complementItemSchema.getMeasureType(), complementItemSchema.getAmountOfMeasure());
        return ComplementItem.create(id, name, feedstockId, measure);
    }

    @Override
    public ComplementItemSchema toPersistence(ComplementItem complementItem) {
        InfraException.whenIsNull(complementItem, "could not map a complement item entity to schema of persistence.");
        
        var id = complementItem.getIdValue();
        var feedstockId = complementItem.getFeedstockId().value();
        var name = complementItem.getName();
        var measureType = complementItem.getBaseConsumptionPerUnity().type();
        var amountOfMeasure = complementItem.getBaseConsumptionPerUnity().amount();
        
        return new ComplementItemSchema(id, feedstockId, name, measureType, amountOfMeasure);
    }
}
