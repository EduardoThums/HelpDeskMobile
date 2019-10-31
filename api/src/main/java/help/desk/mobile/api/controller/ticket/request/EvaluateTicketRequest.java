package help.desk.mobile.api.controller.ticket.request;

import help.desk.mobile.api.domain.status.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author eduardo.thums
 */
@Setter
@Getter
@NoArgsConstructor
public class EvaluateTicketRequest {

	@NotNull
	private Status newStatus;

	private String curatorshipMessage;
}
