package help.desk.mobile.api.controller.authentication.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public class AuthenticationRequest {

	private String username;

	private String password;
}
