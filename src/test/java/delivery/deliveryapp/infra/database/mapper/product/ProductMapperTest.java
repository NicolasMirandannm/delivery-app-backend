package delivery.deliveryapp.infra.database.mapper.product;

import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.repository.mapper.implementations.product.ProductMapper;
import delivery.deliveryapp.infra.database.schemas.ProductSchema;
import delivery.deliveryapp.infra.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

public class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Mock
    private Mapper<ServingSize, ServingSizeSchema> servingSizeMapper;

    private Product product;
    private ProductSchema productSchema;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        product = ProductBuilder
                .aProduct()
                .withName("product")
                .build();

        var productId = product.getIdValue();
        var productName = product.getName();
        var productDescription = product.getDescription();
        var productImageURI = product.getImageURI();
        var productIsCustomizable = product.getIsCustomizable();
        var productIsActived = product.getIsActived();
        var productCategoryId = product.getProductCategoryId();

        productSchema = new ProductSchema(productId, productName, productDescription, productImageURI, productIsCustomizable, productIsActived, productCategoryId, new ArrayList<>());

    }

    @Test
    void should_map_procut_schema_to_domain_with_empty_serving_sizes_when_serving_size_schema_is_null() {
        productSchema.setServingSizes(null);

        var productMapped = productMapper.toDomain(productSchema);

        Assertions.assertTrue(productMapped.getServingSizes().isEmpty());
    }

    @Test
    void should_throw_an_error_when_map_schema_document_to_domain_with_null_schema() {
        var expectedMessage = "could not map product schema to domain because schema is null.";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            productMapper.toDomain(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_map_domain_to_persistence() {
        var productSchemaMapped = productMapper.toPersistence(product);

        Assertions.assertEquals(productSchema, productSchemaMapped);
    }

    @Test
    void should_map_domain_procut_to_schema_with_empty_serving_sizes_when_serving_size_is_null() {
        product.setServingSizes(null);

        var productSchemaMapped = productMapper.toPersistence(product);

        Assertions.assertTrue(productSchemaMapped.getServingSizes().isEmpty());
    }

    @Test
    void should_throw_an_error_when_map_domain_entity_to_schema_with_null_product() {
        var expectedMessage = "could not map product entity to schema because product is null.";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            productMapper.toPersistence(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

}
