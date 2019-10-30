package help.desk.mobile.api.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WrongPasswordException extends RuntimeException {

	public WrongPasswordException() {
		super("Passwords must be equal");
	}
}
