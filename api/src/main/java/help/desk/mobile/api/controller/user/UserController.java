package help.desk.mobile.api.controller.user;

import help.desk.mobile.api.controller.user.request.EditUserRequest;
import help.desk.mobile.api.controller.user.response.UserDetailsResponse;
import help.desk.mobile.api.exception.user.UserAlreadyExistsException;
import help.desk.mobile.api.service.user.DetailLoggedUserService;
import help.desk.mobile.api.service.user.EditUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private EditUserService editUserService;

	private DetailLoggedUserService detailLoggedUserService;

	public UserController(EditUserService editUserService, DetailLoggedUserService detailLoggedUserService) {
		this.editUserService = editUserService;
		this.detailLoggedUserService = detailLoggedUserService;
	}

	@GetMapping
	public ResponseEntity<UserDetailsResponse> detailLoggedUser() {
		return ResponseEntity.ok(detailLoggedUserService.detailLoggedUser());
	}

	@PutMapping
	public void editDetails(@RequestBody EditUserRequest request) throws UserAlreadyExistsException {
		editUserService.editUser(request);
	}
}
