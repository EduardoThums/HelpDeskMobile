package help.desk.mobile.api.mapper;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author eduardo.thums
 */
@Component
public class TicketMapper {

	private CustomUserDetailsService customUserDetailsService;

	public TicketMapper(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}

	public TicketEntity toTicketEntity(SaveTicketRequest request) {
		return new TicketEntity(
				request.getTitle(),
				request.getDescription(),
				request.getAreaId(),
				customUserDetailsService.getUser().getId(),
				LocalDateTime.now());
	}

	public TicketDetailsResponse toTicketDetailsResponse(TicketEntity ticketEntity, AreaDetailsResponse area) {
		return TicketDetailsResponse
				.builder()
				.title(ticketEntity.getTitle())
				.description(ticketEntity.getDescription())
				.area(area)
				.build();
	}
}
