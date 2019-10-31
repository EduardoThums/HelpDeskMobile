package help.desk.mobile.api.service.user;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.controller.user.request.EditUserRequest;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.exception.area.InvalidAreaException;
import help.desk.mobile.api.exception.user.InvalidUserException;
import help.desk.mobile.api.exception.user.UserAlreadyExistsException;
import help.desk.mobile.api.exception.user.WrongPasswordException;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class EditUserService {

	private UserRepository userRepository;

	private ExistsByPhoneAndUserIdService existsByPhoneAndUserIdService;

	private ExistsByIdAreaService existsByIdAreaService;

	private CustomUserDetailsService customUserDetailsService;

	private PasswordEncoder passwordEncoder;

	public EditUserService(UserRepository userRepository, ExistsByPhoneAndUserIdService existsByPhoneAndUserIdService, ExistsByIdAreaService existsByIdAreaService, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.existsByPhoneAndUserIdService = existsByPhoneAndUserIdService;
		this.existsByIdAreaService = existsByIdAreaService;
		this.customUserDetailsService = customUserDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	public void editUser(EditUserRequest request) throws UserAlreadyExistsException {
		final Long loggedUserId = customUserDetailsService.getUser().getId();

		final UserEntity loggedUser = userRepository.findById(loggedUserId)
				.orElseThrow(InvalidUserException::new);

		if (request.getPassword() != null || request.getConfirmPassword() != null) {
			if (!request.getPassword().equals(request.getConfirmPassword())) {
				throw new WrongPasswordException();
			}

			loggedUser.setPassword(passwordEncoder.encode(request.getPassword()));
		}

		if (request.getAreaId() != null) {
			if (!existsByIdAreaService.existsById(request.getAreaId())) {
				throw new InvalidAreaException();
			}

			loggedUser.setAreaId(request.getAreaId());
		}

		if (request.getPhone() != null) {
			if (existsByPhoneAndUserIdService.existsByPhoneAndUserId(request.getPhone(), loggedUserId)) {
				throw new UserAlreadyExistsException();
			}

			loggedUser.setPhone(request.getPhone());
		}

		userRepository.save(loggedUser);
	}
}
