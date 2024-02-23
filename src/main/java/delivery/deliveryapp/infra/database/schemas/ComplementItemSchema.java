package delivery.deliveryapp.infra.database.schemas;

import delivery.deliveryapp.domain.enums.MeasurementType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document("complement-item")
public class ComplementItemSchema {
    private String id;
    private String feedstockId;
    private String name;
    private MeasurementType measureType;
    private Double amountOfMeasure;

    public ComplementItemSchema(String id, String feedstockId, String name, MeasurementType measureType, Double amountOfMeasure) {
        super();
        this.id = id;
        this.feedstockId = feedstockId;
        this.name = name;
        this.measureType = measureType;
        this.amountOfMeasure = amountOfMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplementItemSchema that = (ComplementItemSchema) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(feedstockId, that.feedstockId) &&
                measureType == that.measureType &&
                Objects.equals(amountOfMeasure, that.amountOfMeasure) &&
                Objects.equals(name, that.name);
    }
}
