package delivery.deliveryapp.shared.exceptions;

public class InfraException extends RuntimeException {
    public InfraException(String message) {
        super(message);
    }

    public InfraException() {
        super("An exception has occurred in the application layer.");
    }

    public static void whenIsNull(Object object, String message) {
        if (object == null)
            throw new InfraException(message);
    }
}
