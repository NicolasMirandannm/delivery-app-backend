package delivery.deliveryapp.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationException extends RuntimeException{
    public ApplicationException(String message) {
        super(message);
        StackTraceElement[] elements = {};
        this.setStackTrace(elements);
    }

    public ApplicationException() {
        super("An exception has occurred in the application layer.");
    }

    public static void whenIsNull(Object object, String message) {
        if (object == null)
            throwException(message);
    }

    public static void throwException(String message) {
        throw new ApplicationException(message);
    }
}
