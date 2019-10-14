package help.desk.mobile.api.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
public enum ProfileType {

	ADMIN("ADMIN_ROLE"),
	DEFAULT("DEFAULT_ROLE");

	private final String role;
}
