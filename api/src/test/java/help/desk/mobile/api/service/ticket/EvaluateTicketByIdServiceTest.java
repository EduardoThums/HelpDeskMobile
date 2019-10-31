package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.ticket.request.EvaluateTicketRequest;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.ticketstatus.CreateTicketStatusService;
import help.desk.mobile.api.service.ticketstatus.FindCurrentStatusByTicketIdService;
import help.desk.mobile.api.service.ticketstatus.SaveTicketStatusService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public class EvaluateTicketByIdServiceTest extends AbstractUnitTest {

	@InjectMocks
	private EvaluateTicketByIdService evaluateTicketByIdService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService;

	@Mock
	private CreateTicketStatusService createTicketStatusService;

	@Mock
	private SaveTicketStatusService saveTicketStatusService;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Captor
	private ArgumentCaptor<TicketStatusEntity> ticketStatusEntityArgumentCaptor;

	@Test
	public void evaluate() {
		// Arrange
		final Long id = 1L;
		final Status newStatus = Status.APPROVED;
		final Long loggedUserId = 2L;
		final Long ticketAuthorId = 3L;
		final String curatorshipMessage = "Ok, it's seems fine, go have your new chair.";

		final EvaluateTicketRequest request = new EvaluateTicketRequest();
		request.setNewStatus(newStatus);
		request.setCuratorshipMessage(curatorshipMessage);

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setAuthorId(ticketAuthorId);
		mockedTicketEntity.setDeleted(false);

		final UserPrincipal loggedUser = setupUser(loggedUserId);

		final TicketStatusEntity mockedCurrentTicketStatus = new TicketStatusEntity();
		mockedCurrentTicketStatus.setStatus(Status.PENDING);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(mockedTicketEntity));

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id))
				.willReturn(mockedCurrentTicketStatus);

		// Act
		evaluateTicketByIdService.evaluate(id, request);

		// Assert
		BDDMockito.then(saveTicketStatusService)
				.should()
				.saveTicketStatus(ticketStatusEntityArgumentCaptor.capture());

		final TicketStatusEntity persistedTicketStatus = ticketStatusEntityArgumentCaptor.getValue();
		Assert.assertFalse(persistedTicketStatus.isCurrentStatus());

		BDDMockito.then(createTicketStatusService)
				.should()
				.createTicketStatus(id, true, newStatus);

		BDDMockito.then(createTicketStatusService)
				.shouldHaveNoMoreInteractions();
	}

	private UserPrincipal setupUser(Long userId) {
		return UserPrincipal
				.builder()
				.id(userId)
				.build();
	}
}
