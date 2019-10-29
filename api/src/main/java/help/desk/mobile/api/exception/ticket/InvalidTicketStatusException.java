package help.desk.mobile.api.exception.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidTicketStatusException extends RuntimeException {
	public InvalidTicketStatusException() {
		super("TicketStatus doesn't exist");
	}
}
