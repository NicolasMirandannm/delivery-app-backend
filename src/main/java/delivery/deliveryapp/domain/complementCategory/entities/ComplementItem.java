package delivery.deliveryapp.domain.complementCategory.entities;

import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplementItem extends DomainEntity {

    private String name;
    private UniqueIdentifier feedstockId;
    private UnitOfMeasurement baseConsumptionPerUnity;

    private ComplementItem(UniqueIdentifier id, String name, UniqueIdentifier feedstockId, UnitOfMeasurement baseConsumptionPerUnity) {
        super(id);
        this.name = name;
        this.feedstockId = feedstockId;
        this.baseConsumptionPerUnity = baseConsumptionPerUnity;
    }

    public static ComplementItem create(UniqueIdentifier id, String name, UniqueIdentifier feedstockId, UnitOfMeasurement baseConsumptionPerUnity) {
        return new ComplementItem(id,name,feedstockId, baseConsumptionPerUnity);
    }
}
