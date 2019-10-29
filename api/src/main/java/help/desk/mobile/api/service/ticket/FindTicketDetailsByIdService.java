package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.exception.ticket.InvalidTicketException;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.area.FindAreaDetailsByIdService;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class FindTicketDetailsByIdService {

	private TicketRepository ticketRepository;

	private TicketMapper ticketMapper;

	private FindAreaDetailsByIdService findAreaDetailsByIdService;

	public FindTicketDetailsByIdService(TicketRepository ticketRepository, TicketMapper ticketMapper, FindAreaDetailsByIdService findAreaDetailsByIdService) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
		this.findAreaDetailsByIdService = findAreaDetailsByIdService;
	}

	public TicketDetailsResponse findDetailsById(Long id) {
		final TicketEntity ticketEntity = ticketRepository.findById(id)
				.orElseThrow(InvalidTicketException::new);

		final AreaDetailsResponse area = findAreaDetailsByIdService.findDetailsById(ticketEntity.getAreaId());

		return ticketMapper.toTicketDetailsResponse(ticketEntity, area);
	}
}
