package delivery.deliveryapp.shared.service;

public interface CreationService<Dto, Entity>{
    public Entity create(Dto dto);
}
