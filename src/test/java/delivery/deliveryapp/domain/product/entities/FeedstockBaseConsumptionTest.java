package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FeedstockBaseConsumptionTest {

    private UniqueIdentifier id;
    private UniqueIdentifier feedstockId;
    private Integer quantity;
    private UnitOfMeasurement consumptionPerUnity;

    @BeforeEach
    void setup() {
        id = UniqueIdentifier.create();
        feedstockId = UniqueIdentifier.create();
        quantity = 2;
        consumptionPerUnity = UnitOfMeasurement.create(MeasurementType.GRAM, 100.0);
    }

    @Test
    void should_create_a_feedstock_base_comsumption_entity() {
        var feedstockBaseConsumption = ProductFeedstockBaseConsumption.create(id, feedstockId, quantity, consumptionPerUnity);

        Assertions.assertNotNull(feedstockBaseConsumption);
    }

    @Test
    void should_throw_an_exception_when_id_is_null_on_creation() {
        var expectedMessage = "cannot create a feedstock base consumption without id.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            ProductFeedstockBaseConsumption.create(null, feedstockId, quantity, consumptionPerUnity);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_feedstockId_is_null_on_creation() {
        var expectedMessage = "cannot create a feedstock base consumption without refence of the feedstock.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            ProductFeedstockBaseConsumption.create(id, null, quantity, consumptionPerUnity);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_quantity_is_null_on_creation() {
        var expectedMessage = "cannot create a feedstock base consumption without a quantity of items for consumption.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            ProductFeedstockBaseConsumption.create(id, feedstockId, null, consumptionPerUnity);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_consumption_per_unity_is_null_on_creation() {
        var expectedMessage = "cannot create a feedstock base consumption without consumption per unity.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            ProductFeedstockBaseConsumption.create(id, feedstockId, quantity, null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
