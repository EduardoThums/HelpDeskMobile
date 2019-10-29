package help.desk.mobile.api.controller.ticket;

import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.service.ticket.FindTicketDetailsByIdService;
import help.desk.mobile.api.service.ticket.SaveTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

	private SaveTicketService saveTicketService;

	private FindTicketDetailsByIdService findTicketDetailsByIdService;

	public TicketController(SaveTicketService saveTicketService, FindTicketDetailsByIdService findTicketDetailsByIdService) {
		this.saveTicketService = saveTicketService;
		this.findTicketDetailsByIdService = findTicketDetailsByIdService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<TicketDetailsResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(findTicketDetailsByIdService.findDetailsById(id));
	}

	@PostMapping
	public ResponseEntity<Long> saveTicket(@RequestBody @Valid SaveTicketRequest request) {
		return new ResponseEntity<>(saveTicketService.saveTicket(request), HttpStatus.CREATED);
	}
}
