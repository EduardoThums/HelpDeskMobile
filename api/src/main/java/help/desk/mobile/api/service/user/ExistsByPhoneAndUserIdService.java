package help.desk.mobile.api.service.user;

import help.desk.mobile.api.repository.user.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class ExistsByPhoneAndUserIdService {

	private UserRepository userRepository;

	public ExistsByPhoneAndUserIdService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public boolean existsByPhoneAndUserId(String phone, Long id) {
		return userRepository.existsByPhoneAndId(phone, id);
	}
}
