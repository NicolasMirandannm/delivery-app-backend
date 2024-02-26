package delivery.deliveryapp.domain.builder;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;

import java.util.ArrayList;
import java.util.List;

public final class ServingSizeBuilder {
    private List<ComplementCategory> complementCategories;
    private String name;
    private String description;
    private Boolean activedComplements = false;
    private Double price;
    private UniqueIdentifier id;

    private ServingSizeBuilder() {
    }

    public static ServingSizeBuilder aServingSize() {
        return new ServingSizeBuilder();
    }

    public ServingSizeBuilder withComplementCategories() {
        this.complementCategories = new ArrayList<ComplementCategory>();
        return this;
    }
    
    public ServingSizeBuilder withComplementCategories(List<ComplementCategory> complementCategories) {
        this.complementCategories = complementCategories;
        return this;
    }

    public ServingSizeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ServingSizeBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ServingSizeBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }
    
    public ServingSizeBuilder withActivedComplements(Boolean activedComplements) {
        this.activedComplements = activedComplements;
        return this;
    }

    public ServingSizeBuilder withId() {
        this.id = UniqueIdentifier.create();;
        return this;
    }

    public ServingSize build() {
        ServingSize servingSize = ServingSize.create(id, name, description, activedComplements, price, complementCategories);
        servingSize.setComplementCategories(complementCategories);
        return servingSize;
    }
}
