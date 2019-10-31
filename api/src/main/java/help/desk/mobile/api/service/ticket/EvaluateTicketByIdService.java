package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.controller.ticket.request.EvaluateTicketRequest;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.ticket.*;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.ticketstatus.CreateTicketStatusService;
import help.desk.mobile.api.service.ticketstatus.FindCurrentStatusByTicketIdService;
import help.desk.mobile.api.service.ticketstatus.SaveTicketStatusService;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class EvaluateTicketByIdService {

	private TicketRepository ticketRepository;

	private FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService;

	private CreateTicketStatusService createTicketStatusService;

	private SaveTicketStatusService saveTicketStatusService;

	private CustomUserDetailsService customUserDetailsService;

	public EvaluateTicketByIdService(TicketRepository ticketRepository, FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService, CreateTicketStatusService createTicketStatusService, SaveTicketStatusService saveTicketStatusService, CustomUserDetailsService customUserDetailsService) {
		this.ticketRepository = ticketRepository;
		this.findCurrentStatusByTicketIdService = findCurrentStatusByTicketIdService;
		this.createTicketStatusService = createTicketStatusService;
		this.saveTicketStatusService = saveTicketStatusService;
		this.customUserDetailsService = customUserDetailsService;
	}

	public void evaluate(Long id, EvaluateTicketRequest request) {
		if (request.getNewStatus().equals(Status.PENDING)) {
			throw new CuratorshipNotAllowedException();
		}

		final TicketEntity ticketEntity = ticketRepository.findById(id)
				.orElseThrow(InvalidTicketException::new);

		final Long loggedUserId = customUserDetailsService.getUser().getId();

		if (ticketEntity.getAuthorId().equals(loggedUserId)) {
			throw new EvaluateTicketOwnedException();
		}

		if (ticketEntity.isDeleted()) {
			throw new TicketAlreadyCanceledException();
		}

		final TicketStatusEntity currentTicketStatus = findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id);

		if (!currentTicketStatus.getStatus().equals(Status.PENDING)) {
			throw new TicketAlreadyEvaluatedException();
		}

		ticketEntity.setCuratorshipMessage(request.getCuratorshipMessage());

		currentTicketStatus.setCurrentStatus(false);

		saveTicketStatusService.saveTicketStatus(currentTicketStatus);

		createTicketStatusService.saveTicketStatus(id, true, request.getNewStatus());
	}
}
