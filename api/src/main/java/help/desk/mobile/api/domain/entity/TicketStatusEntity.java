package help.desk.mobile.api.domain.entity;

import help.desk.mobile.api.domain.status.Status;
import help.desk.mobile.api.domain.type.PostgreSQLEnumType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

/**
 * @author eduardo.thums
 */
@Getter
@Table(name = "ticket_status")
@Entity
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
@NoArgsConstructor
public class TicketStatusEntity {

	@Id
	@SequenceGenerator(name = "ticket_status_id_seq", sequenceName = "ticket_status_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "ticket_status_id_seq", strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long ticketId;

	@NotNull
	private boolean currentStatus;

	@NotNull
	@CreatedDate
	@PastOrPresent
	private LocalDateTime createdDate;

	@NotNull
	@Type(type = "pgsql_enum")
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotNull
	private boolean deleted;

	public TicketStatusEntity(@NotNull Long ticketId, @NotNull boolean currentStatus, @NotNull @PastOrPresent LocalDateTime createdDate, @NotNull Status status) {
		this.ticketId = ticketId;
		this.currentStatus = currentStatus;
		this.createdDate = createdDate;
		this.status = status;
	}
}
