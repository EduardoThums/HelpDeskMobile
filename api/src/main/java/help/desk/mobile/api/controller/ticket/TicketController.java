package help.desk.mobile.api.controller.ticket;

import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.service.ticket.CancelTicketByIdService;
import help.desk.mobile.api.service.ticket.FindAllTicketDetailsByLoggedUserService;
import help.desk.mobile.api.service.ticket.FindTicketDetailsByIdService;
import help.desk.mobile.api.service.ticket.SaveTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/ticket")
public class TicketController {

	private SaveTicketService saveTicketService;

	private FindTicketDetailsByIdService findTicketDetailsByIdService;

	private CancelTicketByIdService cancelTicketByIdService;

	private FindAllTicketDetailsByLoggedUserService findAllTicketDetailsByLoggedUserService;

	public TicketController(SaveTicketService saveTicketService, FindTicketDetailsByIdService findTicketDetailsByIdService, CancelTicketByIdService cancelTicketByIdService, FindAllTicketDetailsByLoggedUserService findAllTicketDetailsByLoggedUserService) {
		this.saveTicketService = saveTicketService;
		this.findTicketDetailsByIdService = findTicketDetailsByIdService;
		this.cancelTicketByIdService = cancelTicketByIdService;
		this.findAllTicketDetailsByLoggedUserService = findAllTicketDetailsByLoggedUserService;
	}

	@GetMapping
	public ResponseEntity<List<TicketDetailsResponse>> findAllByLoggedUser() {
		return ResponseEntity.ok(findAllTicketDetailsByLoggedUserService.findAll());
	}

	@GetMapping("/{id}")

	public ResponseEntity<TicketDetailsResponse> findById(@PathVariable Long id) {
		return ResponseEntity.ok(findTicketDetailsByIdService.findDetailsById(id));
	}

	@PostMapping
	public ResponseEntity<Long> saveTicket(@RequestBody @Valid SaveTicketRequest request) {
		return new ResponseEntity<>(saveTicketService.saveTicket(request), HttpStatus.CREATED);
	}

	@PutMapping("/{id}/cancel")
	public void cancelTicket(@PathVariable Long id) {
		cancelTicketByIdService.cancelById(id);
	}
}
