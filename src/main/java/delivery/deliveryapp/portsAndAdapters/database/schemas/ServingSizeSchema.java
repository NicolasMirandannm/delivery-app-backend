package delivery.deliveryapp.portsAndAdapters.database.schemas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document("serving-size")
public class ServingSizeSchema {
    private String id;
    private String name;
    private String description;
    private Boolean activedComplements;
    private Integer amountOfComplements;
    private Double price;
    private String complementCategoryId;
    private List<FeedstockBaseConsumptionSchema> feedstockBaseConsumptions;

    public ServingSizeSchema(String id, String name, String description, Boolean activedComplements, Integer amountOfComplements, Double price, String complementCategoryId, List<FeedstockBaseConsumptionSchema> feedstockBaseConsumptions) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.activedComplements = activedComplements;
        this.amountOfComplements = amountOfComplements;
        this.price = price;
        this.complementCategoryId = complementCategoryId;
        this.feedstockBaseConsumptions = feedstockBaseConsumptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServingSizeSchema that = (ServingSizeSchema) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(activedComplements, that.activedComplements) &&
                Objects.equals(amountOfComplements, that.amountOfComplements) &&
                Objects.equals(price, that.price) &&
                Objects.equals(complementCategoryId, that.complementCategoryId) &&
                Objects.equals(feedstockBaseConsumptions, that.feedstockBaseConsumptions);
    }
}
