package delivery.deliveryapp.domain.builder;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ProductFeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;

import java.util.ArrayList;
import java.util.List;

public class ProductBuilder {
    private UniqueIdentifier productCategoryId;
    private String name;
    private String description;
    private String imageURI;
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

    public ProductBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductBuilder withImageUri(String imageURI) {
        this.imageURI = imageURI;
        return this;
    }

    public ProductBuilder withIsCustomizable(Boolean isCustomizable) {
        this.isCustomizable = isCustomizable;
        return this;
    }

    public ProductBuilder withServingSizes() {
        var feedstocksBaseConsumption = List.of(ProductFeedstockBaseConsumption.createNew(UniqueIdentifier.create(), 1, UnitOfMeasurement.create(MeasurementType.GRAM, 10.0)));
        var complementCategories = new ArrayList<ComplementCategory>();
        var servingSize = ServingSize.create(UniqueIdentifier.create(), "Pequeno", "tamanho pequeno", true, 9.99, complementCategories, feedstocksBaseConsumption);
        servingSizes = List.of(servingSize);
        return this;
    }

    public ProductBuilder withServingSizes(List<ServingSize> servingSizes) {
        this.servingSizes = servingSizes;
        return this;
    }

    public ProductBuilder withServingSizeInative() {
        var feedstocksBaseConsumption = List.of(ProductFeedstockBaseConsumption.createNew(UniqueIdentifier.create(), 1, UnitOfMeasurement.create(MeasurementType.GRAM, 10.0)));
        var servingSize = ServingSize.create(UniqueIdentifier.create(), "Pequeno", "tamanho pequeno", false, 9.99, null, feedstocksBaseConsumption);
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
        if (isActive == null)
            isActive = true;
        if (isCustomizable == null)
            isCustomizable = true;
        if (productCategoryId == null)
            productCategoryId = UniqueIdentifier.create();
        return Product.create(id, name, description, imageURI, isCustomizable, productCategoryId, servingSizes, isActive);
    }

    public Product estrictedBuild() {
        return Product.create(id, name, description, imageURI, isCustomizable, productCategoryId, servingSizes, isActive);
    }
}
