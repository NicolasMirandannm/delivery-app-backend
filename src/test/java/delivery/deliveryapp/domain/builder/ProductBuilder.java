package delivery.deliveryapp.domain.builder;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;

import java.util.List;

public class ProductBuilder {
    private UniqueIdentifier productCategoryId;
    private String name;
    private Boolean isCustomizable;
    private List<ServingSize> servingSizes;
    private UniqueIdentifier id;

    private Boolean isActive;

    private ProductBuilder() {
    }

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withProductCategoryId(UniqueIdentifier productCategoryId) {
        this.productCategoryId = productCategoryId;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withIsCustomizable(Boolean isCustomizable) {
        this.isCustomizable = isCustomizable;
        return this;
    }

    public ProductBuilder withServingSizes(List<ServingSize> servingSizes) {
        this.servingSizes = servingSizes;
        return this;
    }

    public ProductBuilder withServingSizeInative() {
        var servingSize = ServingSize.create(UniqueIdentifier.create(), "Pequeno", "tamanho pequeno", false, 1, UniqueIdentifier.create());
        servingSizes = List.of(servingSize);
        return this;
    }


    public ProductBuilder withId(UniqueIdentifier id) {
        this.id = id;
        return this;
    }

    public ProductBuilder isActived(Boolean val) {
        this.isActive = val;
        return this;
    }

    public Product build() {
        if (id == null)
            id = UniqueIdentifier.create();
        if (servingSizes == null) {
            var servingSize = ServingSize.create(UniqueIdentifier.create(), "Pequeno", "tamanho pequeno", true, 1, UniqueIdentifier.create());
            servingSizes = List.of(servingSize);
        }
        if (isActive == null)
            isActive = true;
        if (isCustomizable == null)
            isCustomizable = true;
        if (productCategoryId == null)
            productCategoryId = UniqueIdentifier.create();
        return Product.create(id, name, isCustomizable, productCategoryId, servingSizes, isActive);
    }

    public Product estrictedBuild() {
        return Product.create(id, name, isCustomizable, productCategoryId, servingSizes, isActive);
    }
}
