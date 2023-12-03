package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import delivery.deliveryapp.shared.exceptions.DomainException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServingSize extends DomainEntity {

    private String name;
    private String description;
    private Boolean activedComplements;
    private Integer amountOfComplements;
    private UniqueIdentifier complementCategoryId;

    private ServingSize(UniqueIdentifier id, String name, String description, Boolean activedComplements, Integer amountOfComplements,
                        UniqueIdentifier complementTypeId) {
        super(id);
        this.name = name;
        this.description = description;
        this.activedComplements = activedComplements;
        this.amountOfComplements = amountOfComplements;
        this.complementCategoryId = complementTypeId;
    }

    public static ServingSize create(UniqueIdentifier id, String name, String description, Boolean activedComplements, Integer amountOfComplements,
                                     UniqueIdentifier complementTypeId) {
        if (activedComplements)
            DomainException.whenIsNull(complementTypeId, "cannot create a serving size with actived complements without id of complement type.");

        return new ServingSize(id, name, description, activedComplements, amountOfComplements, complementTypeId);
    }

    public static ServingSize createNew(String name, String description, Boolean activedComplements, Integer amountOfComplements,
                                        UniqueIdentifier complementTypeId) {
        var id = UniqueIdentifier.create();
        return create(id, name, description, activedComplements, amountOfComplements, complementTypeId);
    }
}
