package delivery.deliveryapp.domain.repository;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.shared.ddd.Repository;

public interface ProductRepository extends Repository<Product> {
    public Product findByName(String name);
}
