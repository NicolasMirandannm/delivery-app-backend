package delivery.deliveryapp.application.productCategory;

import delivery.deliveryapp.domain.repository.IProductCategoryRepository;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Executable;

public class CreateProductCategoryTest {

    @InjectMocks
    CreateProductCategory createProductCategory;

    @Mock
    IProductCategoryRepository productCategoryRepository;

    private CreateProductCategoryDto createProductCategoryDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        createProductCategoryDto = new CreateProductCategoryDto("Gelatos");
    }

    @Test
    void should_create_a_product_category() {
        Assertions.assertDoesNotThrow(() -> createProductCategory.create(createProductCategoryDto));
    }

    @Test
    void should_throw_when_dto_is_null() {
        var expectedMessage = "cannot create a product category without data.";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            createProductCategory.create(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
