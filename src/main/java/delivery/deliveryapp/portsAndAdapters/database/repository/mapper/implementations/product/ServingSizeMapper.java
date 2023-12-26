package delivery.deliveryapp.portsAndAdapters.database.repository.mapper.implementations.product;

import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.portsAndAdapters.database.repository.mapper.IMapper;
import delivery.deliveryapp.portsAndAdapters.database.schemas.FeedstockBaseConsumptionSchema;
import delivery.deliveryapp.portsAndAdapters.database.schemas.ServingSizeSchema;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.InfraException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ServingSizeMapper implements IMapper<ServingSize, ServingSizeSchema> {
    private final IMapper<FeedstockBaseConsumption, FeedstockBaseConsumptionSchema> feedstockBaseConsumptionMapper;

    @Override
    public ServingSize toDomain(ServingSizeSchema servingSizeSchema) {
        InfraException.whenIsNull(servingSizeSchema, "serving size schema is null");

        var id = UniqueIdentifier.createFrom(servingSizeSchema.getId());
        var name = servingSizeSchema.getName();
        var description = servingSizeSchema.getDescription();
        var isActive = servingSizeSchema.getActivedComplements();
        var amountOfComplements = servingSizeSchema.getAmountOfComplements();
        var price = servingSizeSchema.getPrice();
        var complementCategoryId = servingSizeSchema.getComplementCategoryId() != null
                ? UniqueIdentifier.createFrom(servingSizeSchema.getComplementCategoryId())
                : null;
        var feedstocksBaseConsumption = assembleFeedstockBaseConsumptions(servingSizeSchema.getFeedstockBaseConsumptions());
        return ServingSize.create(id, name, description, isActive, amountOfComplements, price, complementCategoryId, feedstocksBaseConsumption);
    }

    @Override
    public ServingSizeSchema toPersistence(ServingSize servingSize) {
        InfraException.whenIsNull(servingSize, "serving size entity is null");

        var id = servingSize.getIdValue();
        var name = servingSize.getName();
        var description = servingSize.getDescription();
        var activedComplements = servingSize.getActivedComplements();
        var amountOfComplements = servingSize.getAmountOfComplements();
        var price = servingSize.getPrice();
        var complementCategoryId = servingSize.getComplementCategoryId();
        var feedstockBaseConsumption = assembleFeedstockBaseConsumptionsSchema(servingSize.getFeedstocksBaseConsumption());
        return new ServingSizeSchema(id, name, description, activedComplements, amountOfComplements, price, complementCategoryId, feedstockBaseConsumption);
    }

    private List<FeedstockBaseConsumption> assembleFeedstockBaseConsumptions(List<FeedstockBaseConsumptionSchema> feedstockBaseConsumptionSchemas) {
        return feedstockBaseConsumptionSchemas == null
                ? new ArrayList<FeedstockBaseConsumption>()
                : feedstockBaseConsumptionSchemas
                .stream().map((this.feedstockBaseConsumptionMapper::toDomain))
                .collect(Collectors.toList());
    }

    private List<FeedstockBaseConsumptionSchema> assembleFeedstockBaseConsumptionsSchema(List<FeedstockBaseConsumption> feedstockBaseConsumptions) {
        return feedstockBaseConsumptions == null
                ? new ArrayList<FeedstockBaseConsumptionSchema>()
                : feedstockBaseConsumptions
                .stream().map((this.feedstockBaseConsumptionMapper::toPersistence))
                .toList();
    }
}
