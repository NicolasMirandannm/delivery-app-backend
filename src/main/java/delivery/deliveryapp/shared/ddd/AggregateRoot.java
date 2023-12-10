package delivery.deliveryapp.shared.ddd;

import delivery.deliveryapp.shared.UniqueIdentifier;

public abstract class AggregateRoot extends DomainEntity {
    protected AggregateRoot(UniqueIdentifier id) {
        super(id);
    }

}
