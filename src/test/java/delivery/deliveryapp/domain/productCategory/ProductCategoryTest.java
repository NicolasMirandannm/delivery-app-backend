package delivery.deliveryapp.domain.productCategory;

import delivery.deliveryapp.shared.UniqueIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductCategoryTest {

    @Test
    void should_create_a_product_category() {
        String categoryName = "Gelatos";
        Boolean isActive = true;

        var categoryCreated = ProductCategory.create(UniqueIdentifier.create(), categoryName, isActive);

        Assertions.assertNotNull(categoryCreated.getId());
    }
}
