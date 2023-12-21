package delivery.deliveryapp.application.productCategory;

import delivery.deliveryapp.application.product.creation.dtos.CreateFeedstockBaseConsumptionDto;
import delivery.deliveryapp.domain.product.entities.FeedstockBaseConsumption;
import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.repository.IProductCategoryRepository;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import delivery.deliveryapp.shared.service.CreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductCategory implements CreationService<CreateProductCategoryDto, ProductCategory> {

    private final IProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory create(CreateProductCategoryDto createCategoryDto) {
        ApplicationException.whenIsNull(createCategoryDto, "cannot create a product category without data.");

        var productCategoryAlreadyExistis = productCategoryRepository.findByName(createCategoryDto.getCategoryName());
        if (productCategoryAlreadyExistis != null)
            ApplicationException.throwException("A product category already exists with the same name");

        var productCategory = ProductCategory.createNew(createCategoryDto.getCategoryName());
        productCategoryRepository.create(productCategory);
        return productCategory;
    }
}