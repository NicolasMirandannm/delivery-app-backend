package delivery.deliveryapp.infra.database.repository;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.schemas.ProductSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImplementation implements ProductRepository {

    private final MongoTemplate mongoTemplate;
    private final Mapper<Product, ProductSchema> mapper;

    @Override
    public void create(Product product) {
        var productSchema = mapper.toPersistence(product);
        mongoTemplate.save(productSchema);
    }

    @Override
    public void update(Product product) {
        var productSchema = mapper.toPersistence(product);
        mongoTemplate.save(productSchema);
    }

    @Override
    public void deleteBy(UniqueIdentifier productId) {
        var productSchema = mongoTemplate.findById(productId.value(), ProductSchema.class);
        if (productSchema != null)
            mongoTemplate.remove(productSchema);
    }

    @Override
    public Product findBy(UniqueIdentifier productId) {
        var productSchema = mongoTemplate.findById(productId.value(), ProductSchema.class);
        return productSchema != null
                ? mapper.toDomain(productSchema)
                : null;
    }

    @Override
    public List<Product> findAll() {
        var products = mongoTemplate.findAll(ProductSchema.class);
        return products.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Product findByName(String name) {
        var query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        var productFound = mongoTemplate.findOne(query, ProductSchema.class);
        return productFound != null
                ? mapper.toDomain(productFound)
                : null;
    }
}
