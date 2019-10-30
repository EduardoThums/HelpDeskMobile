package help.desk.mobile.api.dto;

import help.desk.mobile.api.domain.status.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author eduardo.thums
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailsDto {

	private Long id;

	private String title;

	private String description;

	private Long areaId;

	private String areaName;

	private Long ticketStatusId;

	private Status status;
}
