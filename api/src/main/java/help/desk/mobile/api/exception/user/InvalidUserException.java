package help.desk.mobile.api.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidUserException extends RuntimeException {

	public InvalidUserException() {
		super("User doesn't exist");
	}
}
