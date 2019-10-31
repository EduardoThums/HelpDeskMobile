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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

/**
 * @author eduardo.thums
 */
public class FindAllPendingTicketToCuratorshipServiceTest extends AbstractUnitTest {

	@InjectMocks
	private FindAllPendingTicketToCuratorshipService findAllPendingTicketToCuratorshipService;

	@Mock
	private TicketRepository ticketRepository;

	@Mock
	private TicketMapper ticketMapper;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Test
	public void findAllPendingTicketToCuratorship() {
		// Arrange
		final Long loggedUserId = 1L;

		final Pageable pageable = BDDMockito.mock(Pageable.class);

		final UserPrincipal loggedUser = setupUser(loggedUserId);

		final TicketDetailsDto mockedTicketDetailsDto = new TicketDetailsDto();

		final TicketDetailsResponse mockedResponse = new TicketDetailsResponse();

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(ticketRepository.findAllTicketDetailsDtoByPendingStatus(loggedUserId, pageable))
				.willReturn(Collections.singletonList(mockedTicketDetailsDto));

		BDDMockito.given(ticketMapper.toTicketDetailsResponses(Collections.singletonList(mockedTicketDetailsDto)))
				.willReturn(Collections.singletonList(mockedResponse));

		// Act
		final Page<TicketDetailsResponse> pageResponse = findAllPendingTicketToCuratorshipService.findAllPendingTicketToCuratorship(pageable);

		// Assert
		Assert.assertFalse(pageResponse.getContent().isEmpty());

		final TicketDetailsResponse response = pageResponse.getContent().get(0);
		Assert.assertEquals(mockedResponse, response);
	}

	private UserPrincipal setupUser(Long userId) {
		return UserPrincipal
				.builder()
				.id(userId)
				.build();
	}
}
