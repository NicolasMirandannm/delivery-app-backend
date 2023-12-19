package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import delivery.deliveryapp.shared.exceptions.DomainException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedstockBaseConsumption extends DomainEntity {
    private UniqueIdentifier feedstockId;
    private Integer quantity;
    private UnitOfMeasurement consumptionPerUnity;

    protected FeedstockBaseConsumption(UniqueIdentifier id, UniqueIdentifier feedstockId, Integer quantity, UnitOfMeasurement consumptionPerUnity) {
        super(id);
        this.feedstockId = feedstockId;
        this.quantity = quantity;
        this.consumptionPerUnity = consumptionPerUnity;
    }

    public static FeedstockBaseConsumption create(UniqueIdentifier id, UniqueIdentifier feedstockId, Integer quantity, UnitOfMeasurement consumptionPerUnity) {
        verifyCreationParameters(id, feedstockId, quantity, consumptionPerUnity);
        return new FeedstockBaseConsumption(id, feedstockId, quantity, consumptionPerUnity);
    }

    public static FeedstockBaseConsumption createNew(UniqueIdentifier feedstockId, Integer quantity, UnitOfMeasurement consumptionPerUnity) {
        return FeedstockBaseConsumption.create(UniqueIdentifier.create(), feedstockId, quantity, consumptionPerUnity);
    }

    private static void verifyCreationParameters(UniqueIdentifier id, UniqueIdentifier feedstockId, Integer quantity, UnitOfMeasurement consumptionPerUnity) {
        DomainException.whenIsNull(id, "cannot create a feedstock base consumption without id.");
        DomainException.whenIsNull(feedstockId, "cannot create a feedstock base consumption without refence of the feedstock.");
        DomainException.whenIsNull(quantity, "cannot create a feedstock base consumption without a quantity of items for consumption.");
        DomainException.whenIsNull(consumptionPerUnity, "cannot create a feedstock base consumption without consumption per unity.");
    }
}
