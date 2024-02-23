package delivery.deliveryapp.infra.database.schemas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document("complement-category")
public class ComplementCategorySchema {
    private String id;
    private String categoryName;
    private Integer amountAvailable;
    private List<ComplementItemSchema> complementItems;

    public ComplementCategorySchema(String id, String categoryName, Integer amountAvailable, List<ComplementItemSchema> complementItems) {
        super();
        this.id = id;
        this.categoryName = categoryName;
        this.amountAvailable = amountAvailable;
        this.complementItems = complementItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplementCategorySchema that = (ComplementCategorySchema) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(categoryName, that.categoryName) &&
                Objects.equals(amountAvailable, that.amountAvailable) &&
                Objects.equals(complementItems, that.complementItems);
    }
}
