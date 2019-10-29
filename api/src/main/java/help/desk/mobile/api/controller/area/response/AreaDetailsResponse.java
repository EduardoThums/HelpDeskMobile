package help.desk.mobile.api.controller.area.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author eduardo.thums
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaDetailsResponse {

	private Long id;

	private String name;
}
