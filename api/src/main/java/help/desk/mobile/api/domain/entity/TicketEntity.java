package help.desk.mobile.api.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author eduardo.thums
 */
@Setter
@Getter
@Table(name = "ticket")
@Entity
@NoArgsConstructor
public class TicketEntity {

	@Id
	@SequenceGenerator(name = "ticket_id_seq", sequenceName = "ticket_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "ticket_id_seq", strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 256)
	private String title;

	@NotBlank
	@Size(max = 512)
	private String description;

	@Size(max = 512)
	private String curatorshipMessage;

	@NotNull
	private Long areaId;

	@NotNull
	@CreatedBy
	private Long authorId;

	@NotNull
	@CreatedDate
	private LocalDateTime createdDate;

	private Long curatorId;

	public TicketEntity(@NotBlank @Size(max = 256) String title, @NotBlank @Size(max = 512) String description, @NotNull Long areaId, @NotNull Long authorId, @NotNull LocalDateTime createdDate) {
		this.title = title;
		this.description = description;
		this.areaId = areaId;
		this.authorId = authorId;
		this.createdDate = createdDate;
	}
}
