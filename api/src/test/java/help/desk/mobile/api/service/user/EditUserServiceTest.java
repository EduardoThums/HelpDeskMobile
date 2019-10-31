package help.desk.mobile.api.service.user;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.config.security.UserPrincipal;
import help.desk.mobile.api.controller.user.request.EditUserRequest;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.exception.area.InvalidAreaException;
import help.desk.mobile.api.exception.user.InvalidUserException;
import help.desk.mobile.api.exception.user.UserAlreadyExistsException;
import help.desk.mobile.api.exception.user.WrongPasswordException;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;
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
	private ExistsByPhoneAndUserIdService existsByPhoneAndUserIdService;

	@Mock
	private ExistsByIdAreaService existsByIdAreaService;

	@Mock
	private CustomUserDetailsService customUserDetailsService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Captor
	private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

	@Test
	public void editUser() throws UserAlreadyExistsException {
		// Arrange
		final Long id = 1L;
		final String password = "password";
		final String confirmPassword = "password";
		final Long areaId = 2L;
		final String phone = "40028922";
		final String encodedPassword = "encodedPassword";

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

		BDDMockito.given(passwordEncoder.encode(password))
				.willReturn(encodedPassword);

		BDDMockito.given(existsByIdAreaService.existsById(areaId))
				.willReturn(true);

		BDDMockito.given(existsByPhoneAndUserIdService.existsByPhoneAndUserId(phone, id))
				.willReturn(false);

		// Act
		editUserService.editUser(request);

		// Assert
		BDDMockito.then(userRepository)
				.should()
				.save(userEntityArgumentCaptor.capture());

		final UserEntity persistedUserEntity = userEntityArgumentCaptor.getValue();
		Assert.assertEquals(encodedPassword, persistedUserEntity.getPassword());
		Assert.assertEquals(areaId, persistedUserEntity.getAreaId());
		Assert.assertEquals(phone, persistedUserEntity.getPhone());
	}


	@Test(expected = InvalidUserException.class)
	public void editUser_throwsInvalidUserException_whenUserDoesNotExist() throws UserAlreadyExistsException {
		// Arrange
		final Long userId = 1L;

		final UserPrincipal someRandomUser = setupUser(2L);

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(someRandomUser);

		// Act
		editUserService.editUser(new EditUserRequest());
	}


	@Test(expected = WrongPasswordException.class)
	public void editUser_throwsWrongPasswordException_whenPasswordsAreNotEqual() throws UserAlreadyExistsException {
		// Arrange
		final Long userId = 1L;
		final String password = "password";
		final String confirmPassword = "anotherPassword";

		final EditUserRequest request = new EditUserRequest();
		request.setPassword(password);
		request.setConfirmPassword(confirmPassword);

		final UserPrincipal loggedUser = setupUser(userId);

		final UserEntity mockedUserEntity = new UserEntity();

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(userRepository.findById(userId))
				.willReturn(Optional.of(mockedUserEntity));

		// Act
		editUserService.editUser(request);
	}


	@Test(expected = InvalidAreaException.class)
	public void editUser_throwsInvalidAreaException_whenGivenAreaIdAreaDoesNotExist() throws UserAlreadyExistsException {
		// Arrange
		final Long userId = 1L;
		final String password = "password";
		final String confirmPassword = "password";
		final Long areaId = 2L;

		final EditUserRequest request = new EditUserRequest();
		request.setPassword(password);
		request.setConfirmPassword(confirmPassword);
		request.setAreaId(areaId);

		final UserPrincipal loggedUser = setupUser(userId);

		final UserEntity mockedUserEntity = new UserEntity();

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(userRepository.findById(userId))
				.willReturn(Optional.of(mockedUserEntity));

		BDDMockito.given(existsByIdAreaService.existsById(areaId))
				.willReturn(false);

		// Act
		editUserService.editUser(request);
	}


	@Test(expected = UserAlreadyExistsException.class)
	public void editUser_throwsUserAlreadyExistsException_whenGivenPhoneIsAlreadyUsed() throws UserAlreadyExistsException {
		// Arrange
		final Long userId = 1L;
		final String password = "password";
		final String confirmPassword = "password";
		final Long areaId = 2L;
		final String phone = "40028922";

		final EditUserRequest request = new EditUserRequest();
		request.setPassword(password);
		request.setConfirmPassword(confirmPassword);
		request.setAreaId(areaId);
		request.setPhone(phone);

		final UserPrincipal loggedUser = setupUser(userId);

		final UserEntity mockedUserEntity = new UserEntity();

		BDDMockito.given(customUserDetailsService.getUser())
				.willReturn(loggedUser);

		BDDMockito.given(userRepository.findById(userId))
				.willReturn(Optional.of(mockedUserEntity));

		BDDMockito.given(existsByIdAreaService.existsById(areaId))
				.willReturn(true);

		BDDMockito.given(existsByPhoneAndUserIdService.existsByPhoneAndUserId(phone, userId))
				.willReturn(true);

		// Act
		editUserService.editUser(request);
	}

	private UserPrincipal setupUser(Long id) {
		return UserPrincipal
				.builder()
				.id(id)
				.build();
	}
}
