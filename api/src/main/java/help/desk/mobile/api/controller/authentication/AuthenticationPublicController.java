package help.desk.mobile.api.controller.authentication;

import help.desk.mobile.api.config.security.AuthenticationService;
import help.desk.mobile.api.controller.authentication.request.AuthenticationRequest;
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
@RequestMapping("/public")
public class AuthenticationPublicController {

	private AuthenticationService authenticationService;

	public AuthenticationPublicController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody AuthenticationRequest request) {
		return ResponseEntity.ok(authenticationService.authenticate(request));
	}
}
