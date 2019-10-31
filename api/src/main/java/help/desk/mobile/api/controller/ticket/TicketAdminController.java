package help.desk.mobile.api.controller.ticket;

import help.desk.mobile.api.controller.ticket.request.EvaluateTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.service.ticket.EvaluateTicketByIdService;
import help.desk.mobile.api.service.ticket.FindAllPendingTicketToCuratorshipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

	private FindAllPendingTicketToCuratorshipService findAllPendingTicketToCuratorshipService;

	private EvaluateTicketByIdService evaluateTicketByIdService;

	public TicketAdminController(FindAllPendingTicketToCuratorshipService findAllPendingTicketToCuratorshipService, EvaluateTicketByIdService evaluateTicketByIdService) {
		this.findAllPendingTicketToCuratorshipService = findAllPendingTicketToCuratorshipService;
		this.evaluateTicketByIdService = evaluateTicketByIdService;
	}

	@GetMapping("/pending")
	public ResponseEntity<Page<TicketDetailsResponse>> findAllPendingTicketToCuratorship(Pageable pageable) {
		return ResponseEntity.ok(findAllPendingTicketToCuratorshipService.findAllPendingTicketToCuratorship(pageable));
	}

	@PutMapping("{id}")
	public void evaluateTicket(@PathVariable Long id, @RequestBody @Valid EvaluateTicketRequest request) {
		evaluateTicketByIdService.evaluate(id, request);
	}
}
