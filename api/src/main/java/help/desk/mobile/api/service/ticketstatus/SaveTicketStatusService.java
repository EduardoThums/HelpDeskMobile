package help.desk.mobile.api.service.ticketstatus;

import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.repository.ticketstatus.TicketStatusRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class SaveTicketStatusService {

	private TicketStatusRepository ticketStatusRepository;

	public SaveTicketStatusService(TicketStatusRepository ticketStatusRepository) {
		this.ticketStatusRepository = ticketStatusRepository;
	}

	public Long saveTicketStatus(TicketStatusEntity ticketStatusEntity) {
		return ticketStatusRepository.save(ticketStatusEntity).getId();
	}
}
