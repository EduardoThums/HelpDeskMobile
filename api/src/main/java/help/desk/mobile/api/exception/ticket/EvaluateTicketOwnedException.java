package help.desk.mobile.api.exception.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class EvaluateTicketOwnedException extends RuntimeException {

	public EvaluateTicketOwnedException() {
		super("It's not allowed evaluate your own ticket");
	}
}
