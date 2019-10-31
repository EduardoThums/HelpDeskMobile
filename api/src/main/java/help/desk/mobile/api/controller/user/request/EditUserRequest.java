package help.desk.mobile.api.controller.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author eduardo.thums
 */
@Setter
@Getter
@NoArgsConstructor
public class EditUserRequest {

	private String phone;

	private Long areaId;

	private String password;

	private String confirmPassword;
}
