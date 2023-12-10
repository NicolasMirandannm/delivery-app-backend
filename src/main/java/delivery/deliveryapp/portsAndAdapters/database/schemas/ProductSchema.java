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
    private Boolean is_customizable;
    private Boolean is_actived;
    private String product_category_id;
    private List<ServingSizeSchema> serving_sizes;

    public ProductSchema(String id, String name, Boolean isCustomizable, Boolean isActived, String productCategoryId, List<ServingSizeSchema> servingSizes) {
        super();
        this.id = id;
        this.name = name;
        this.is_customizable = isCustomizable;
        this.is_actived = isActived;
        this.product_category_id = productCategoryId;
        this.serving_sizes = servingSizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSchema that = (ProductSchema) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(is_customizable, that.is_customizable) && Objects.equals(is_actived, that.is_actived) && Objects.equals(product_category_id, that.product_category_id) && Objects.equals(serving_sizes, that.serving_sizes);
    }

}
