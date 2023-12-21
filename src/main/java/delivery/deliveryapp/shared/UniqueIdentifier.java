package delivery.deliveryapp.shared;

import delivery.deliveryapp.shared.exceptions.DomainException;

import java.util.UUID;

public record UniqueIdentifier(String value) {

    public static UniqueIdentifier create() {
        return new UniqueIdentifier(generateRandomId());
    }

    public static UniqueIdentifier createFrom(String uuid) {
        if (uuid == null) return null;
        return new UniqueIdentifier(UniqueIdentifier.generateIdFrom(uuid));
    }

    private static String generateIdFrom(String uuid) {
        try {
            return UUID.fromString(uuid).toString();
        }catch (Exception ex) {
            DomainException.throwException("UUID -> "+uuid+" <- inválido.");
            return null;
        }
    }

    private static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

}