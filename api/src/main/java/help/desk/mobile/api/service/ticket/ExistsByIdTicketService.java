package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.repository.ticket.TicketRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class ExistsByIdTicketService {

	private TicketRepository ticketRepository;

	public ExistsByIdTicketService(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public boolean existsById(Long id) {
		return ticketRepository.existsById(id);
	}
}
