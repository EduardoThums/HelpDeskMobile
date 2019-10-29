package help.desk.mobile.api.service.ticketstatus;

import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.exception.ticket.InvalidTicketStatusException;
import help.desk.mobile.api.repository.ticketstatus.TicketStatusRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class FindCurrentStatusByTicketIdService {

	private TicketStatusRepository ticketStatusRepository;

	public FindCurrentStatusByTicketIdService(TicketStatusRepository ticketStatusRepository) {
		this.ticketStatusRepository = ticketStatusRepository;
	}

	public TicketStatusEntity findCurrentStatusByTicketId(Long ticketId) {
		return ticketStatusRepository.findByTicketIdAndCurrentStatusTrue(ticketId)
				.orElseThrow(InvalidTicketStatusException::new);
	}
}
