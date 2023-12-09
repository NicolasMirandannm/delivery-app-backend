package delivery.deliveryapp.domain.services.productCatalog;

import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.builder.ProductCategoryBuilder;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ProductCatalogServiceTest {

    @InjectMocks
    private ProductCatalogService productCatalogService;
    private List<ProductCategory> productCategories;
    private List<Product> products;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        var category = ProductCategoryBuilder
                .aProductCategory()
                .withCategoryName("Ice creams")
                .build();
        var inactiveCategory = ProductCategoryBuilder
                .aProductCategory()
                .withCategoryName("Gelatos")
                .withIsActive(false)
                .build();

        var product = ProductBuilder
                .aProduct()
                .withIsCustomizable(false)
                .withName("Chocolate Ice cream")
                .withProductCategoryId(category.getId())
                .build();
        productCategories = List.of(category, inactiveCategory);
        products = List.of(product);
    }

    @Test
    void should_load_category_of_products() {
        var expectedCatalogName = "Ice creams";

        var catalogs = productCatalogService.assemble(productCategories, products);
        var categoryName = catalogs.get(0).getCategoryName();

        Assertions.assertEquals(expectedCatalogName, categoryName);
    }

    @Test
    void should_not_load_inactived_category_of_products() {
        var inactivedCategoryName = "Gelatos";

        var catalogs = productCatalogService.assemble(productCategories, products);
        var inactivedCategoryHasBeenLoad = catalogs.stream().anyMatch(catalog -> {
            return catalog.getCategoryName().equals(inactivedCategoryName);
        });

        Assertions.assertFalse(inactivedCategoryHasBeenLoad);
    }

    @Test
    void should_load_products_mapped_by_productCategoryId() {
        var expectedProductName = "Chocolate Ice cream";

        var catalogs = productCatalogService.assemble(productCategories, products);
        var productName = catalogs.get(0).getProducts().get(0).getName();

        Assertions.assertEquals(expectedProductName, productName);
    }

    @Test
    void should_not_load_products_inactived() {
        products.get(0).setIsActived(false);

        var catalogs = productCatalogService.assemble(productCategories, products);
        var products = catalogs.get(0).getProducts();

        Assertions.assertTrue(products.isEmpty());
    }

}
