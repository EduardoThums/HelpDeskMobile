package help.desk.mobile.api.service.user;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.repository.user.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author eduardo.thums
 */
public class ExistsByPhoneAndUserIdServiceTest extends AbstractUnitTest {

	@InjectMocks
	private ExistsByPhoneAndUserIdService existsByPhoneAndUserIdService;

	@Mock
	private UserRepository userRepository;

	@Test
	public void existsByPhoneByLoggedUser() {
		// Arrange
		final String phone = "40028922";
		final Long userId = 1L;

		BDDMockito.given(userRepository.existsByPhoneAndId(phone, userId))
				.willReturn(true);

		// Act
		final boolean response = existsByPhoneAndUserIdService.existsByPhoneAndUserId(phone, userId);

		// Assert
		Assert.assertTrue(response);
	}
}
