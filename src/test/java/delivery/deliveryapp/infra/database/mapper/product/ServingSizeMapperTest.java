package delivery.deliveryapp.infra.database.mapper.product;

import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ProductFeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.repository.mapper.implementations.product.ServingSizeMapper;
import delivery.deliveryapp.infra.database.schemas.ComplementCategorySchema;
import delivery.deliveryapp.infra.database.schemas.FeedstockBaseConsumptionSchema;
import delivery.deliveryapp.infra.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.domain.builder.ServingSizeBuilder;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ServingSizeMapperTest {

    @InjectMocks
    private ServingSizeMapper servingSizeMapper;

    @Mock
    private Mapper<ProductFeedstockBaseConsumption, FeedstockBaseConsumptionSchema> feedstockBaseConsumptionMapper;

    private ServingSize servingSize;
    private ServingSizeSchema servingSizeSchema;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        servingSize = ServingSizeBuilder
                .aServingSize()
                .withId()
                .withFeedstocksBaseConsumption()
                .build();

        var sizeId = servingSize.getIdValue();
        var sizeName = servingSize.getName();
        var sizeDescription = servingSize.getDescription();
        var sizeHasActivedComplements = servingSize.getActivedComplements();
        var sizePrice = servingSize.getPrice();
        var complementCategories = new ArrayList<ComplementCategorySchema>();
        var feedstockBaseConsumption = new FeedstockBaseConsumptionSchema("id", "feedstockId", 1, MeasurementType.GRAM, 10.0);
        servingSizeSchema = new ServingSizeSchema(sizeId,sizeName,sizeDescription,sizeHasActivedComplements, sizePrice, complementCategories, List.of(feedstockBaseConsumption));

        Mockito.when(feedstockBaseConsumptionMapper.toDomain(feedstockBaseConsumption)).thenReturn(servingSize.getFeedstocksBaseConsumption().get(0));
        Mockito.when(feedstockBaseConsumptionMapper.toPersistence(servingSize.getFeedstocksBaseConsumption().get(0))).thenReturn(servingSizeSchema.getFeedstockBaseConsumptions().get(0));
    }

    @Test
    void should_map_schema_to_domain_entity() {
        var servingSizeMapped = servingSizeMapper.toDomain(servingSizeSchema);

        Assertions.assertEquals(servingSize, servingSizeMapped);
    }

    @Test
    void should_throw_an_exception_when_schema_is_null() {
        var expectedMessage = "serving size schema is null";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            servingSizeMapper.toDomain(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_map_serving_size_entity_to_persistence() {
        var servingSizeSchemaMapped = servingSizeMapper.toPersistence(servingSize);

        Assertions.assertEquals(servingSizeSchema, servingSizeSchemaMapped);
    }

    @Test
    void should_throw_an_exception_when_servingSize_entity_is_null() {
        var expectedMessage = "serving size entity is null";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            servingSizeMapper.toPersistence(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
