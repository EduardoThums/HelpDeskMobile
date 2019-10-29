package help.desk.mobile.api.mapper;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.status.Status;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
		final String title = "title";
		final String description = "description";
		final Status status = Status.PENDING;

		final TicketEntity ticketEntity = new TicketEntity();
		ticketEntity.setTitle(title);
		ticketEntity.setDescription(description);

		final AreaDetailsResponse area = new AreaDetailsResponse();

		// Act
		final TicketDetailsResponse response = ticketMapper.toTicketDetailsResponse(ticketEntity, area, status);

		// Assert
		Assert.assertEquals(title, response.getTitle());
		Assert.assertEquals(description, response.getDescription());
		Assert.assertEquals(area, response.getArea());
		Assert.assertEquals(status, response.getStatus());
	}

	private UserPrincipal setupUser() {
		return UserPrincipal
				.builder()
				.id(1L)
				.build();
	}
}
