package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.ticket.InvalidTicketException;
import help.desk.mobile.api.exception.ticket.TicketAlreadyCanceledException;
import help.desk.mobile.api.exception.ticket.TicketAlreadyEvaluatedException;
import help.desk.mobile.api.exception.ticket.TicketNotAllowedException;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.ticketstatus.FindCurrentStatusByTicketIdService;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class CancelTicketByIdService {

	private TicketRepository ticketRepository;

	private FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService;

	private CustomUserDetailsService customUserDetailsService;

	public CancelTicketByIdService(TicketRepository ticketRepository, FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService, CustomUserDetailsService customUserDetailsService) {
		this.ticketRepository = ticketRepository;
		this.findCurrentStatusByTicketIdService = findCurrentStatusByTicketIdService;
		this.customUserDetailsService = customUserDetailsService;
	}

	public void cancelById(Long id) {
		final TicketEntity ticketEntity = ticketRepository.findById(id)
				.orElseThrow(InvalidTicketException::new);

		if (ticketEntity.isDeleted()) {
			throw new TicketAlreadyCanceledException();
		}

		final UserPrincipal loggedUser = customUserDetailsService.getUser();

		if (!ticketEntity.getAuthorId().equals(loggedUser.getId())) {
			throw new TicketNotAllowedException();
		}

		final TicketStatusEntity currentTicketStatus = findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id);

		if (!currentTicketStatus.getStatus().equals(Status.PENDING)) {
			throw new TicketAlreadyEvaluatedException();
		}

		ticketEntity.setDeleted(true);

		ticketRepository.save(ticketEntity);
	}
}
