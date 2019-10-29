package help.desk.mobile.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidTicketException extends RuntimeException {

	public InvalidTicketException() {
		super("Ticket doesn't exists");
	}
}
