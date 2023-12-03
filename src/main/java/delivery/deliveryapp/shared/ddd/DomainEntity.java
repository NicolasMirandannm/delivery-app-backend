package delivery.deliveryapp.shared.ddd;

import delivery.deliveryapp.shared.UniqueIdentifier;
import lombok.Getter;

@Getter
public abstract class DomainEntity {

    protected UniqueIdentifier id;

    protected DomainEntity(UniqueIdentifier id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other instanceof DomainEntity domainEntityObject)
            return this.id.equals(domainEntityObject.getId());
        else
            return false;
    }
}
