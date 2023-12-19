package delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.IMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.FeedstockBaseConsumptionSchema;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ProductSchema;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ProductMapper implements IMapper<Product, ProductSchema> {

    private final IMapper<ServingSize, ServingSizeSchema> servingSizeMapper;

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
                : productSchema.getServing_sizes().stream().map(this.servingSizeMapper::toDomain).toList();

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
                : product.getServingSizes().stream().map(this.servingSizeMapper::toPersistence).toList();

        return new ProductSchema(id, name, isCustomizable, isActived, productCategoryId, servingSizes);
    }
}
