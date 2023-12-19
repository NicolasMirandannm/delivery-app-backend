package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.repository.IProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.valueObjects.UnitOfMeasurement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateProduct implements ICreateProduct {

    private final IProductRepository productRepository;

    public Product create(CreateProductDto createProductDto) {
        ApplicationException.whenIsNull(createProductDto, "cannot create a product without product creation data.");

        var productCategoryId = UniqueIdentifier.createFrom(createProductDto.getProductCategoryId());
        var name = createProductDto.getName();
        var productCreated = Product.createNew(name, productCategoryId);

        for (var servingSizeDto : createProductDto.getServingSizes()) {
            var servingSizeName = servingSizeDto.getName();
            var description = servingSizeDto.getDescription();
            var complementsIsActive = servingSizeDto.getComplementsIsActive();
            var amountOfComplements = servingSizeDto.getAmountOfComplements();
            var complementTypeId = servingSizeDto.getComplementTypeId() != null
                    ? UniqueIdentifier.createFrom(servingSizeDto.getComplementTypeId())
                    : null;
            var feedstocksBaseConsumption = servingSizeDto.getFeedstocksBaseConsumptions().stream().map(baseConsumptionDto -> {
                var feedstockId = UniqueIdentifier.createFrom(baseConsumptionDto.getFeedstockId());
                var quantity = baseConsumptionDto.getQuantity();
                var unitOfMeasurement = UnitOfMeasurement.create(baseConsumptionDto.getMeasureType(), baseConsumptionDto.getAmount());
                return FeedstockBaseConsumption.createNew(feedstockId, quantity, unitOfMeasurement);
            }).collect(Collectors.toList());

            var servingSize = ServingSize.createNew(servingSizeName, description, complementsIsActive, amountOfComplements, complementTypeId, feedstocksBaseConsumption);
            productCreated.addAServingSize(servingSize);
        }

        productRepository.create(productCreated);
        return productCreated;
    }
}
