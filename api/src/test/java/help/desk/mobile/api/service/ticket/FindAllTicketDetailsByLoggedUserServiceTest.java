package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.dto.TicketDetailsDto;
import help.desk.mobile.api.mapper.TicketMapper;
import help.desk.mobile.api.repository.ticket.TicketRepository;
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
public class FindAllTicketDetailsByLoggedUserServiceTest extends AbstractUnitTest {

	@InjectMocks
	private FindAllTicketDetailsByLoggedUserService findAllTicketDetailsByLoggedUserService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private TicketMapper ticketMapper;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Test
	public void findAllByLoggedUser() {
		// Arrange
		final Long authorId = 1L;

		final UserPrincipal loggedUser = setupUser(authorId);

		final TicketDetailsDto ticketDetailsDto = new TicketDetailsDto();

		final TicketDetailsResponse mockedResponse = new TicketDetailsResponse();

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(ticketRepository.findAllTicketDetailsDtoByAuthorId(authorId))
				.willReturn(Collections.singletonList(ticketDetailsDto));

		BDDMockito.given(ticketMapper.toTicketDetailsResponses(Collections.singletonList(ticketDetailsDto)))
				.willReturn(Collections.singletonList(mockedResponse));

		// Act
		final List<TicketDetailsResponse> responseList = findAllTicketDetailsByLoggedUserService.findAllByLoggedUser();

		// Assert
		Assert.assertFalse(responseList.isEmpty());

		final TicketDetailsResponse response = responseList.get(0);

		Assert.assertEquals(mockedResponse, response);
	}

	private UserPrincipal setupUser(Long authorId) {
		return UserPrincipal
				.builder()
				.id(authorId)
				.build();
	}
}
