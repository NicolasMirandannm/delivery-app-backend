package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateFeedstockBaseConsumptionDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.complementCategory.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class CreateServingSizeTest {

    @InjectMocks
    private CreateServingSize createServingSize;

    @Mock
    private CreateFeedstockBaseConsumption createFeedstockBaseConsumption;

    private CreateServingSizeDto servingSizeDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        var feedstocksBaseConsumptionDto = new CreateFeedstockBaseConsumptionDto(UniqueIdentifier.create().value(), 1, MeasurementType.GRAM, 10.0);
        servingSizeDto = new CreateServingSizeDto(
                "pequeno",
                "tamanho pequeno",
                false,
                0,
                null,
                List.of(feedstocksBaseConsumptionDto)
        );

        FeedstockBaseConsumption feedstockBaseConsumption = FeedstockBaseConsumption.createNew(
                UniqueIdentifier.create(),
                1,
                UnitOfMeasurement.create(MeasurementType.GRAM, 10.0)
        );
        Mockito.when(createFeedstockBaseConsumption.create(feedstocksBaseConsumptionDto)).thenReturn(feedstockBaseConsumption);
    }

    @Test
    void should_create_a_serving_size_from_dto() {
        var servingSizeCreated = createServingSize.create(servingSizeDto);

        Assertions.assertNotNull(servingSizeCreated.getIdValue());
        Assertions.assertEquals(servingSizeDto.getName(), servingSizeCreated.getName());
        Assertions.assertEquals(servingSizeDto.getDescription(), servingSizeCreated.getDescription());
        Assertions.assertEquals(servingSizeDto.getComplementsIsActive(), servingSizeCreated.getActivedComplements());
        Assertions.assertEquals(servingSizeDto.getAmountOfComplements(), servingSizeCreated.getAmountOfComplements());
        Assertions.assertNull(servingSizeCreated.getComplementCategoryId());
        Assertions.assertEquals(1, servingSizeCreated.getFeedstocksBaseConsumption().size());
    }

    @Test
    void should_throw_an_exception_when_serving_size_is_null() {
        var expectedMessage = "serving size dto is null";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            createServingSize.create(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_serving_size_has_a_null_feedstocksBaseConsumptionDto() {
        var expectedMessage = "servingSizeDto has a null feedstocksBaseConsumptionDto";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            servingSizeDto.setFeedstocksBaseConsumptions(null);
            createServingSize.create(servingSizeDto);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
