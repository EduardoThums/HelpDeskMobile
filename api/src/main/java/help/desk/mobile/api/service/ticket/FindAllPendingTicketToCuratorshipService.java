package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class FindAllPendingTicketToCuratorshipService {

	private TicketRepository ticketRepository;

	private TicketMapper ticketMapper;

	private CustomUserDetailsService customUserDetailsService;

	public FindAllPendingTicketToCuratorshipService(TicketRepository ticketRepository, TicketMapper ticketMapper, CustomUserDetailsService customUserDetailsService) {
		this.ticketRepository = ticketRepository;
		this.ticketMapper = ticketMapper;
		this.customUserDetailsService = customUserDetailsService;
	}

	public Page<TicketDetailsResponse> findAllPendingTicketToCuratorship(Pageable pageable) {
		final Long loggedUserId = customUserDetailsService.getUser().getId();

		return new PageImpl<>(ticketMapper.toTicketDetailsResponses(ticketRepository.findAllTicketDetailsDtoByPendingStatus(loggedUserId, pageable)));
	}
}
