package delivery.deliveryapp.shared.exceptions;

public class ApplicationException extends RuntimeException{
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException() {
        super("An exception has occurred in the application layer.");
    }

    public static void whenIsNull(Object object, String message) {
        if (object == null)
            throw new ApplicationException(message);
    }
}
