package help.desk.mobile.api.controller.user;

import help.desk.mobile.api.controller.user.request.EditUserRequest;
import help.desk.mobile.api.exception.user.UserAlreadyExistsException;
import help.desk.mobile.api.service.user.EditUserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private EditUserService editUserService;

	public UserController(EditUserService editUserService) {
		this.editUserService = editUserService;
	}

	@PutMapping
	public void editDetails(@RequestBody EditUserRequest request) throws UserAlreadyExistsException {
		editUserService.editUser(request);
	}
}
