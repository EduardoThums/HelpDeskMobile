package help.desk.mobile.api.service.user;

import help.desk.mobile.api.repository.user.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class ExistsByPhoneByLoggedUserService {

	private UserRepository userRepository;

	public ExistsByPhoneByLoggedUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean existsByPhoneByLoggedUser(String phone, Long id) {
		return userRepository.existsByPhoneAndId(phone, id);
	}
}
