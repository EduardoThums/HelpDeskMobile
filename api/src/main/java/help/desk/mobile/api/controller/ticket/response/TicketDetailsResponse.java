package help.desk.mobile.api.controller.ticket.response;

import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
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
public class TicketDetailsResponse {

	private String title;

	private String description;

	private AreaDetailsResponse area;
}
