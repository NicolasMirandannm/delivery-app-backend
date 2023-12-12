package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.builder.ProductBuilder;

import delivery.deliveryapp.domain.repository.IProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


public class CreateProductTest {
    @InjectMocks
    private CreateProduct createProduct;

    @Mock
    private IProductRepository productRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_a_product() {
        var categoryId = UniqueIdentifier.create().value();
        var productExpected = ProductBuilder
                .aProduct()
                .withName("Mongo Ice cream")
                .build();
        var servingSize = productExpected.getServingSizes().get(0);
        var servingSizeDto = new CreateServingSizeDto(servingSize.getName(),servingSize.getDescription(),servingSize.getActivedComplements(),servingSize.getAmountOfComplements(),servingSize.getIdValue());
        var productCreationDto = new CreateProductDto("Mongo Ice cream", categoryId, List.of(servingSizeDto));

        var productCreated = createProduct.create(productCreationDto);

        Assertions.assertEquals(productExpected.getName(), productCreated.getName());
        Assertions.assertEquals(productExpected.getServingSizes().size(), productCreated.getServingSizes().size());
    }

    @Test
    void should_throw_an_exception_when_dto_is_null() {
        var expectedMessage = "cannot create a product without product creation data.";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            createProduct.create(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
