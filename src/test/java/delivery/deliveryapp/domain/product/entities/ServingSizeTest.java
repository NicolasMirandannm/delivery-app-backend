package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.domain.complementCategory.enums.MeasurementType;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ServingSizeTest {

    private String name;
    private String description;
    private Boolean activedComplements;
    private Integer amountOfComplements;
    private Double price;
    private UniqueIdentifier complementTypeId;
    private List<FeedstockBaseConsumption> feedstockBaseConsumptions;

    @BeforeEach
    void setup() {
        this.name = "pequeno";
        this.description = "200 ML";
        this.activedComplements = true;
        this.amountOfComplements = 4;
        this.price = 9.99;
        this.complementTypeId = UniqueIdentifier.create();
        this.feedstockBaseConsumptions = List.of(FeedstockBaseConsumption.createNew(UniqueIdentifier.create(), 1, UnitOfMeasurement.create(MeasurementType.GRAM, 10.0)));

    }

    @Test
    void should_create_a_serving_size_with_complements() {
        var servingSizeCreated = ServingSize.create(UniqueIdentifier.create(), name, description, activedComplements, amountOfComplements, price, complementTypeId, feedstockBaseConsumptions);

        Assertions.assertNotNull(servingSizeCreated.getId());
    }

    @Test
    void should_create_an_new_serving_size() {
        var servingSizeCreated = ServingSize.createNew(name, description, activedComplements, amountOfComplements, price, complementTypeId, feedstockBaseConsumptions);

        Assertions.assertNotNull(servingSizeCreated.getId());
    }

    @Test
    void should_throw_an_exception_when_has_actived_complements_and_complementTypeId_is_null() {
        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            ServingSize.createNew(name, description, activedComplements, amountOfComplements, price, null, feedstockBaseConsumptions);
        });
        var expectedErrorMessage = "cannot create a serving size with actived complements without id of complement type.";

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
