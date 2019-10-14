package help.desk.mobile.api.service.user;

import help.desk.mobile.api.AbstractUnitTest;
import help.desk.mobile.api.controller.user.request.UserRequest;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.domain.type.ProfileType;
import help.desk.mobile.api.exception.InvalidAreaException;
import help.desk.mobile.api.exception.UserAlreadyExistsException;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author eduardo.thums
 */
public class SaveUserServiceTest extends AbstractUnitTest {

	@InjectMocks
	private SaveUserService saveUserService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private ExistsByIdAreaService existsByIdAreaService;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Captor
	private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;

	@Test
	public void saveUser() throws UserAlreadyExistsException {
		// Arrange
		final String email = "email";
		final String cpf = "cpf";
		final String phone = "phone";
		final Long areaId = 1L;
		final String password = "password";
		final Long userId = 1L;
		final String encodedPassword = "encodedPassword";

		final UserRequest request = mockUserRequest();
		request.setEmail(email);
		request.setCpf(cpf);
		request.setPhone(phone);
		request.setAreaId(areaId);
		request.setPassword(password);

		final UserEntity mockedUserEntity = new UserEntity();
		mockedUserEntity.setId(userId);

		BDDMockito
				.given(userRepository.existsByEmailOrCpfOrPhone(email, cpf, phone))
				.willReturn(false);

		BDDMockito
				.given(existsByIdAreaService.existsById(areaId))
				.willReturn(true);

		BDDMockito
				.given(passwordEncoder.encode(password))
				.willReturn(encodedPassword);

		BDDMockito
				.given(userRepository.save(BDDMockito.any()))
				.willReturn(mockedUserEntity);

		// Act
		final Long response = saveUserService.saveUser(request);

		// Assert
		BDDMockito
				.verify(userRepository, BDDMockito.times(1))
				.save(userEntityArgumentCaptor.capture());

		final UserEntity savedUserEntity = userEntityArgumentCaptor.getValue();
		Assert.assertEquals(request.getName(), savedUserEntity.getName());
		Assert.assertEquals(email, savedUserEntity.getUsername());
		Assert.assertEquals(encodedPassword, savedUserEntity.getPassword());
		Assert.assertEquals(email, savedUserEntity.getEmail());
		Assert.assertEquals(cpf, savedUserEntity.getCpf());
		Assert.assertEquals(phone, savedUserEntity.getPhone());
		Assert.assertEquals(areaId, savedUserEntity.getAreaId());
		Assert.assertEquals(ProfileType.DEFAULT, savedUserEntity.getProfile());

		Assert.assertEquals(userId, response);
	}

	@Test(expected = UserAlreadyExistsException.class)
	public void saveUser_throwsUserAlreadyExistsException() throws UserAlreadyExistsException {
		// Arrange
		final String email = "email";
		final String cpf = "cpf";
		final String phone = "phone";

		final UserRequest request = mockUserRequest();
		request.setEmail(email);
		request.setCpf(cpf);
		request.setPhone(phone);

		BDDMockito
				.given(userRepository.existsByEmailOrCpfOrPhone(email, cpf, phone))
				.willReturn(true);

		// Act
		saveUserService.saveUser(request);
	}

	@Test(expected = InvalidAreaException.class)
	public void saveUser_throwsInvalidAreaException() throws UserAlreadyExistsException {
		// Arrange
		final String email = "email";
		final String cpf = "cpf";
		final String phone = "phone";
		final Long areaId = 1L;

		final UserRequest request = mockUserRequest();
		request.setEmail(email);
		request.setCpf(cpf);
		request.setPhone(phone);
		request.setAreaId(areaId);

		BDDMockito
				.given(userRepository.existsByEmailOrCpfOrPhone(email, cpf, phone))
				.willReturn(false);

		BDDMockito
				.given(existsByIdAreaService.existsById(areaId))
				.willReturn(false);

		// Act
		saveUserService.saveUser(request);
	}

	private UserRequest mockUserRequest() {
		final UserRequest request = new UserRequest();
		request.setName("MOCKED_NAME");
		request.setEmail("MOCKED_EMAIL");
		request.setPassword("MOCKED_PASSWORD");
		request.setCpf("MOCKED_CPF");
		request.setPhone("MOCKED_PHONE");
		request.setAreaId(1L);

		return request;
	}
}
