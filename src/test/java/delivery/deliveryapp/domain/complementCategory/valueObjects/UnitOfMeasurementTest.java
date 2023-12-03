package delivery.deliveryapp.domain.complementCategory.valueObjects;

import delivery.deliveryapp.domain.complementCategory.enums.MeasurementType;
import delivery.deliveryapp.shared.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnitOfMeasurementTest {

    private MeasurementType measureType;
    private Double amount;

    @BeforeEach
    void setup() {
        this.measureType = MeasurementType.GRAM;
        this.amount = 20.0;
    }

    @Test
    void should_create_an_unit_of_measurement() {
        var unitOfMeasurementCreated = UnitOfMeasurement.create(measureType, amount);

        Assertions.assertNotNull(unitOfMeasurementCreated);
    }

    @Test
    void should_throw_an_exception_when_amount_is_less_than_zero() {
        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            UnitOfMeasurement.create(measureType, -2.0);
        });
        var expectedErrorMessage = "cannot create an unit of measurement with amount less than 0.";

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
