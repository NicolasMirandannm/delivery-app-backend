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
    private Double price;
    private List<ComplementCategorySchema> complementCategories;
    private List<FeedstockBaseConsumptionSchema> feedstockBaseConsumptions;

    public ServingSizeSchema(String id, String name, String description, Boolean activedComplements, Double price, List<ComplementCategorySchema> complementCategories, List<FeedstockBaseConsumptionSchema> feedstockBaseConsumptions) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.activedComplements = activedComplements;
        this.price = price;
        this.complementCategories = complementCategories;
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
                Objects.equals(price, that.price) &&
                Objects.equals(complementCategories, that.complementCategories) &&
                Objects.equals(feedstockBaseConsumptions, that.feedstockBaseConsumptions);
    }
}
