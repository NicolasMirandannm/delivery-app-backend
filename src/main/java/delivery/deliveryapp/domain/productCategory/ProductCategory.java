package delivery.deliveryapp.domain.productCategory;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.AggregateRoot;
import delivery.deliveryapp.shared.exceptions.DomainException;
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
        this.isActive = isActive;
    }

    public static ProductCategory create(UniqueIdentifier id, String categoryName, Boolean isActive) {
        return new ProductCategory(id, categoryName, isActive);
    }

    public static ProductCategory createNew(String categoryName) {
        DomainException.whenIsNull(categoryName, "cannot create a product category with an empty name");

        var id = UniqueIdentifier.create();
        var isActive = true;
        return ProductCategory.create(id, categoryName, isActive);
    }

    public Boolean isActive() {
        return this.isActive;
    }
}
