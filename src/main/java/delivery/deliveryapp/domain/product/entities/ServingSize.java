package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import delivery.deliveryapp.shared.exceptions.DomainException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServingSize extends DomainEntity {

    private String name;
    private String description;
    private Boolean activedComplements;
    private Integer amountOfComplements;
    private Double price;
    private UniqueIdentifier complementCategoryId;
    private List<FeedstockBaseConsumption> feedstocksBaseConsumption;

    private ServingSize(UniqueIdentifier id, String name, String description, Boolean activedComplements, Integer amountOfComplements,
                        Double price, UniqueIdentifier complementTypeId, List<FeedstockBaseConsumption> feedstocksBaseConsumption) {
        super(id);
        this.name = name;
        this.description = description;
        this.activedComplements = activedComplements;
        this.amountOfComplements = amountOfComplements;
        this.price = price;
        this.complementCategoryId = complementTypeId;
        this.feedstocksBaseConsumption = feedstocksBaseConsumption;
    }

    public static ServingSize create(UniqueIdentifier id, String name, String description, Boolean activedComplements, Integer amountOfComplements,
                                     Double price, UniqueIdentifier complementTypeId, List<FeedstockBaseConsumption> feedstocksBaseConsumption) {
        if (activedComplements)
            DomainException.whenIsNull(complementTypeId, "cannot create a serving size with actived complements without id of complement type.");

        return new ServingSize(id, name, description, activedComplements, amountOfComplements, price, complementTypeId, feedstocksBaseConsumption);
    }

    public static ServingSize createNew(String name, String description, Boolean activedComplements, Integer amountOfComplements,
                                        Double price, UniqueIdentifier complementTypeId, List<FeedstockBaseConsumption> feedstocksBaseConsumption) {
        var id = UniqueIdentifier.create();
        return create(id, name, description, activedComplements, amountOfComplements, price, complementTypeId, feedstocksBaseConsumption);
    }

    public String getComplementCategoryId() {
        return complementCategoryId != null ? complementCategoryId.value() : null;
    }
}
