package delivery.deliveryapp.domain.repository;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.shared.ddd.Repository;

public interface ProductCategoryRepository extends Repository<ProductCategory> {
    public ProductCategory findByName(String name);
}
