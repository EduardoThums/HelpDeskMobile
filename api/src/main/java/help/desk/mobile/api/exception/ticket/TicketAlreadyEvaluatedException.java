package help.desk.mobile.api.exception.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TicketAlreadyEvaluatedException extends RuntimeException {

	public TicketAlreadyEvaluatedException() {
		super("Ticket is already evaluated");
	}
}
