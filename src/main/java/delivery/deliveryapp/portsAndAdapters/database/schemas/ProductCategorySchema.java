package delivery.deliveryapp.portsAndAdapters.database.schemas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document("product-category")
public class ProductCategorySchema {

    @Id
    private String id;
    private String name;
    private Boolean isActive;

    public ProductCategorySchema(String id, String name, Boolean isActive) {
        super();
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategorySchema that = (ProductCategorySchema) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(isActive, that.isActive);
    }
}
