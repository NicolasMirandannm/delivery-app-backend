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
  private List<ProductFeedstockBaseConsumption> feedstocksBaseConsumption;
  
  private ServingSize(UniqueIdentifier id, String name, String description, Boolean activedComplements, Double price,
        List<ComplementCategory> complementCategories, List<ProductFeedstockBaseConsumption> feedstocksBaseConsumption) {
    super(id);
    this.name = name;
    this.description = description;
    this.activedComplements = activedComplements;
    this.price = price;
    this.complementCategories = complementCategories;
    this.feedstocksBaseConsumption = feedstocksBaseConsumption;
  }
  
  public static ServingSize create(UniqueIdentifier id, String name, String description, Boolean activedComplements, Double price,
        List<ComplementCategory> complementCategories, List<ProductFeedstockBaseConsumption> feedstocksBaseConsumption) {
    if (activedComplements)
      DomainException.whenIsNull(complementCategories, "cannot create a serving size with actived complements without list of complements.");
    
    return new ServingSize(id, name, description, activedComplements, price, complementCategories, feedstocksBaseConsumption);
  }
  
  public static ServingSize createNew(String name, String description, Boolean activedComplements,
        Double price, List<ComplementCategory> complementCategories, List<ProductFeedstockBaseConsumption> feedstocksBaseConsumption) {
    var id = UniqueIdentifier.create();
    return create(id, name, description, activedComplements, price, complementCategories, feedstocksBaseConsumption);
  }
  
  public Boolean hasActiveComplements() {
    return activedComplements;
  }
}
