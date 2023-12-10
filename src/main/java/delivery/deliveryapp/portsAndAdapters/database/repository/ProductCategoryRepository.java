package delivery.deliveryapp.portsAndAdapters.database.repository;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.repository.IProductCategoryRepository;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.IMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ProductCategorySchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCategoryRepository implements IProductCategoryRepository {

    private final MongoTemplate mongoTemplate;
    private final IMapper<ProductCategory, ProductCategorySchema> mapper;

    @Override
    public void create(ProductCategory aggregateRoot) {

    }

    @Override
    public void update(ProductCategory aggregateRoot) {

    }

    @Override
    public void delete(UniqueIdentifier aggregateRootId) {

    }

    @Override
    public ProductCategory findBy(UniqueIdentifier aggregateRootId) {
        return null;
    }

    @Override
    public List<ProductCategory> findAll() {
        var categoriesSchema = mongoTemplate.findAll(ProductCategorySchema.class);
        return categoriesSchema.stream().map(this.mapper::toDomain).toList();
    }
}
