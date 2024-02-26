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
    private String name;
    private MeasurementType measureType;
    private Double amountOfMeasure;

    public ComplementItemSchema(String id, String name, MeasurementType measureType, Double amountOfMeasure) {
        super();
        this.id = id;
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
                measureType == that.measureType &&
                Objects.equals(amountOfMeasure, that.amountOfMeasure) &&
                Objects.equals(name, that.name);
    }
}
