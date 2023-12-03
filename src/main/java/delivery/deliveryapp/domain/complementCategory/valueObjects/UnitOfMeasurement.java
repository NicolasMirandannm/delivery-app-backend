package delivery.deliveryapp.domain.complementCategory.valueObjects;

import delivery.deliveryapp.domain.complementCategory.enums.MeasurementType;
import delivery.deliveryapp.shared.exceptions.DomainException;

public record UnitOfMeasurement(MeasurementType type, Double amount) {
    public static UnitOfMeasurement create(MeasurementType type, Double amount) {
        if (amount < 0) {
            DomainException.throwException("cannot create an unit of measurement with amount less than 0.");
        }

        return new UnitOfMeasurement(type, amount);
    }
}
