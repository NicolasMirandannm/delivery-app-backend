package delivery.deliveryapp.application.product.find;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.IRepository;
import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class FindProductServiceTest {

    @InjectMocks
    private FindProductService findProductService;

    @Mock
    private IRepository<Product> productRepository;

    private Product product;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        product = ProductBuilder.aProduct().build();
    }

    @Test
    void should_parser_string_id_to_unique_identifier_to_find_product_by_id() {
        var id = UniqueIdentifier.create().value();
        var idParsedExpected = UniqueIdentifier.createFrom(id);
        Mockito.when(productRepository.findBy(idParsedExpected)).thenReturn(product);

        var productFound = findProductService.findBy(id);

        Assertions.assertEquals(product, productFound);
    }

    @Test
    void should_throw_an_exception_when_id_is_null() {
        var expectedMessage = "cannot find product with empty id.";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
           findProductService.findBy(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
