package delivery.deliveryapp.application.productCategory;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.repository.ProductCategoryRepository;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CreateProductCategoryTest {

    @InjectMocks
    CreateProductCategory createProductCategory;

    @Mock
    ProductCategoryRepository productCategoryRepository;

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

    @Test
    void should_throw_when_product_category_already_existis() {
        var productCategory = ProductCategory.createNew(createProductCategoryDto.getCategoryName());
        Mockito.when(productCategoryRepository.findByName(createProductCategoryDto.getCategoryName())).thenReturn(productCategory);
        var expectedMessage = "A product category already exists with the same name";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            createProductCategory.create(createProductCategoryDto);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
