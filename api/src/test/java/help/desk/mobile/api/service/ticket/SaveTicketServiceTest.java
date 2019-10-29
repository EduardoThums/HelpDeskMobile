package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.exception.area.InvalidAreaException;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import help.desk.mobile.api.service.ticketstatus.SaveTicketStatusService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author eduardo.thums
 */
public class SaveTicketServiceTest extends AbstractUnitTest {

	@InjectMocks
	private SaveTicketService saveTicketService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private TicketMapper ticketMapper;

	@Mock
	private ExistsByIdAreaService existsByIdAreaService;

	@Mock
	private SaveTicketStatusService saveTicketStatusService;

	@Test
	public void saveTicket() {
		// Arrange
		final Long areaId = 1L;
		final Long ticketId = 2L;

		final SaveTicketRequest request = new SaveTicketRequest();
		request.setAreaId(areaId);

		final TicketEntity ticketEntity = new TicketEntity();

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setId(ticketId);

		BDDMockito.given(existsByIdAreaService.existsById(areaId))
				.willReturn(true);

		BDDMockito.given(ticketMapper.toTicketEntity(request))
				.willReturn(ticketEntity);

		BDDMockito.given(ticketRepository.saveAndFlush(ticketEntity))
				.willReturn(mockedTicketEntity);

		// Act
		final Long response = saveTicketService.saveTicket(request);

		// Assert
		Assert.assertEquals(ticketId, response);

		BDDMockito.then(saveTicketStatusService)
				.should()
				.saveTicketStatus(ticketId, true, Status.PENDING);
	}


	@Test(expected = InvalidAreaException.class)
	public void saveTicket_throwsInvalidAreaException() {
		// Arrange
		final Long areaId = 1L;

		final SaveTicketRequest request = new SaveTicketRequest();
		request.setAreaId(areaId);

		// Act
		saveTicketService.saveTicket(request);
	}
}
