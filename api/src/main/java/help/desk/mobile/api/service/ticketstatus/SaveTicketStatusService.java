package help.desk.mobile.api.service.ticketstatus;

import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.repository.ticketstatus.TicketStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author eduardo.thums
 */
@Service
public class SaveTicketStatusService {

	private TicketStatusRepository ticketStatusRepository;

	public SaveTicketStatusService(TicketStatusRepository ticketStatusRepository) {
		this.ticketStatusRepository = ticketStatusRepository;
	}

	public void saveTicketStatus(Long ticketId, boolean currentStatus, Status status) {
		final TicketStatusEntity ticketStatusEntity = new TicketStatusEntity(
				ticketId,
				currentStatus,
				LocalDateTime.now(),
				status);

		ticketStatusRepository.saveAndFlush(ticketStatusEntity);
	}
}
