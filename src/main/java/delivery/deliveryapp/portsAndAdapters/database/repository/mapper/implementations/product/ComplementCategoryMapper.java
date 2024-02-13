package delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ComplementItem;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.Mapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ComplementCategorySchema;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ComplementItemSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ComplementCategoryMapper implements Mapper<ComplementCategory, ComplementCategorySchema> {
  
  private final Mapper<ComplementItem, ComplementItemSchema> complementItemMapper;
  
  @Override
  public ComplementCategory toDomain(ComplementCategorySchema complementCategorySchema) {
    InfraException.whenIsNull(complementCategorySchema, "could not map null complement category schema to domain.");
    
    var id = UniqueIdentifier.createFrom(complementCategorySchema.getId());
    var categoryName = complementCategorySchema.getCategoryName();
    var amountAvailable = complementCategorySchema.getAmountAvailable();
    var complementItems = complementCategorySchema.getComplementItems() != null
      ? complementCategorySchema.getComplementItems().stream().map(this.complementItemMapper::toDomain).toList()
      : new ArrayList<ComplementItem>();
    
    return ComplementCategory.create(id, categoryName, amountAvailable, complementItems);
  }
  
  @Override
  public ComplementCategorySchema toPersistence(ComplementCategory complementCategory) {
    InfraException.whenIsNull(complementCategory, "could not map null complement category to schema of persistence.");
    
    var id = complementCategory.getIdValue();
    var categoryName = complementCategory.getCategoryName();
    var amountAvailable = complementCategory.getAmountAvailable();
    var complementItems = complementCategory.getComplements() != null
      ? complementCategory.getComplements().stream().map(this.complementItemMapper::toPersistence).toList()
      : new ArrayList<ComplementItemSchema>();
    
    return new ComplementCategorySchema(id, categoryName, amountAvailable, complementItems);
  }
}
