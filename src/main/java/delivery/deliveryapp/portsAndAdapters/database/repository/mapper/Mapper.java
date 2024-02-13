package delivery.deliveryapp.portsAndAdapters.database.repository.mapper;

import delivery.deliveryapp.shared.ddd.DomainEntity;

public interface Mapper<DomainAggregate extends DomainEntity, MongoSchema> {
    public DomainAggregate toDomain(MongoSchema schema);
    public MongoSchema toPersistence(DomainAggregate entity);
}
