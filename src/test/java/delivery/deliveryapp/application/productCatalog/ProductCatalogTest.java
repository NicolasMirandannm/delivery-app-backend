package delivery.deliveryapp.application.productCatalog;

import delivery.deliveryapp.domain.builder.ProductBuilder;
import delivery.deliveryapp.domain.builder.ProductCategoryBuilder;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.repository.ProductCategoryRepository;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.domain.services.productCatalog.IProductCatalogService;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class ProductCatalogTest {
    @InjectMocks
    private ProductCatalog productCatalog;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private IProductCatalogService productCatalogService;

    private List<ProductCategory> categories;
    private List<Product> products;
    private List<ProductCatalogItem> catalogItems;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        var categoryName = "Ice creams";
        var productName = "Chocolate Ice cream";
        var category = ProductCategoryBuilder
                .aProductCategory()
                .withCategoryName(categoryName)
                .build();
        var product = ProductBuilder
                .aProduct()
                .withName(productName)
                .build();
        categories = List.of(category);
        products = List.of(product);
        var catalogItem = new ProductCatalogItem();
        var productItem = new ProductItem();
        productItem.setName(productName);
        catalogItem.setCategoryName(categoryName);
        catalogItem.setProducts(List.of(productItem));
        catalogItems = List.of(catalogItem);
    }

    @Test
    void should_load_product_catalog() {
        Mockito.when(productCategoryRepository.findAll()).thenReturn(categories);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        Mockito.when(productCatalogService.assemble(categories, products)).thenReturn(catalogItems);
        var expectedCatalogSize = 1;

        var productCatalogItems = productCatalog.show();
        var catologSize = productCatalogItems.size();

        Assertions.assertEquals(expectedCatalogSize, catologSize);
    }

    @Test
    void should_an_empty_list_when_categories_repository_returns_null() {
        Mockito.when(productCategoryRepository.findAll()).thenReturn(null);

        var catalogItems = productCatalog.show();

        Assertions.assertTrue(catalogItems.isEmpty());
    }

    @Test
    void should_an_empty_list_when_product_repository_returns_null() {
        Mockito.when(productRepository.findAll()).thenReturn(null);

        var catalogItems = productCatalog.show();

        Assertions.assertTrue(catalogItems.isEmpty());
    }
}
