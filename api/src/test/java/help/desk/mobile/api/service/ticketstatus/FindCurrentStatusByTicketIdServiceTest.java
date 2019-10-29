package help.desk.mobile.api.service.ticketstatus;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import help.desk.mobile.api.exception.ticket.InvalidTicketStatusException;
import help.desk.mobile.api.repository.ticketstatus.TicketStatusRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public class FindCurrentStatusByTicketIdServiceTest extends AbstractUnitTest {

	@InjectMocks
	private FindCurrentStatusByTicketIdService findCurrentStatusByTicketIdService;

	@Mock
	private TicketStatusRepository ticketStatusRepository;

	@Test
	public void findCurrentStatusByTicketId() {
		// Arrange
		final Long ticketId = 1L;

		final TicketStatusEntity mockedResponse = new TicketStatusEntity();

		BDDMockito.given(ticketStatusRepository.findByTicketIdAndCurrentStatusTrue(ticketId))
				.willReturn(Optional.of(mockedResponse));

		// Act
		final TicketStatusEntity response = findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(ticketId);

		// Assert
		Assert.assertEquals(mockedResponse, response);
	}

	@Test(expected = InvalidTicketStatusException.class)
	public void findCurrentStatusByTicketId_throwsInvalidTicketStatusException() {
		// Arrange
		final Long ticketId = 1L;

		BDDMockito.given(ticketStatusRepository.findByTicketIdAndCurrentStatusTrue(ticketId))
				.willReturn(Optional.empty());

		// Act
		findCurrentStatusByTicketIdService.findCurrentStatusByTicketId(ticketId);
	}
}
