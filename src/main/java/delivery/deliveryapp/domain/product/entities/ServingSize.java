package delivery.deliveryapp.domain.product.entities;

import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.DomainEntity;
import delivery.deliveryapp.shared.exceptions.DomainException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ServingSize extends DomainEntity {
  
  private String name;
  private String description;
  private Boolean activedComplements;
  private Double price;
  private List<ComplementCategory> complementCategories;
  
  private ServingSize(UniqueIdentifier id, String name, String description, Boolean activedComplements, Double price,
        List<ComplementCategory> complementCategories) {
    super(id);
    this.name = name;
    this.description = description;
    this.activedComplements = activedComplements;
    this.price = price;
    this.complementCategories = complementCategories;
  }
  
  public static ServingSize create(UniqueIdentifier id, String name, String description, Boolean activedComplements, Double price,
        List<ComplementCategory> complementCategories) {
    if (activedComplements)
      DomainException.whenIsNull(complementCategories, "cannot create a serving size with actived complements without list of complements.");
    
    return new ServingSize(id, name, description, activedComplements, price, complementCategories);
  }
  
  public static ServingSize createNew(String name, String description, Boolean activedComplements,
        Double price, List<ComplementCategory> complementCategories) {
    var id = UniqueIdentifier.create();
    return create(id, name, description, activedComplements, price, complementCategories);
  }
  
  public Boolean hasActiveComplements() {
    return activedComplements;
  }
}
