package delivery.deliveryapp.infra.database.repository.mapper.implementations.productCategory;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.schemas.ProductCategorySchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper implements Mapper<ProductCategory, ProductCategorySchema> {

    @Override
    public ProductCategory toDomain(ProductCategorySchema schema) {
        InfraException.whenIsNull(schema, "could not map product category schema to domain because schema is null.");

        var id = UniqueIdentifier.createFrom(schema.getId());
        var name = schema.getName();
        var isActive = schema.getIsActive();
        return ProductCategory.create(id, name, isActive);
    }

    @Override
    public ProductCategorySchema toPersistence(ProductCategory category) {
        InfraException.whenIsNull(category, "could not map product category to persistence because aggregate value is null.");

        var id = category.getIdValue();
        var name = category.getCategoryName();
        var isActive = category.isActive();
        return new ProductCategorySchema(id,name,isActive);
    }
}
