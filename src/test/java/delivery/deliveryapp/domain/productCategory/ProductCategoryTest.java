package delivery.deliveryapp.domain.productCategory;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductCategoryTest {

    private String categoryName;

    @BeforeEach
    void setup() {
        categoryName = "Gelatos";
    }

    @Test
    void should_create_a_product_category() {
        Boolean isActive = true;

        var categoryCreated = ProductCategory.create(UniqueIdentifier.create(), categoryName, isActive);

        Assertions.assertNotNull(categoryCreated.getId());
    }

    @Test
    void should_create_a_new_product_category() {
        var productCategory = ProductCategory.createNew(categoryName);

        Assertions.assertNotNull(productCategory);
    }

    @Test
    void should_throw_an_exception_when_categoryName_is_null_on_new_creation() {
        var expectedMessage = "cannot create a product category with an empty name";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            ProductCategory.createNew(null);
        });

        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
