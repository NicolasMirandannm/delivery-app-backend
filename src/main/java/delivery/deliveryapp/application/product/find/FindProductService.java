package delivery.deliveryapp.application.product.find;

import delivery.deliveryapp.domain.product.Product;
import delivery.deliveryapp.shared.UniqueIdentifier;
import delivery.deliveryapp.shared.ddd.IRepository;
import delivery.deliveryapp.shared.exceptions.InfraException;
import delivery.deliveryapp.shared.service.FindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProductService implements FindService<Product> {
    private final IRepository<Product> productRepository;


    @Override
    public Product findBy(String id) {
        InfraException.whenIsNull(id, "cannot find product with empty id.");

        var identifier = UniqueIdentifier.createFrom(id);
        return productRepository.findBy(identifier);
    }
}