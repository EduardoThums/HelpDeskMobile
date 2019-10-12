package help.desk.mobile.api.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author eduardo.thums
 */
@Data
@Entity
@Table(name = "area")
public class AreaEntity {

	@Id
	@SequenceGenerator(name = "area_id_seq", sequenceName = "area_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "area_id_seq", strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 256)
	private String name;
}
