package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateComplementCategoryDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
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
    private CreationService<CreateComplementCategoryDto, ComplementCategory> createComplementCategory;

    private CreateServingSizeDto servingSizeDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        servingSizeDto = new CreateServingSizeDto(
                "pequeno",
                "tamanho pequeno",
                false,
                9.99,
                new ArrayList<CreateComplementCategoryDto>()
        );
    }

    @Test
    void should_create_a_serving_size_from_dto() {
        var servingSizeCreated = createServingSize.create(servingSizeDto);

        Assertions.assertNotNull(servingSizeCreated.getIdValue());
        Assertions.assertEquals(servingSizeDto.getName(), servingSizeCreated.getName());
        Assertions.assertEquals(servingSizeDto.getDescription(), servingSizeCreated.getDescription());
        Assertions.assertEquals(servingSizeDto.getComplementsIsActive(), servingSizeCreated.getActivedComplements());
        Assertions.assertEquals(servingSizeCreated.getComplementCategories().size(), 0);
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
    void should_throw_an_exception_when_complement_categories_is_null() {
        var expectedMessage = "servingSizeDto has a null complement categories.";
        
        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            servingSizeDto.setComplementCategories(null);
            createServingSize.create(servingSizeDto);
        });
        
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
