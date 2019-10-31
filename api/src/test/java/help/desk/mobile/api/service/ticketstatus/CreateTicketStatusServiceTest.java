package help.desk.mobile.api.service.ticketstatus;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.ticket.InvalidTicketException;
import help.desk.mobile.api.repository.ticketstatus.TicketStatusRepository;
import help.desk.mobile.api.service.ticket.ExistsByIdTicketService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;

/**
 * @author eduardo.thums
 */
public class CreateTicketStatusServiceTest extends AbstractUnitTest {

	@InjectMocks
	private CreateTicketStatusService createTicketStatusService;

	@Mock
	private TicketStatusRepository ticketStatusRepository;

	@Mock
	private ExistsByIdTicketService existsByIdTicketService;

	@Captor
	private ArgumentCaptor<TicketStatusEntity> ticketStatusEntityArgumentCaptor;

	@Test

	public void createTicketStatus() {
		// Arrange
		final Long ticketId = 1L;
		final boolean currentStatus = true;
		final Status status = Status.PENDING;

		BDDMockito.given(existsByIdTicketService.existsById(ticketId))
				.willReturn(true);

		// Act
		createTicketStatusService.createTicketStatus(ticketId, currentStatus, status);

		// Assert
		BDDMockito.then(ticketStatusRepository)
				.should()
				.saveAndFlush(ticketStatusEntityArgumentCaptor.capture());

		final TicketStatusEntity persistedTicketStatusEntity = ticketStatusEntityArgumentCaptor.getValue();

		Assert.assertEquals(ticketId, persistedTicketStatusEntity.getTicketId());
		Assert.assertEquals(currentStatus, persistedTicketStatusEntity.isCurrentStatus());
		Assert.assertEquals(status, persistedTicketStatusEntity.getStatus());
	}


	@Test(expected = InvalidTicketException.class)
	public void createTicketStatus_throwsInvalidTicketException() {
		// Arrange
		final Long ticketId = 1L;

		BDDMockito.given(existsByIdTicketService.existsById(ticketId))
				.willReturn(false);

		// Act
		createTicketStatusService.createTicketStatus(ticketId, false, Status.PENDING);
	}
}
