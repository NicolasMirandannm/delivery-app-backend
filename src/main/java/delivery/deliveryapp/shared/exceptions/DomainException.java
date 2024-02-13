package delivery.deliveryapp.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DomainException extends RuntimeException {
  private DomainException(String message) {
    super(message);
    StackTraceElement[] elements = {};
    this.setStackTrace(elements);
  }
  
  public static void throwException(String message) {
    throw new DomainException(message);
  }
  
  public static void whenIsNull(Object object, String message) {
    if (object == null)
      throwException(message);
  }
  
  public static void whenIsEmptyOrNull(List<?> collection, String message) {
    if (collection == null || collection.isEmpty())
      throwException(message);
  }
}
