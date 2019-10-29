package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
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
import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public class CancelTicketByIdServiceTest extends AbstractUnitTest {

	@InjectMocks
	private CancelTicketByIdService cancelTicketByIdService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Captor
	private ArgumentCaptor<TicketEntity> ticketEntityArgumentCaptor;

	@Test
	public void cancelById() {
		// Arrange
		final Long id = 1L;
		final Long authorUserId = 2L;

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setDeleted(false);
		mockedTicketEntity.setAuthorId(authorUserId);

		final TicketStatusEntity mockedTicketStatusEntity = new TicketStatusEntity();
		mockedTicketStatusEntity.setStatus(Status.PENDING);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(mockedTicketEntity));

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(setupUser(authorUserId));

		BDDMockito.given(findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id))
				.willReturn(mockedTicketStatusEntity);

		// Act
		cancelTicketByIdService.cancelById(id);

		// Assert
		BDDMockito.then(ticketRepository)
				.should()
				.save(ticketEntityArgumentCaptor.capture());

		final TicketEntity persistedTicketEntity = ticketEntityArgumentCaptor.getValue();

		Assert.assertTrue(persistedTicketEntity.isDeleted());
	}

	@Test(expected = InvalidTicketException.class)
	public void cancelById_throwsInvalidTicketException() {
		// Arrange
		final Long id = 1L;

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.empty());

		// Act
		cancelTicketByIdService.cancelById(id);
	}

	@Test(expected = TicketAlreadyCanceledException.class)
	public void cancelById_throwsTicketAlreadyCanceledException() {
		// Arrange
		final Long id = 1L;

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setDeleted(true);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(mockedTicketEntity));

		// Act
		cancelTicketByIdService.cancelById(id);
	}

	@Test(expected = TicketNotAllowedException.class)
	public void cancelById_throwsTicketNotAllowedException() {
		// Arrange
		final Long id = 1L;
		final Long authorUserId = 2L;

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setDeleted(false);
		mockedTicketEntity.setAuthorId(authorUserId);


		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(mockedTicketEntity));

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(setupUser(3L));

		// Act
		cancelTicketByIdService.cancelById(id);
	}

	@Test(expected = TicketAlreadyEvaluatedException.class)
	public void cancelById_throwsTicketAlreadyEvaluatedException() {
		// Arrange
		final Long id = 1L;
		final Long authorUserId = 2L;

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setDeleted(false);
		mockedTicketEntity.setAuthorId(authorUserId);

		final TicketStatusEntity mockedTicketStatusEntity = new TicketStatusEntity();
		mockedTicketStatusEntity.setStatus(Status.APPROVED);

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(mockedTicketEntity));

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(setupUser(authorUserId));

		BDDMockito.given(findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(id))
				.willReturn(mockedTicketStatusEntity);

		// Act
		cancelTicketByIdService.cancelById(id);
	}

	private UserPrincipal setupUser(Long userId) {
		return UserPrincipal
				.builder()
				.id(userId)
				.build();
	}
}
