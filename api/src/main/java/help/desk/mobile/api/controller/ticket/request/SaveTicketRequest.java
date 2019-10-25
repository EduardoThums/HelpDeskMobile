package help.desk.mobile.api.controller.ticket.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author eduardo.thums
 */
@Setter
@Getter
@NoArgsConstructor
public class SaveTicketRequest {

	@NotBlank
	@Size(max = 256)
	private String title;

	@NotBlank
	@Size(max = 512)
	private String description;

	@NotNull
	private Long areaId;
}
