package delivery.deliveryapp.portsAndAdapters.database.mapper;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations.ProductMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ProductSchema;
import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    private Product product;
    private ProductSchema productSchema;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        product = ProductBuilder
                .aProduct()
                .withName("product")
                .build();

        var servingSizeSchema = getServingSizeSchema();

        var productId = product.getIdValue();
        var productName = product.getName();
        var productIsCustomizable = product.getIsCustomizable();
        var productIsActived = product.getIsActived();
        var productCategoryId = product.getProductCategoryId().value();
        var productServingSizes = List.of(servingSizeSchema);

        productSchema = new ProductSchema(productId, productName, productIsCustomizable, productIsActived, productCategoryId, productServingSizes);

    }

    private ServingSizeSchema getServingSizeSchema() {
        var servingSize = product.getServingSizes().get(0);
        var sizeId = servingSize.getIdValue();
        var sizeName = servingSize.getName();
        var sizeDescription = servingSize.getDescription();
        var sizeHasActivedComplements = servingSize.getActivedComplements();
        var sizeAmountOfComplements = servingSize.getAmountOfComplements();
        var sizeComplementCategoryId = servingSize.getComplementCategoryId().value();
        return new ServingSizeSchema(sizeId, sizeName, sizeDescription, sizeHasActivedComplements, sizeAmountOfComplements, sizeComplementCategoryId);
    }

    @Test
    void should_map_procut_schema_to_domain_with_empty_serving_sizes_when_serving_size_schema_is_null() {
        productSchema.setServing_sizes(null);

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

        Assertions.assertTrue(productSchemaMapped.getServing_sizes().isEmpty());
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
