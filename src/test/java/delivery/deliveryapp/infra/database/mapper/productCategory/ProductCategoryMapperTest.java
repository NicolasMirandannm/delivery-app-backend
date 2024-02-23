package delivery.deliveryapp.infra.database.mapper.productCategory;

import delivery.deliveryapp.domain.builder.ProductCategoryBuilder;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.infra.database.repository.mapper.implementations.productCategory.ProductCategoryMapper;
import delivery.deliveryapp.infra.database.schemas.ProductCategorySchema;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class ProductCategoryMapperTest {

    @InjectMocks
    private ProductCategoryMapper productCategoryMapper;

    private ProductCategory category;
    private ProductCategorySchema categorySchema;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        category = ProductCategoryBuilder
            .aProductCategory()
            .withCategoryName("Category")
            .build();

        var id = category.getIdValue();
        var name = category.getCategoryName();
        var isActive = category.isActive();
        categorySchema = new ProductCategorySchema(id,name,isActive);
    }

    @Test
    void should_map_schema_to_domain() {
        var categoryMapped = productCategoryMapper.toDomain(categorySchema);

        Assertions.assertEquals(category, categoryMapped);
    }

    @Test
    void should_throw_an_error_when_schema_is_null_for_to_domain() {
        var expectedMessage = "could not map product category schema to domain because schema is null.";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            productCategoryMapper.toDomain(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void should_map_domain_to_schema() {
        var categorySchemaMapped = productCategoryMapper.toPersistence(category);

        Assertions.assertEquals(categorySchema, categorySchemaMapped);
    }

    @Test
    void should_throw_an_error_when_schema_is_null_for_to_persistence() {
        var expectedMessage = "could not map product category to persistence because aggregate value is null.";

        InfraException exception = Assertions.assertThrows(InfraException.class, () -> {
            productCategoryMapper.toPersistence(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
