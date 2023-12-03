package delivery.deliveryapp.domain.product;

import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductTest {

    private String name;
    private Boolean isCustomizable;
    private UniqueIdentifier categoryId;
    private List<ServingSize> servingSizes;

    private Product product;

    @BeforeEach
    void setup() {
        this.name = "Ice cream";
        this.isCustomizable = true;
        this.categoryId = UniqueIdentifier.create();
        this.servingSizes = new ArrayList<ServingSize>();

        this.product = Product.create(UniqueIdentifier.create(), name, isCustomizable, categoryId, servingSizes);
    }

    @Test
    void should_create_a_product() {
        var productCreated = Product.create(UniqueIdentifier.create(), name, isCustomizable, categoryId, servingSizes);

        Assertions.assertNotNull(productCreated.getId());
    }

    @Test
    void should_add_a_servingSize_in_product() {
        var name = "pequeno";
        var description = "200 ML";
        var activedComplements = true;
        var amountOfComplements = 4;
        var complementTypeId = UniqueIdentifier.create();
        var servingSize = ServingSize.createNew(name, description, activedComplements, amountOfComplements, complementTypeId);

        this.product.addAServingSize(servingSize);

        Assertions.assertEquals(1, product.getServingSizes().size());
    }

    @Test
    void should_throw_an_exception_when_add_a_servingSize_null_in_product() {
        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            this.product.addAServingSize(null);
        });
        var expectedErrorMessage = "cannot add an empty serving size in product.";

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Assertions.assertEquals(0, product.getServingSizes().size());
    }
}
