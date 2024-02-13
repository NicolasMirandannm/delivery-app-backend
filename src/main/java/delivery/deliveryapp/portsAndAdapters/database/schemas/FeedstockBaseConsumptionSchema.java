package delivery.deliveryapp.portsAndAdapters.database.schemas;

import delivery.deliveryapp.domain.enums.MeasurementType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document("feedstock-base-consumption")
public class FeedstockBaseConsumptionSchema {
    private String id;
    private String feedstockId;
    private Integer quantity;
    private MeasurementType measureType;
    private Double amount;

    public FeedstockBaseConsumptionSchema(String id, String feedstockId, Integer quantity, MeasurementType measureType, Double amount) {
        super();
        this.id = id;
        this.feedstockId = feedstockId;
        this.quantity = quantity;
        this.measureType = measureType;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedstockBaseConsumptionSchema that = (FeedstockBaseConsumptionSchema) o;
        return Objects.equals(id, that.id) && Objects.equals(feedstockId, that.feedstockId) && Objects.equals(quantity, that.quantity) && measureType == that.measureType && Objects.equals(amount, that.amount);
    }
}
