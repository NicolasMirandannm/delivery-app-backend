package delivery.deliveryapp.application.productCategory;

import delivery.deliveryapp.domain.productCategory.ProductCategory;
import delivery.deliveryapp.domain.repository.IProductCategoryRepository;
import delivery.deliveryapp.shared.exceptions.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductCategory implements ICreateProductCategory {

    private final IProductCategoryRepository productCategoryRepository;

    @Override
    public void create(CreateProductCategoryDto createCategoryDto) {
        ApplicationException.whenIsNull(createCategoryDto, "cannot create a product category without data.");

        var productCategory = ProductCategory.createNew(createCategoryDto.getCategoryName());
        productCategoryRepository.create(productCategory);
    }
}