package delivery.deliveryapp.domain.complementCategory;

import delivery.deliveryapp.domain.complementCategory.entities.ComplementItem;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.AggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComplementCategory extends AggregateRoot {

    private String categoryName;
    private UniqueIdentifier productId;
    private List<ComplementItem> complements;

    private ComplementCategory(UniqueIdentifier id, String categoryName, UniqueIdentifier productId, List<ComplementItem> complements) {
        super(id);
        this.categoryName = categoryName;
        this.productId = productId;
        this.complements = complements;
    }

    public static ComplementCategory create(UniqueIdentifier id, String categoryName, UniqueIdentifier productId, List<ComplementItem> complements) {
        return new ComplementCategory(id,categoryName, productId, complements);
    }
}
