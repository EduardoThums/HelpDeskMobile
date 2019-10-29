package help.desk.mobile.api.service.ticketstatus;

import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.ticket.InvalidTicketException;
import help.desk.mobile.api.repository.ticketstatus.TicketStatusRepository;
import help.desk.mobile.api.service.ticket.ExistsByIdTicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author eduardo.thums
 */
@Service
public class SaveTicketStatusService {

	private TicketStatusRepository ticketStatusRepository;

	private ExistsByIdTicketService existsByIdTicketService;

	public SaveTicketStatusService(TicketStatusRepository ticketStatusRepository, ExistsByIdTicketService existsByIdTicketService) {
		this.ticketStatusRepository = ticketStatusRepository;
		this.existsByIdTicketService = existsByIdTicketService;
	}

	public void saveTicketStatus(Long ticketId, boolean currentStatus, Status status) {
		if (!existsByIdTicketService.existsById(ticketId)) {
			throw new InvalidTicketException();
		}

		final TicketStatusEntity ticketStatusEntity = new TicketStatusEntity(
				ticketId,
				currentStatus,
				LocalDateTime.now(),
				status);

		ticketStatusRepository.saveAndFlush(ticketStatusEntity);
	}
}
