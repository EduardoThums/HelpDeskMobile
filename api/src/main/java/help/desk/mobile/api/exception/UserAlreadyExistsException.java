package help.desk.mobile.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.AuthenticationException;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends AuthenticationException {

	public UserAlreadyExistsException() {
		super("Already exists a user with the given values");
	}
}
