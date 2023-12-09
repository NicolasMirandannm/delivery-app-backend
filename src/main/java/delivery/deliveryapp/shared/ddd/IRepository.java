package delivery.deliveryapp.shared.ddd;

import delivery.deliveryapp.shared.UniqueIdentifier;

import java.util.List;

public interface IRepository<T extends AggregateRoot> {
    public void create(T aggregateRoot);
    public void update(T aggregateRoot);
    public void delete(UniqueIdentifier aggregateRootId);
    public T findBy(UniqueIdentifier aggregateRootId);
    public List<T> findAll();
}
