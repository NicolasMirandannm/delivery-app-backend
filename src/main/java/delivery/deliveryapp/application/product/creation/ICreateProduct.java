package delivery.deliveryapp.application.product.creation;

import delivery.deliveryapp.application.product.creation.dtos.CreateProductDto;
import delivery.deliveryapp.domain.product.Product;

public interface ICreateProduct {
    public Product create(CreateProductDto createProductDto);
}
