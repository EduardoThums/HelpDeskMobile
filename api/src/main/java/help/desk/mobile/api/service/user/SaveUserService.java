package help.desk.mobile.api.service.user;

import help.desk.mobile.api.controller.user.request.UserRequest;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.domain.type.ProfileType;
import help.desk.mobile.api.exception.InvalidAreaException;
import help.desk.mobile.api.exception.UserAlreadyExistsException;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.ExistsAreaByIdService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class SaveUserService {

	private UserRepository userRepository;

	private ExistsAreaByIdService existsAreaByIdService;

	private PasswordEncoder passwordEncoder;

	public SaveUserService(UserRepository userRepository, ExistsAreaByIdService existsAreaByIdService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.existsAreaByIdService = existsAreaByIdService;
		this.passwordEncoder = passwordEncoder;
	}

	public Long saveUser(UserRequest request) throws UserAlreadyExistsException {
		if (userRepository.existsByEmailOrCpfOrPhone(request.getEmail(), request.getCpf(), request.getPhone())) {
			throw new UserAlreadyExistsException();
		}

		if (!existsAreaByIdService.existsById(request.getAreaId())) {
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
