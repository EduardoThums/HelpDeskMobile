package help.desk.mobile.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidAreaException extends RuntimeException {

	public InvalidAreaException() {
		super("Area doest exists");
	}
}
