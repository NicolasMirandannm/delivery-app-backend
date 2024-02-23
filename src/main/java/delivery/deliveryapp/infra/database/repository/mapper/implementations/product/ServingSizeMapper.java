package delivery.deliveryapp.infra.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.entities.ComplementCategory;
import delivery.deliveryapp.domain.product.entities.ProductFeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.infra.database.repository.mapper.Mapper;
import delivery.deliveryapp.infra.database.schemas.ComplementCategorySchema;
import delivery.deliveryapp.infra.database.schemas.FeedstockBaseConsumptionSchema;
import delivery.deliveryapp.infra.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServingSizeMapper implements Mapper<ServingSize, ServingSizeSchema> {
  
  private final Mapper<ProductFeedstockBaseConsumption, FeedstockBaseConsumptionSchema> feedstockBaseConsumptionMapper;
  private final Mapper<ComplementCategory, ComplementCategorySchema> complementCategoryMapper;
  
  @Override
  public ServingSize toDomain(ServingSizeSchema servingSizeSchema) {
    InfraException.whenIsNull(servingSizeSchema, "serving size schema is null");
    
    var id = UniqueIdentifier.createFrom(servingSizeSchema.getId());
    var name = servingSizeSchema.getName();
    var description = servingSizeSchema.getDescription();
    var isActive = servingSizeSchema.getActivedComplements();
    var price = servingSizeSchema.getPrice();
    var complementCategories = servingSizeSchema.getComplementCategories() != null
      ? servingSizeSchema.getComplementCategories().stream().map(this.complementCategoryMapper::toDomain).toList()
      : new ArrayList<ComplementCategory>();
    var feedstocksBaseConsumption = assembleFeedstockBaseConsumptions(servingSizeSchema.getFeedstockBaseConsumptions());
    return ServingSize.create(id, name, description, isActive, price, complementCategories, feedstocksBaseConsumption);
  }
  
  @Override
  public ServingSizeSchema toPersistence(ServingSize servingSize) {
    InfraException.whenIsNull(servingSize, "serving size entity is null");
    
    var id = servingSize.getIdValue();
    var name = servingSize.getName();
    var description = servingSize.getDescription();
    var activedComplements = servingSize.getActivedComplements();
    var price = servingSize.getPrice();
    var complementsCategories = servingSize.getComplementCategories() != null
      ? servingSize.getComplementCategories().stream().map(this.complementCategoryMapper::toPersistence).toList()
      : new ArrayList<ComplementCategorySchema>();
    var feedstockBaseConsumption = assembleFeedstockBaseConsumptionsSchema(servingSize.getFeedstocksBaseConsumption());
    return new ServingSizeSchema(id, name, description, activedComplements, price, complementsCategories, feedstockBaseConsumption);
  }
  
  private List<ProductFeedstockBaseConsumption> assembleFeedstockBaseConsumptions(List<FeedstockBaseConsumptionSchema> feedstockBaseConsumptionSchemas) {
    return feedstockBaseConsumptionSchemas == null
      ? new ArrayList<ProductFeedstockBaseConsumption>()
      : feedstockBaseConsumptionSchemas.stream()
        .map((this.feedstockBaseConsumptionMapper::toDomain))
        .collect(Collectors.toList());
  }
  
  private List<FeedstockBaseConsumptionSchema> assembleFeedstockBaseConsumptionsSchema(List<ProductFeedstockBaseConsumption> feedstockBaseConsumptions) {
    return feedstockBaseConsumptions == null
      ? new ArrayList<FeedstockBaseConsumptionSchema>()
      : feedstockBaseConsumptions.stream()
        .map((this.feedstockBaseConsumptionMapper::toPersistence))
        .toList();
  }
}
