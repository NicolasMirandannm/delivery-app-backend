package delivery.deliveryapp.infra.database.repository;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.repository.ProductCategoryRepository;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.schemas.ProductCategorySchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductCategoryRepositoryImplementation implements ProductCategoryRepository {

    private final MongoTemplate mongoTemplate;
    private final Mapper<ProductCategory, ProductCategorySchema> mapper;

    @Override
    public void create(ProductCategory productCategory) {
        var categorySchema = mapper.toPersistence(productCategory);
        mongoTemplate.save(categorySchema);
    }

    @Override
    public void update(ProductCategory productCategory) {
        var categorySchema = mapper.toPersistence(productCategory);
        mongoTemplate.save(categorySchema);

    }

    @Override
    public void deleteBy(UniqueIdentifier identifier) {
        var category = mongoTemplate.findById(identifier.value(), ProductCategorySchema.class);
        if (category != null)
            mongoTemplate.remove(category);
    }

    @Override
    public ProductCategory findBy(UniqueIdentifier identifier) {
        var categorySchema = mongoTemplate.findById(identifier.value(), ProductCategorySchema.class);
        return categorySchema == null
                ? null
                : mapper.toDomain(categorySchema);
    }

    @Override
    public List<ProductCategory> findAll() {
        var categoriesSchema = mongoTemplate.findAll(ProductCategorySchema.class);
        return categoriesSchema.stream().map(this.mapper::toDomain).toList();
    }

    @Override
    public ProductCategory findByName(String name) {
        var query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        var productCategoryFound = mongoTemplate.findOne(query, ProductCategorySchema.class);
        return productCategoryFound != null
                ? mapper.toDomain(productCategoryFound)
                : null;
    }
}
