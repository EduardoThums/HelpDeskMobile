package help.desk.mobile.api.controller.ticket;

import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.service.ticket.FindAllPendingTicketToCuratorshipService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {

	private FindAllPendingTicketToCuratorshipService findAllPendingTicketToCuratorshipService;

	public TicketAdminController(FindAllPendingTicketToCuratorshipService findAllPendingTicketToCuratorshipService) {
		this.findAllPendingTicketToCuratorshipService = findAllPendingTicketToCuratorshipService;
	}

	@GetMapping("/pending")
	public ResponseEntity<Page<TicketDetailsResponse>> findAllPendingTicketToCuratorship(Pageable pageable) {
		return ResponseEntity.ok(findAllPendingTicketToCuratorshipService.findAllPendingTicketToCuratorship(pageable));
	}
}
