package delivery.deliveryapp.infra.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.schemas.ProductSchema;
import delivery.deliveryapp.infra.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ProductMapper implements Mapper<Product, ProductSchema> {

    private final Mapper<ServingSize, ServingSizeSchema> servingSizeMapper;

    @Override
    public Product toDomain(ProductSchema productSchema) {
        InfraException.whenIsNull(productSchema, "could not map product schema to domain because schema is null.");

        var id = UniqueIdentifier.createFrom(productSchema.getId());
        var name = productSchema.getName();
        var description = productSchema.getDescription();
        var imageURI = productSchema.getImageURI();
        var isCustomizable = productSchema.getIsCustomizable();
        var isActived = productSchema.getIsActived();
        var productCategoryId = UniqueIdentifier.createFrom(productSchema.getProductCategoryId());
        var servingSizes = productSchema.getServingSizes() == null
                ? new ArrayList<ServingSize>()
                : productSchema.getServingSizes().stream().map(this.servingSizeMapper::toDomain).toList();

        return Product.create(id, name, description, imageURI, isCustomizable, productCategoryId, servingSizes, isActived);
    }

    @Override
    public ProductSchema toPersistence(Product product) {
        InfraException.whenIsNull(product, "could not map product entity to schema because product is null.");

        var id = product.getIdValue();
        var name = product.getName();
        var description = product.getDescription();
        var imageURI = product.getImageURI();
        var isCustomizable = product.getIsCustomizable();
        var isActived = product.getIsActived();
        var productCategoryId = product.getProductCategoryId();
        var servingSizes = product.getServingSizes() == null
                ? new ArrayList<ServingSizeSchema>()
                : product.getServingSizes().stream().map(this.servingSizeMapper::toPersistence).toList();

        return new ProductSchema(id, name, description, imageURI, isCustomizable, isActived, productCategoryId, servingSizes);
    }
}
