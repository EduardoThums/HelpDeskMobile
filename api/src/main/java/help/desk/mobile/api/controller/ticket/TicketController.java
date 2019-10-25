package help.desk.mobile.api.controller.ticket;

import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.service.ticket.SaveTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

	private SaveTicketService saveTicketService;

	public TicketController(SaveTicketService saveTicketService) {
		this.saveTicketService = saveTicketService;
	}

	@PostMapping
	public ResponseEntity<Long> saveTicket(@RequestBody @Valid SaveTicketRequest request) {
		return new ResponseEntity<>(saveTicketService.saveTicket(request), HttpStatus.CREATED);
	}
}
