package help.desk.mobile.api.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public enum ProfileType {

	ADMIN("ROLE_ADMIN"),
	DEFAULT("ROLE_DEFAULT");

	private final String role;
}
