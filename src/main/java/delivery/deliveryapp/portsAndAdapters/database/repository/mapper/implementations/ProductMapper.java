package delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.IMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ProductSchema;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ProductMapper implements IMapper<Product, ProductSchema> {

    @Override
    public Product toDomain(ProductSchema productSchema) {
        InfraException.whenIsNull(productSchema, "could not map product schema to domain because schema is null.");

        var id = UniqueIdentifier.createFrom(productSchema.getId());
        var name = productSchema.getName();
        var isCustomizable = productSchema.getIs_customizable();
        var isActived = productSchema.getIs_actived();
        var productCategoryId = UniqueIdentifier.createFrom(productSchema.getProduct_category_id());
        var servingSizes = productSchema.getServing_sizes() == null
                ? new ArrayList<ServingSize>()
                : productSchema.getServing_sizes().stream().map(this::assembleServingSize).toList();

        return Product.create(id, name, isCustomizable, productCategoryId, servingSizes, isActived);
    }

    @Override
    public ProductSchema toPersistence(Product product) {
        InfraException.whenIsNull(product, "could not map product entity to schema because product is null.");

        var id = product.getIdValue();
        var name = product.getName();
        var isCustomizable = product.getIsCustomizable();
        var isActived = product.getIsActived();
        var productCategoryId = product.getProductCategoryId().value();
        var servingSizes = product.getServingSizes() == null
                ? new ArrayList<ServingSizeSchema>()
                : product.getServingSizes().stream().map(this::assembleServingSizeSchema).toList();

        return new ProductSchema(id, name, isCustomizable, isActived, productCategoryId, servingSizes);
    }

    private ServingSizeSchema assembleServingSizeSchema(ServingSize servingSize) {
        var id = servingSize.getIdValue();
        var name = servingSize.getName();
        var description = servingSize.getDescription();
        var complementsIsActived = servingSize.getActivedComplements();
        var amountOfComplements = servingSize.getAmountOfComplements();
        var complementCategoryId = servingSize.getComplementCategoryId().value();

        return new ServingSizeSchema(id, name, description, complementsIsActived, amountOfComplements, complementCategoryId);
    }

    private ServingSize assembleServingSize(ServingSizeSchema servingSizeSchema) {
        var id = UniqueIdentifier.createFrom(servingSizeSchema.getId());
        var name = servingSizeSchema.getName();
        var description = servingSizeSchema.getDescription();
        var isActive = servingSizeSchema.getActived_complements();
        var amountOfComplements = servingSizeSchema.getAmount_of_complements();
        var complementCategoryId = UniqueIdentifier.createFrom(servingSizeSchema.getComplement_category_id());
        return ServingSize.create(id, name, description, isActive, amountOfComplements, complementCategoryId);
    }
}
