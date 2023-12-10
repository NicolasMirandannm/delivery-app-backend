package delivery.deliveryapp.portsAndAdapters.database.schemas;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@Document("serving-size")
public class ServingSizeSchema {
    private String id;
    private String name;
    private String description;
    private Boolean actived_complements;
    private Integer amount_of_complements;
    private String complement_category_id;

    public ServingSizeSchema(String id, String name, String description, Boolean actived_complements, Integer amount_of_complements, String complement_category_id) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.actived_complements = actived_complements;
        this.amount_of_complements = amount_of_complements;
        this.complement_category_id = complement_category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServingSizeSchema that = (ServingSizeSchema) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(actived_complements, that.actived_complements) && Objects.equals(amount_of_complements, that.amount_of_complements) && Objects.equals(complement_category_id, that.complement_category_id);
    }
}
