package help.desk.mobile.api.controller.user;

import help.desk.mobile.api.controller.user.request.UserRequest;
import help.desk.mobile.api.exception.user.UserAlreadyExistsException;
import help.desk.mobile.api.service.user.SaveUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author eduardo.thums
 */
@RestController
@RequestMapping("/public/user")
public class UserPublicController {

	private SaveUserService saveUserService;

	public UserPublicController(SaveUserService saveUserService) {
		this.saveUserService = saveUserService;
	}

	@PostMapping
	public ResponseEntity<Long> register(@RequestBody @Valid UserRequest request) throws UserAlreadyExistsException {
		return new ResponseEntity<>(saveUserService.saveUser(request), HttpStatus.CREATED);
	}
}
