package help.desk.mobile.api.service.user;

import help.desk.mobile.api.controller.user.request.UserRequest;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.domain.type.ProfileType;
import help.desk.mobile.api.exception.area.InvalidAreaException;
import help.desk.mobile.api.exception.user.UserAlreadyExistsException;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.ExistsByIdAreaService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class SaveUserService {

	private UserRepository userRepository;

	private ExistsByIdAreaService existsByIdAreaService;

	private PasswordEncoder passwordEncoder;

	public SaveUserService(UserRepository userRepository, ExistsByIdAreaService existsByIdAreaService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.existsByIdAreaService = existsByIdAreaService;
		this.passwordEncoder = passwordEncoder;
	}

	public Long saveUser(UserRequest request) throws UserAlreadyExistsException {
		if (userRepository.existsByEmailOrCpfOrPhone(request.getEmail(), request.getCpf(), request.getPhone())) {
			throw new UserAlreadyExistsException();
		}

		if (!existsByIdAreaService.existsById(request.getAreaId())) {
			throw new InvalidAreaException();
		}

		final UserEntity userEntity = new UserEntity(
				request.getName(),
				request.getEmail(),
				passwordEncoder.encode(request.getPassword()),
				request.getEmail(),
				request.getCpf(),
				request.getPhone(),
				request.getAreaId(),
				ProfileType.DEFAULT);

		return userRepository.save(userEntity).getId();
	}
}
