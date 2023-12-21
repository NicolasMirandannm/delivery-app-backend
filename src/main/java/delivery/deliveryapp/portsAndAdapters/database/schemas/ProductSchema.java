package delivery.deliveryapp.portsAndAdapters.database.schemas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document("product")
public class ProductSchema {
    private String id;
    private String name;
    private Boolean isCustomizable;
    private Boolean isActived;
    private String productCategoryId;
    private List<ServingSizeSchema> servingSizes;

    public ProductSchema(String id, String name, Boolean isCustomizable, Boolean isActived, String productCategoryId, List<ServingSizeSchema> servingSizes) {
        super();
        this.id = id;
        this.name = name;
        this.isCustomizable = isCustomizable;
        this.isActived = isActived;
        this.productCategoryId = productCategoryId;
        this.servingSizes = servingSizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSchema that = (ProductSchema) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(isCustomizable, that.isCustomizable) && Objects.equals(isActived, that.isActived) && Objects.equals(productCategoryId, that.productCategoryId) && Objects.equals(servingSizes, that.servingSizes);
    }

}
