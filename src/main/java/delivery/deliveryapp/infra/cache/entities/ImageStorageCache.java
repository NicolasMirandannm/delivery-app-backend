package delivery.deliveryapp.infra.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@AllArgsConstructor
@RedisHash("ImageStorageCache")
public class ImageStorageCache {
  private String id;
  private String url;
  
  @TimeToLive
  private Long ttl;
}
