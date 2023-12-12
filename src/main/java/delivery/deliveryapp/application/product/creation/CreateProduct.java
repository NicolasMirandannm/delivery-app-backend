package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.repository.IProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProduct {

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
            var complementTypeId = UniqueIdentifier.createFrom(servingSizeDto.getComplementTypeId());
            var servingSize = ServingSize.createNew(servingSizeName, description, complementsIsActive, amountOfComplements, complementTypeId);
            productCreated.addAServingSize(servingSize);
        }

        productRepository.create(productCreated);
        return productCreated;
    }
}
