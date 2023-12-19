package delivery.deliveryapp.domain.builder;

import delivery.deliveryapp.domain.complementCategory.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;

import java.util.ArrayList;
import java.util.List;

public final class ServingSizeBuilder {
    private UniqueIdentifier complementCategoryId;
    private String name;
    private String description;
    private Boolean activedComplements = false;
    private Integer amountOfComplements;
    private List<FeedstockBaseConsumption> feedstocksBaseConsumption;
    private UniqueIdentifier id;

    private ServingSizeBuilder() {
    }

    public static ServingSizeBuilder aServingSize() {
        return new ServingSizeBuilder();
    }

    public ServingSizeBuilder withComplementCategoryId(UniqueIdentifier complementCategoryId) {
        this.complementCategoryId = complementCategoryId;
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

    public ServingSizeBuilder withActivedComplements(Boolean activedComplements) {
        this.activedComplements = activedComplements;
        return this;
    }

    public ServingSizeBuilder withAmountOfComplements(Integer amountOfComplements) {
        this.amountOfComplements = amountOfComplements;
        return this;
    }

    public ServingSizeBuilder withFeedstocksBaseConsumption() {
        var feedstockBaseConsumption = FeedstockBaseConsumption
                .create(UniqueIdentifier.create(), UniqueIdentifier.create(), 1, UnitOfMeasurement.create(MeasurementType.GRAM, 10.0));
        this.feedstocksBaseConsumption = List.of(feedstockBaseConsumption);
        return this;
    }

    public ServingSizeBuilder withId() {
        this.id = UniqueIdentifier.create();;
        return this;
    }

    public ServingSize build() {
        ServingSize servingSize = ServingSize.create(id, name, description, activedComplements, amountOfComplements, complementCategoryId, feedstocksBaseConsumption);
        servingSize.setComplementCategoryId(complementCategoryId);
        return servingSize;
    }
}
