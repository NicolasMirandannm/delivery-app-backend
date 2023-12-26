package delivery.deliveryapp.application.productCatalog;

import delivery.deliveryapp.domain.repository.IProductCategoryRepository;
import delivery.deliveryapp.domain.repository.IProductRepository;
import delivery.deliveryapp.domain.services.productCatalog.IProductCatalogService;
import delivery.deliveryapp.domain.services.productCatalog.dto.ProductCatalogItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCatalog implements IProductCatalog {
    private final IProductCategoryRepository productCategoryRepository;
    private final IProductRepository productRepository;
    private final IProductCatalogService productCatalogService;

    public List<ProductCatalogItem> show() {
        var categories = productCategoryRepository.findAll();
        var products = productRepository.findAll();
        if (categories == null || products == null)
            return new ArrayList<>();
        return productCatalogService.assemble(categories, products);
    }
}
