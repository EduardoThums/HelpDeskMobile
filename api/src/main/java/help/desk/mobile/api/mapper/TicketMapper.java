package help.desk.mobile.api.mapper;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.controller.area.response.AreaDetailsResponse;
import help.desk.mobile.api.controller.ticket.request.SaveTicketRequest;
import help.desk.mobile.api.controller.ticket.response.TicketDetailsResponse;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.dto.TicketDetailsDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

	public TicketDetailsResponse toTicketDetailsResponse(TicketEntity ticketEntity, AreaDetailsResponse area, Status status) {
		return TicketDetailsResponse
				.builder()
				.id(ticketEntity.getId())
				.title(ticketEntity.getTitle())
				.description(ticketEntity.getDescription())
				.area(area)
				.status(status)
				.build();
	}

	public List<TicketDetailsResponse> toTicketDetailsResponses(List<TicketDetailsDto> ticketDetailsDtos) {
		return ticketDetailsDtos
				.stream()
				.map(ticketDetailsDto -> {
					final AreaDetailsResponse area = AreaDetailsResponse
							.builder()
							.id(ticketDetailsDto.getAreaId())
							.name(ticketDetailsDto.getAreaName())
							.build();

					return TicketDetailsResponse
							.builder()
							.id(ticketDetailsDto.getId())
							.title(ticketDetailsDto.getTitle())
							.description(ticketDetailsDto.getDescription())
							.area(area)
							.status(ticketDetailsDto.getStatus())
							.build();
				})
				.collect(Collectors.toList());
	}
}
