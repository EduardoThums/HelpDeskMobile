package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
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
public class FindAllTicketDetailsByLoggedUserService {

	private TicketRepository ticketRepository;

	private TicketMapper ticketMapper;

	private CustomUserDetailsService customUserDetailsService;

	public FindAllTicketDetailsByLoggedUserService(TicketRepository ticketRepository, TicketMapper ticketMapper, CustomUserDetailsService customUserDetailsService) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
		this.customUserDetailsService = customUserDetailsService;
	}

	public List<TicketDetailsResponse> findAll() {
		final UserPrincipal loggedUser = customUserDetailsService.getUser();

		final List<TicketDetailsDto> ticketDetailsDtos = ticketRepository.findAllTicketDetailsDtoByAuthorId(loggedUser.getId());

		return ticketMapper.toTicketDetailsResponses(ticketDetailsDtos);
	}
}
