package delivery.deliveryapp.domain.product;

import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.AggregateRoot;
import delivery.deliveryapp.shared.exceptions.DomainException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Product extends AggregateRoot {

    private String name;
    private Boolean isCustomizable;
    private UniqueIdentifier productCategoryId;
    private List<ServingSize> servingSizes;

    private Product(UniqueIdentifier id, String name, Boolean isCustomizable, UniqueIdentifier categoryId, List<ServingSize> servingSizes) {
        super(id);
        this.name = name;
        this.isCustomizable = isCustomizable;
        this.productCategoryId = categoryId;
        this.servingSizes = servingSizes;
    }

    public static Product create(UniqueIdentifier id, String name, Boolean isCustomizable, UniqueIdentifier categoryId, List<ServingSize> servingSizes) {
        return new Product(id, name, isCustomizable, categoryId, servingSizes);
    }

    public void addAServingSize(ServingSize servingSize) {
        DomainException.whenIsNull(servingSize, "cannot add an empty serving size in product.");

        if (this.servingSizes == null)
            this.servingSizes = new ArrayList<ServingSize>();

        this.servingSizes.add(servingSize);
    }
}
