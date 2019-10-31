package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.ticket.request.EvaluateTicketRequest;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.ticket.*;
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
		mockedTicketEntity.setDeleted(false);
		mockedTicketEntity.setAuthorId(ticketAuthorId);

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

	@Test(expected = CuratorshipNotAllowedException.class)
	public void evaluate_throwsCuratorshipNotAllowedException_whenNewStatusIsPending() {
		// Arrange
		final Long id = 1L;
		final Status newStatus = Status.PENDING;

		final EvaluateTicketRequest request = new EvaluateTicketRequest();
		request.setNewStatus(newStatus);

		// Act
		evaluateTicketByIdService.evaluate(id, request);
	}

	@Test(expected = InvalidTicketException.class)
	public void evaluate_throwsInvalidTicketException_whenGivenTicketIdDoesNotExist() {
		// Arrange
		final Long id = 1L;
		final Status newStatus = Status.APPROVED;

		final EvaluateTicketRequest request = new EvaluateTicketRequest();
		request.setNewStatus(newStatus);

		// Act
		evaluateTicketByIdService.evaluate(id, request);
	}

	@Test(expected = TicketAlreadyCanceledException.class)
	public void evaluate_throwsTicketAlreadyCanceledException_whenTicketIsAlreadyDeleted() {
		// Arrange
		final Long id = 1L;
		final Status newStatus = Status.APPROVED;

		final EvaluateTicketRequest request = new EvaluateTicketRequest();
		request.setNewStatus(newStatus);

		final TicketEntity ticketEntity = new TicketEntity();
		ticketEntity.setDeleted(true);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(ticketEntity));

		// Act
		evaluateTicketByIdService.evaluate(id, request);
	}

	@Test(expected = EvaluateTicketOwnedException.class)
	public void evaluate_throwsEvaluateTicketOwnedException_whenSameTicketAuthorTryEvaluateIt() {
		// Arrange
		final Long id = 1L;
		final Status newStatus = Status.APPROVED;
		final Long userId = 2L;

		final EvaluateTicketRequest request = new EvaluateTicketRequest();
		request.setNewStatus(newStatus);

		final TicketEntity ticketEntity = new TicketEntity();
		ticketEntity.setAuthorId(userId);

		final UserPrincipal loggedUser = setupUser(userId);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(ticketEntity));

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		// Act
		evaluateTicketByIdService.evaluate(id, request);
	}

	@Test(expected = TicketAlreadyEvaluatedException.class)
	public void evaluate_throwsTicketAlreadyEvaluatedException_whenTicketIsAlreadyEvaluated() {
		// Arrange
		final Long id = 1L;
		final Status newStatus = Status.APPROVED;
		final Long loggedUserId = 2L;
		final Long authorId = 3L;

		final EvaluateTicketRequest request = new EvaluateTicketRequest();
		request.setNewStatus(newStatus);

		final TicketEntity ticketEntity = new TicketEntity();
		ticketEntity.setAuthorId(authorId);

		final UserPrincipal loggedUser = setupUser(loggedUserId);

		final TicketStatusEntity currentTicketStatus = new TicketStatusEntity();
		currentTicketStatus.setStatus(Status.APPROVED);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(ticketEntity));

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id))
				.willReturn(currentTicketStatus);

		// Act
		evaluateTicketByIdService.evaluate(id, request);
	}

	private UserPrincipal setupUser(Long userId) {
		return UserPrincipal
				.builder()
				.id(userId)
				.build();
	}
}
