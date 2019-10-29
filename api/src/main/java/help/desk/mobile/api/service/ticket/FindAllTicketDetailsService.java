package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.dto.TicketDetailsDto;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eduardo.thums
 */
@Service
public class FindAllTicketDetailsService {

	private TicketRepository ticketRepository;

	private TicketMapper ticketMapper;

	public FindAllTicketDetailsService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
	}

	public List<TicketDetailsResponse> findAll() {
		final List<TicketDetailsDto> ticketDetailsDtos = ticketRepository.findAllTicketDetailsDto();

		return ticketMapper.toTicketDetailsResponses(ticketDetailsDtos);
	}
}
