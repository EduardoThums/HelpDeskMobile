package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.exception.ticket.InvalidTicketException;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import help.desk.mobile.api.service.area.FindAreaDetailsByIdService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public class FindTicketDetailsByIdServiceTest extends AbstractUnitTest {

	@InjectMocks
	private FindTicketDetailsByIdService findTicketDetailsByIdService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private TicketMapper ticketMapper;

	@Mock
	private FindAreaDetailsByIdService findAreaDetailsByIdService;

	@Test
	public void findDetailsById() {
		// Arrange
		final Long id = 1L;
		final Long areaId = 2L;

		final TicketEntity mockedTicketEntity = new TicketEntity();
		mockedTicketEntity.setAreaId(areaId);

		final AreaDetailsResponse mockedArea = new AreaDetailsResponse();

		final TicketDetailsResponse mockedResponse = new TicketDetailsResponse();

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.of(mockedTicketEntity));

		BDDMockito.given(findAreaDetailsByIdService.findDetailsById(areaId))
				.willReturn(mockedArea);

		BDDMockito.given(ticketMapper.toTicketDetailsResponse(mockedTicketEntity, mockedArea))
				.willReturn(mockedResponse);

		// Act
		final TicketDetailsResponse response = findTicketDetailsByIdService.findDetailsById(id);

		// Assert
		Assert.assertEquals(mockedResponse, response);
	}

	@Test(expected = InvalidTicketException.class)
	public void findDetailsById_throwsInvalidTicketException() {
		// Arrange
		final Long id = 1L;

		BDDMockito.given(ticketRepository.findById(id))
				.willReturn(Optional.empty());

		// Act
		findTicketDetailsByIdService.findDetailsById(id);
	}
}
