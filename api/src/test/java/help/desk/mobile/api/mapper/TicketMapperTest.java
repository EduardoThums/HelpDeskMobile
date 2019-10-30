package help.desk.mobile.api.mapper;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.dto.TicketDetailsDto;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

/**
 * @author eduardo.thums
 */
public class TicketMapperTest extends AbstractUnitTest {

	@InjectMocks
	private TicketMapper ticketMapper;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Test
	public void toTicketEntity() {
		// Arrange
		final String title = "title";
		final String description = "description";
		final Long areaId = 1L;

		final UserPrincipal loggedUser = setupUser();

		final SaveTicketRequest request = new SaveTicketRequest();
		request.setTitle(title);
		request.setDescription(description);
		request.setAreaId(areaId);

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		// Act
		final TicketEntity response = ticketMapper.toTicketEntity(request);

		// Assert
		Assert.assertEquals(title, response.getTitle());
		Assert.assertEquals(description, response.getDescription());
		Assert.assertEquals(areaId, response.getAreaId());
		Assert.assertEquals(loggedUser.getId(), response.getAuthorId());
		Assert.assertNotNull(response.getCreatedDate());
	}

	@Test
	public void toTicketDetailsResponse() {
		// Arrange
		final Long id = 1L;
		final String title = "title";
		final String description = "description";
		final Status status = Status.PENDING;

		final TicketEntity ticketEntity = new TicketEntity();
		ticketEntity.setId(id);
		ticketEntity.setTitle(title);
		ticketEntity.setDescription(description);

		final AreaDetailsResponse area = new AreaDetailsResponse();

		// Act
		final TicketDetailsResponse response = ticketMapper.toTicketDetailsResponse(ticketEntity, area, status);

		// Assert
		Assert.assertEquals(id, response.getId());
		Assert.assertEquals(title, response.getTitle());
		Assert.assertEquals(description, response.getDescription());
		Assert.assertEquals(area, response.getArea());
		Assert.assertEquals(status, response.getStatus());
	}


	@Test
	public void toTicketDetailsResponses() {
		// Arrange
		final Long areaId = 1L;
		final String areaName = "areaName";
		final Long id = 2L;
		final String title = "title";
		final String description = "description";
		final Status status = Status.PENDING;

		final TicketDetailsDto ticketDetailsDto = new TicketDetailsDto();
		ticketDetailsDto.setAreaId(areaId);
		ticketDetailsDto.setAreaName(areaName);
		ticketDetailsDto.setId(id);
		ticketDetailsDto.setTitle(title);
		ticketDetailsDto.setDescription(description);
		ticketDetailsDto.setStatus(status);

		// Act
		final List<TicketDetailsResponse> responseList = ticketMapper.toTicketDetailsResponses(Collections.singletonList(ticketDetailsDto));

		// Assert
		Assert.assertFalse(responseList.isEmpty());

		final TicketDetailsResponse response = responseList.get(0);
		Assert.assertEquals(areaId, response.getArea().getId());
		Assert.assertEquals(areaName, response.getArea().getName());
		Assert.assertEquals(id, response.getId());
		Assert.assertEquals(title, response.getTitle());
		Assert.assertEquals(description, response.getDescription());
		Assert.assertEquals(status, response.getStatus());
	}

	private UserPrincipal setupUser() {
		return UserPrincipal
				.builder()
				.id(1L)
				.build();
	}
}
