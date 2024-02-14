package delivery.deliveryapp.application.productCatalog;

import delivery.deliveryapp.domain.repository.ProductCategoryRepository;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.domain.services.productCatalog.IProductCatalogService;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCatalog implements IProductCatalog {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final IProductCatalogService productCatalogService;

    public List<ProductCatalogItem> show() {
        var categories = productCategoryRepository.findAll();
        var products = productRepository.findAll();
        if (categories == null || products == null)
            return new ArrayList<>();
        return productCatalogService.assemble(categories, products);
    }
}
