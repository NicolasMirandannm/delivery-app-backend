package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.application.product.creation.dtos.CreateServingSizeDto;
import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.domain.product.entities.ServingSize;
import delivery.deliveryapp.domain.repository.ProductRepository;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProduct implements CreationService<CreateProductDto, Product> {

    private final ProductRepository productRepository;
    private final CreationService<CreateServingSizeDto, ServingSize> createServingSizeService;

    public Product create(CreateProductDto createProductDto) {
        ApplicationException.whenIsNull(createProductDto, "cannot create a product without product creation data.");

        var productAlreadyExists = productRepository.findByName(createProductDto.getName());
        if (productAlreadyExists != null)
            ApplicationException.throwException("A product already exists with the same name");

        var productCategoryId = UniqueIdentifier.createFrom(createProductDto.getProductCategoryId());
        var name = createProductDto.getName();
        var description = createProductDto.getDescription();
        var imageURI = createProductDto.getImageURI();

        var productCreated = Product.createNew(name, description, imageURI, productCategoryId);

        for (var servingSizeDto : createProductDto.getServingSizes()) {
            var servingSize = createServingSizeService.create(servingSizeDto);
            productCreated.addAServingSize(servingSize);
        }

        productRepository.create(productCreated);
        return productCreated;
    }
}
