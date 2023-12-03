package delivery.deliveryapp.domain.complementCategory;

import delivery.deliveryapp.domain.complementCategory.entities.ComplementItem;
import delivery.deliveryapp.shared.UniqueIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ComplementCategoryTest {

    private String categoryName;
    private UniqueIdentifier productId;
    private List<ComplementItem> complements;

    @BeforeEach
    void setup() {
        this.categoryName = "Frutas";
        this.productId = UniqueIdentifier.create();
        this.complements = new ArrayList<ComplementItem>();
    }

    @Test
    void should_create_a_complement_category() {
        var complementCategoryCreated = ComplementCategory.create(UniqueIdentifier.create(), categoryName, productId, complements);

        Assertions.assertNotNull(complementCategoryCreated.getId());
    }
}
