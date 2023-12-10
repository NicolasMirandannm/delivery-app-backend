package delivery.deliveryapp.portsAndAdapters.database.repository;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.repository.IProductRepository;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.IMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ProductSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepository implements IProductRepository {

    private final MongoTemplate mongoTemplate;
    private final IMapper<Product, ProductSchema> mapper;

    @Override
    public void create(Product aggregateRoot) {

    }

    @Override
    public void update(Product aggregateRoot) {

    }

    @Override
    public void delete(UniqueIdentifier aggregateRootId) {

    }

    @Override
    public Product findBy(UniqueIdentifier aggregateRootId) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        var products = mongoTemplate.findAll(ProductSchema.class);
        return products.stream().map(mapper::toDomain).toList();
    }
}
