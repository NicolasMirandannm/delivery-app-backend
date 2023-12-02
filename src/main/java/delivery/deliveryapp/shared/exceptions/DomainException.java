package delivery.deliveryapp.shared.exceptions;

public class DomainException extends RuntimeException{
    private DomainException(String message) {
        super(message);
    }

    public static DomainException throwException(String message) {
        throw new DomainException(message);
    }

    public static DomainException throwException() {
        throw new DomainException("An error occurred in the domain.");
    }
}
