package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateFeedstockBaseConsumptionDto;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.shared.UniqueIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class CreateFeedstockBaseConsumptionTest {
    @InjectMocks
    private CreateFeedstockBaseConsumption createFeedstockBaseConsumption;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_a_feedstock_base_consumption_from_dto() {
        var feedstockBaseConsumptionDto = new CreateFeedstockBaseConsumptionDto(
                UniqueIdentifier.create().value(),
                1,
                MeasurementType.GRAM,
                10.0
        );

        var feedstockBaseConsumptionCreated = createFeedstockBaseConsumption.create(feedstockBaseConsumptionDto);

        Assertions.assertNotNull(feedstockBaseConsumptionCreated.getIdValue());
        Assertions.assertEquals(feedstockBaseConsumptionCreated.getFeedstockId(), feedstockBaseConsumptionDto.getFeedstockId());
        Assertions.assertEquals(feedstockBaseConsumptionCreated.getQuantity(), feedstockBaseConsumptionDto.getQuantity());
        Assertions.assertEquals(feedstockBaseConsumptionCreated.getConsumptionPerUnity().type(), feedstockBaseConsumptionDto.getMeasureType());
        Assertions.assertEquals(feedstockBaseConsumptionCreated.getConsumptionPerUnity().amount(), feedstockBaseConsumptionDto.getAmount());
    }
}
