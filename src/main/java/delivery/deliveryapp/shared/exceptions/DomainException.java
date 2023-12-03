package delivery.deliveryapp.shared.exceptions;

public class DomainException extends RuntimeException{
    private DomainException(String message) {
        super(message);
    }

    public static void throwException(String message) {
        throw new DomainException(message);
    }

    public static void throwException() {
        throw new DomainException("An error occurred in the domain.");
    }

    public static void whenIsNull(Object object, String message) {
        if (object == null)
            throwException(message);
    }
}
