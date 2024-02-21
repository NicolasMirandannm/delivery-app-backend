package delivery.deliveryapp.domain.services.productCatalog;

import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.builder.ProductCategoryBuilder;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductItem;
import delivery.deliveryapp.domain.services.productCatalog.dto.ServingSizeItem;
import delivery.deliveryapp.shared.UniqueIdentifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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
                .withServingSizes()
                .build();
        productCategories = List.of(category, inactiveCategory);
        products = List.of(product);
    }

    @Test
    void should_load_category_of_products() {
        var servingSize = products.get(0).getServingSizes().get(0);
        var product = products.get(0);
        var servingSizeItem = new ServingSizeItem(
            servingSize.getName(),
            servingSize.getPrice()
        );
        var productItem = new ProductItem(
            product.getIdValue(),
            product.getName(),
            product.getDescription(),
            product.getImageURI(),
            List.of(servingSizeItem)
        );
        var productCatalogExpected = List.of(new ProductCatalogItem(
            productCategories.get(0).getIdValue(),
            productCategories.get(0).getCategoryName(),
            List.of(productItem)
        ));

        var catalog = productCatalogService.assemble(productCategories, products);

        Assertions.assertEquals(productCatalogExpected, catalog);
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

    @Test
    void should_return_an_empty_list_when_no_categories_exist() {
        var catalogItems = productCatalogService.assemble(new ArrayList<>(), new ArrayList<>());

        Assertions.assertTrue(catalogItems.isEmpty());
    }

    @Test
    void should_not_vincule_product_to_category_when_product_has_no_category_valid() {
        var invalidCategoryId = UniqueIdentifier.create();
        products.get(0).setProductCategoryId(invalidCategoryId);
        
        var catalogs = productCatalogService.assemble(productCategories, products);

        Assertions.assertTrue(catalogs.get(0).getProducts().isEmpty());
    }
}
