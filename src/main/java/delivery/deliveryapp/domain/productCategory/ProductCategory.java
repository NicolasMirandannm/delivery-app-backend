package delivery.deliveryapp.domain.productCategory;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.AggregateRoot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategory extends AggregateRoot {

    private String categoryName;
    private Boolean isActive;

    private ProductCategory(UniqueIdentifier id, String categoryName, Boolean isActive) {
        super(id);
        this.categoryName = categoryName;
        this.isActive = true;
    }

    public static ProductCategory create(UniqueIdentifier id, String categoryName, Boolean isActive) {
        return new ProductCategory(id, categoryName, isActive);
    }
}
