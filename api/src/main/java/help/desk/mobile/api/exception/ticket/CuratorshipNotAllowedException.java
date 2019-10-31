package help.desk.mobile.api.exception.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author eduardo.thums
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CuratorshipNotAllowedException extends RuntimeException {

	public CuratorshipNotAllowedException() {
		super("It's not allow evaluate a ticket without change his status");
	}
}
