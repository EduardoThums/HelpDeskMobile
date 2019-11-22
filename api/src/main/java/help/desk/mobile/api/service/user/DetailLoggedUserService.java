package help.desk.mobile.api.service.user;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.user.response.UserDetailsResponse;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.exception.user.InvalidUserException;
import help.desk.mobile.api.mapper.UserMapper;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.area.FindAreaDetailsByIdService;
import org.springframework.stereotype.Service;

/**
 * @author eduardo.thums
 */
@Service
public class DetailLoggedUserService {

	private UserRepository userRepository;

	private UserMapper userMapper;

	private CustomUserDetailsService customUserDetailsService;

	private FindAreaDetailsByIdService findAreaDetailsByIdService;

	public DetailLoggedUserService(UserRepository userRepository, UserMapper userMapper, CustomUserDetailsService customUserDetailsService, FindAreaDetailsByIdService findAreaDetailsByIdService) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.customUserDetailsService = customUserDetailsService;
		this.findAreaDetailsByIdService = findAreaDetailsByIdService;
	}

	public UserDetailsResponse detailLoggedUser() {
		final UserEntity loggedUser = userRepository.findById(customUserDetailsService.getUser().getId())
				.orElseThrow(InvalidUserException::new);

		final AreaDetailsResponse area = findAreaDetailsByIdService.findDetailsById(loggedUser.getAreaId());

		return userMapper.toUserDetailsResponse(loggedUser, area);
	}
}
