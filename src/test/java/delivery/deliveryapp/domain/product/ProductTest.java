package delivery.deliveryapp.domain.product;

import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import delivery.deliveryapp.domain.builder.ServingSizeBuilder;

import java.util.ArrayList;
import java.util.List;

public class ProductTest {

    private String name;
    private String description;
    private String imageURI;
    private Boolean isCustomizable;
    private UniqueIdentifier categoryId;
    private List<ServingSize> servingSizes;
    private Boolean isActive;

    private Product product;

    @BeforeEach
    void setup() {
        this.name = "Ice cream";
        this.description = "ice cream description";
        this.imageURI = "/path/image";
        this.isCustomizable = true;
        this.categoryId = UniqueIdentifier.create();
        this.servingSizes = new ArrayList<ServingSize>();
        this.isActive = true;

        this.product = Product.create(UniqueIdentifier.create(), name, description, imageURI, isCustomizable, categoryId, servingSizes, isActive);
    }

    @Test
    void should_create_a_product() {
        var productCreated = Product.create(UniqueIdentifier.create(), name, description, imageURI, isCustomizable, categoryId, servingSizes, isActive);

        Assertions.assertNotNull(productCreated.getId());
    }

    @Test
    void should_add_a_servingSize_in_product() {
        var servingSize = ServingSizeBuilder.aServingSize().build();

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

    @Test
    void should_create_a_new_product() {
        var newProductCreated = Product.createNew(name, description, true, imageURI, categoryId);

        Assertions.assertNotNull(newProductCreated);
        Assertions.assertTrue(newProductCreated.getIsCustomizable());
        Assertions.assertTrue(newProductCreated.getIsActived());
        Assertions.assertTrue(newProductCreated.getServingSizes().isEmpty());
    }

    @Test
    void should_throw_an_exception_when_name_is_null_on_new_creation() {
        var expectedErrorMessage = "cannot create a new product with empty name.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            Product.createNew(null, description, false, imageURI, categoryId);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_productCategoryId_is_null_on_new_creation() {
        var expectedErrorMessage = "cannot create a new product without a product category identifier.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            Product.createNew(name, description, false, imageURI, null);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_description_is_null_on_new_creation() {
        var expectedErrorMessage = "cannot create a new product with empty description.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            Product.createNew(name, null, false, imageURI, categoryId);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    void should_throw_an_exception_when_imageURI_is_null_on_new_creation() {
        var expectedErrorMessage = "cannot create a new product with empty image path.";

        DomainException exception = Assertions.assertThrows(DomainException.class, () -> {
            Product.createNew(name, description, false, null, categoryId);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
    }

}
