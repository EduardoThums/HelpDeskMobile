package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.dto.TicketDetailsDto;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

	public Page<TicketDetailsResponse> findAllByLoggedUser(Pageable pageable) {
		final UserPrincipal loggedUser = customUserDetailsService.getUser();

		final List<TicketDetailsDto> ticketDetailsDtos = ticketRepository.findAllTicketDetailsDtoByAuthorId(loggedUser.getId(), pageable);

		return new PageImpl<>(ticketMapper.toTicketDetailsResponses(ticketDetailsDtos));
	}
}
