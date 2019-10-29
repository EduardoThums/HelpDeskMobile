package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author eduardo.thums
 */
public class ExistsByIdTicketServiceTest extends AbstractUnitTest {

	@InjectMocks
	private ExistsByIdTicketService existsByIdTicketService;

	@Mock
	private TicketRepository ticketRepository;

	@Test
	public void existsById() {
		// Arrange
		final Long id = 1L;

		BDDMockito.given(ticketRepository.existsById(id))
				.willReturn(true);

		// Act
		final boolean response = existsByIdTicketService.existsById(id);

		// Assert
		Assert.assertTrue(response);
	}

}
