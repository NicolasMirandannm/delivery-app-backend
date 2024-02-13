package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateComplementCategoryDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateFeedstockBaseConsumptionDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.enums.MeasurementType;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.repository.IProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


public class CreateProductTest {
    @InjectMocks
    private CreateProduct createProduct;

    @Mock
    private IProductRepository productRepository;

    @Mock
    private CreationService<CreateServingSizeDto, ServingSize> createServingSizeService;

    private Product product;
    private CreateProductDto createProductDto;
    private CreateServingSizeDto servingSizeDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        product = ProductBuilder
                .aProduct()
                .withName("Mongo Ice cream")
                .withServingSizes()
                .build();

        var categoryId = UniqueIdentifier.create().value();
        var servingSize = product.getServingSizes().get(0);
        var feedstocksBaseConsumption = List.of(new CreateFeedstockBaseConsumptionDto(UniqueIdentifier.create().value(), 1, MeasurementType.GRAM, 10.0));
        servingSizeDto = new CreateServingSizeDto(
                servingSize.getName(),servingSize.getDescription(),servingSize.getActivedComplements(),
                servingSize.getPrice(), new ArrayList<CreateComplementCategoryDto>(), feedstocksBaseConsumption
        );
        createProductDto = new CreateProductDto("Mongo Ice cream", "product description", categoryId, List.of(servingSizeDto));
        createProductDto.setImageURI("uri image");
    }

    @Test
    void should_create_a_product() {
        Mockito.when(createServingSizeService.create(servingSizeDto)).thenReturn(product.getServingSizes().get(0));

        var productCreated = createProduct.create(createProductDto);

        Assertions.assertEquals(product.getName(), productCreated.getName());;
    }

    @Test
    void should_throw_an_exception_when_dto_is_null() {
        var expectedMessage = "cannot create a product without product creation data.";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            createProduct.create(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_product_with_the_same_name_already_exists() {
        Mockito.when(productRepository.findByName(product.getName())).thenReturn(product);
        var expectedMessage = "A product already exists with the same name";

        ApplicationException exception = Assertions.assertThrows(ApplicationException.class, () -> {
            createProduct.create(createProductDto);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
