package delivery.deliveryapp.portsAndAdapters.database.mapper.product;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ProductFeedstockBaseConsumption;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations.product.FeedstockBaseConsumptionMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.FeedstockBaseConsumptionSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class FeedstockBaseConsumptionMapperTest {

    @InjectMocks
    private FeedstockBaseConsumptionMapper feedstockBaseConsumptionMapper;

    private FeedstockBaseConsumptionSchema feedstockBaseConsumptionSchema;
    private ProductFeedstockBaseConsumption feedstockBaseConsumption;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        feedstockBaseConsumptionSchema = new FeedstockBaseConsumptionSchema(
                UniqueIdentifier.create().value(),
                UniqueIdentifier.create().value(),
                1,
                MeasurementType.GRAM,
                10.0
        );
        feedstockBaseConsumption = ProductFeedstockBaseConsumption.create(
                UniqueIdentifier.createFrom(feedstockBaseConsumptionSchema.getId()),
                UniqueIdentifier.createFrom(feedstockBaseConsumptionSchema.getFeedstockId()),
                feedstockBaseConsumptionSchema.getQuantity(),
                UnitOfMeasurement.create(feedstockBaseConsumptionSchema.getMeasureType(), feedstockBaseConsumptionSchema.getAmount())
        );
    }

    @Test
    void should_map_feedstock_base_consumption_schema_to_domain() {
        var feedstockBaseConsumptionMapped = feedstockBaseConsumptionMapper.toDomain(feedstockBaseConsumptionSchema);

        Assertions.assertEquals(feedstockBaseConsumption, feedstockBaseConsumptionMapped);
    }

    @Test
    void should_throw_an_infra_exception_when_feedstockBaseConsumptionSchema_is_null() {
        var expectedMessage = "feedstock base consumption schema is null";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            feedstockBaseConsumptionMapper.toDomain(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_map_schema_to_domain_entity() {
        var feedstockBaseConsumptionSchemaMapped = feedstockBaseConsumptionMapper.toPersistence(feedstockBaseConsumption);

        Assertions.assertEquals(feedstockBaseConsumptionSchema, feedstockBaseConsumptionSchemaMapped);
    }

    @Test
    void should_throw_an_infra_exception_when_feedstockBaseConsumption_entity_is_null() {
        var expectedMessage = "feedstock base consumption entity is null";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            feedstockBaseConsumptionMapper.toPersistence(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
