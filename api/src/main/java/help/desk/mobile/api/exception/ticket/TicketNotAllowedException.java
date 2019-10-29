package help.desk.mobile.api.exception.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class TicketNotAllowedException extends RuntimeException {
	public TicketNotAllowedException() {
		super("You don't own this ticket");
	}
}
