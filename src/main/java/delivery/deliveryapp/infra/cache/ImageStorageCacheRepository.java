package delivery.deliveryapp.infra.cache;

import delivery.deliveryapp.infra.cache.entities.ImageStorageCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageStorageCacheRepository extends CrudRepository<ImageStorageCache, String> {
}
