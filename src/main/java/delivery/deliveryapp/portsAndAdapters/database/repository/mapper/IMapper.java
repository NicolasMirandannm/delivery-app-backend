package delivery.deliveryapp.portsAndAdapters.database.repository.mapper;

import delivery.deliveryapp.shared.ddd.AggregateRoot;
import delivery.deliveryapp.shared.ddd.DomainEntity;

import javax.swing.text.html.parser.Entity;

public interface IMapper<DomainAggregate extends DomainEntity, MongoSchema> {
    public DomainAggregate toDomain(MongoSchema schema);
    public MongoSchema toPersistence(DomainAggregate entity);
}
