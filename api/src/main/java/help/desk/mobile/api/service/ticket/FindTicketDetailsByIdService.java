package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.exception.ticket.InvalidTicketException;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.area.FindAreaDetailsByIdService;
import help.desk.mobile.api.service.ticketstatus.FindCurrentStatusByTicketIdService;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class FindTicketDetailsByIdService {

	private TicketRepository ticketRepository;

	private TicketMapper ticketMapper;

	private FindAreaDetailsByIdService findAreaDetailsByIdService;

	private FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService;

	public FindTicketDetailsByIdService(TicketRepository ticketRepository, TicketMapper ticketMapper, FindAreaDetailsByIdService findAreaDetailsByIdService, FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
		this.findAreaDetailsByIdService = findAreaDetailsByIdService;
		this.findCurrentStatusByTicketIdService = findCurrentStatusByTicketIdService;
	}

	public TicketDetailsResponse findDetailsById(Long id) {
		final TicketEntity ticketEntity = ticketRepository.findById(id)
				.orElseThrow(InvalidTicketException::new);

		final AreaDetailsResponse area = findAreaDetailsByIdService.findDetailsById(ticketEntity.getAreaId());

		final TicketStatusEntity ticketStatus = findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id);

		return ticketMapper.toTicketDetailsResponse(ticketEntity, area, ticketStatus.getStatus());
	}
}
