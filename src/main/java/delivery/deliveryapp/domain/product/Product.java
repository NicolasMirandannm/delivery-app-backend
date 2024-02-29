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
    private String description;
    private String imageURI;
    private Boolean isCustomizable;
    private Boolean isActived;
    private UniqueIdentifier productCategoryId;
    private List<ServingSize> servingSizes;

    private Product(UniqueIdentifier id, String name, String description, String imageURI, Boolean isCustomizable, UniqueIdentifier categoryId, List<ServingSize> servingSizes, Boolean isActived) {
        super(id);
        this.name = name;
        this.description = description;
        this.imageURI = imageURI;
        this.isCustomizable = isCustomizable;
        this.productCategoryId = categoryId;
        this.servingSizes = servingSizes;
        this.isActived = isActived;
    }

    public static Product create(UniqueIdentifier id, String name, String description, String imageURI, Boolean isCustomizable, UniqueIdentifier categoryId, List<ServingSize> servingSizes, Boolean isActived) {
        return new Product(id, name, description, imageURI, isCustomizable, categoryId, servingSizes, isActived);
    }

    public static Product createNew(String name, String description, Boolean isCustomizable, String imageURI, UniqueIdentifier categoryId) {
        DomainException.whenIsNull(name, "cannot create a new product with empty name.");
        DomainException.whenIsNull(description, "cannot create a new product with empty description.");
        DomainException.whenIsNull(imageURI, "cannot create a new product with empty image path.");
        DomainException.whenIsNull(categoryId, "cannot create a new product without a product category identifier.");

        var id = UniqueIdentifier.create();
        var isActive = true;
        var servingSizes = new ArrayList<ServingSize>();
        return Product.create(id,name, description, imageURI, isCustomizable, categoryId, servingSizes, isActive);
    }

    public void addAServingSize(ServingSize servingSize) {
        DomainException.whenIsNull(servingSize, "cannot add an empty serving size in product.");

        if (this.servingSizes == null)
            this.servingSizes = new ArrayList<ServingSize>();

        this.servingSizes.add(servingSize);
    }

    public Boolean isActive() {
        return this.isActived;
    }

    public String getProductCategoryId() {
        return this.productCategoryId != null
                ? productCategoryId.value()
                : null;
    }
}
