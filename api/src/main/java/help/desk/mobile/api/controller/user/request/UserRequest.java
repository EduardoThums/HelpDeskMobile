package help.desk.mobile.api.controller.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public class UserRequest {

	@NotBlank
	@Size(max = 256)
	private String name;

	@Email
	@Size(max = 256)
	private String email;

	@NotBlank
	@Size(max = 2048)
	private String password;

	@CPF
	@Size(max = 11)
	private String cpf;

	@NotBlank
	@Size(max = 12)
	private String phone;

	@NotNull
	private Long areaId;
}
