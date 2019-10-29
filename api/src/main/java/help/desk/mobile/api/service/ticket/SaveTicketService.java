package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.InvalidAreaException;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import help.desk.mobile.api.service.ticketstatus.SaveTicketStatusService;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class SaveTicketService {

	private TicketRepository ticketRepository;

	private TicketMapper ticketMapper;

	private ExistsByIdAreaService existsByIdAreaService;

	private SaveTicketStatusService saveTicketStatusService;

	public SaveTicketService(TicketRepository ticketRepository, TicketMapper ticketMapper, ExistsByIdAreaService existsByIdAreaService, SaveTicketStatusService saveTicketStatusService) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
		this.existsByIdAreaService = existsByIdAreaService;
		this.saveTicketStatusService = saveTicketStatusService;
	}

	public Long saveTicket(SaveTicketRequest request) {
		if (!existsByIdAreaService.existsById(request.getAreaId())) {
			throw new InvalidAreaException();
		}

		final TicketEntity ticketEntity = ticketMapper.toTicketEntity(request);

		final TicketEntity persistedTicketEntity = ticketRepository.saveAndFlush(ticketEntity);

		saveTicketStatusService.saveTicketStatus(persistedTicketEntity.getId(), true, Status.PENDING);

		return persistedTicketEntity.getId();
	}
}
