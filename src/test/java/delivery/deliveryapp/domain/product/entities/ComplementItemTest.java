package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import delivery.deliveryapp.shared.UniqueIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComplementItemTest {

    private String name;
    private UniqueIdentifier feedstockId;
    private UnitOfMeasurement baseConsumptionPerUnity;

    @BeforeEach
    void setup() {
        this.name = "Sprinkles";
        this.feedstockId = UniqueIdentifier.create();

        var measurementType = MeasurementType.GRAM;
        var amount = 20.0;
        this.baseConsumptionPerUnity = UnitOfMeasurement.create(measurementType, amount);
    }

    @Test
    void should_create_a_complement_item() {
        var complementItemCreated = ComplementItem.create(UniqueIdentifier.create(), name, feedstockId, baseConsumptionPerUnity);

        Assertions.assertNotNull(complementItemCreated.getId());
    }
}
