package delivery.deliveryapp.domain.builder;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.shared.UniqueIdentifier;

public class ProductCategoryBuilder {
    private String categoryName;
    private Boolean isActive;
    private UniqueIdentifier id;

    private ProductCategoryBuilder() {
    }

    public static ProductCategoryBuilder aProductCategory() {
        return new ProductCategoryBuilder();
    }

    public ProductCategoryBuilder withCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ProductCategoryBuilder withIsActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public ProductCategoryBuilder withId(UniqueIdentifier id) {
        this.id = id;
        return this;
    }

    public ProductCategory build() {
        if (id == null)
            id = UniqueIdentifier.create();
        if (isActive == null)
            isActive = true;

        return ProductCategory.create(id, categoryName, isActive);
    }
}
