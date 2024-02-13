package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComplementCategory extends DomainEntity {

    private String categoryName;
    private Integer amountAvailable;
    private List<ComplementItem> complements;

    private ComplementCategory(UniqueIdentifier id, String categoryName, Integer amountAvailable, List<ComplementItem> complements) {
        super(id);
        this.categoryName = categoryName;
        this.amountAvailable = amountAvailable;
        this.complements = complements;
    }

    public static ComplementCategory create(UniqueIdentifier id, String categoryName, Integer amountAvailable, List<ComplementItem> complements) {
        return new ComplementCategory(id, categoryName, amountAvailable, complements);
    }
}
