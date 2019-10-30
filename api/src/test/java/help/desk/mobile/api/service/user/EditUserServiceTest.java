package help.desk.mobile.api.service.user;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.user.request.EditUserRequest;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public class EditUserServiceTest extends AbstractUnitTest {

	@InjectMocks
	private EditUserService editUserService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ExistsByPhoneByLoggedUserService existsByPhoneByLoggedUserService;

	@Mock
	private ExistsByIdAreaService existsByIdAreaService;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	public void editUser() {
		// Arrange
		final Long id = 1L;
		final String password = "password";
		final String confirmPassword = "password";
		final Long areaId = 2L;
		final String phone = "40028922";

		final EditUserRequest request = new EditUserRequest();
		request.setPassword(password);
		request.setConfirmPassword(confirmPassword);
		request.setAreaId(areaId);
		request.setPhone(phone);

		final UserPrincipal loggedUser = setupUser(id);

		final UserEntity userEntity = new UserEntity();

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(userRepository.findById(id))
				.willReturn(Optional.of(userEntity));

		BDDMockito.given(existsByIdAreaService.existsById(areaId))
				.willReturn(true);

		BDDMockito.given(existsByPhoneByLoggedUserService.existsByPhoneByLoggedUser(phone, id))
				.willReturn(false);

		// Act

		// Assert
	}

	private UserPrincipal setupUser(Long id) {
		return UserPrincipal
				.builder()
				.id(id)
				.build();
	}
}
