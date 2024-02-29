package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplementItem extends DomainEntity {

    private String name;
    private UnitOfMeasurement baseConsumptionPerUnity;

    private ComplementItem(UniqueIdentifier id, String name, UnitOfMeasurement baseConsumptionPerUnity) {
        super(id);
        this.name = name;
        this.baseConsumptionPerUnity = baseConsumptionPerUnity;
    }

    public static ComplementItem create(UniqueIdentifier id, String name, UnitOfMeasurement baseConsumptionPerUnity) {
        return new ComplementItem(id, name, baseConsumptionPerUnity);
    }
    
    public static ComplementItem createNew(String name, UnitOfMeasurement baseConsumptionPerUnity) {
        return new ComplementItem(UniqueIdentifier.create(), name, baseConsumptionPerUnity);
    }
}
