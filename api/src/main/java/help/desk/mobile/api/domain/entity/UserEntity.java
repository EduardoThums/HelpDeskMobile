package help.desk.mobile.api.domain.entity;

import help.desk.mobile.api.domain.type.ProfileType;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author eduardo.thums
 */
@Table
@Entity(name = "user")
public class UserEntity {

	@Id
	@SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@Size(max = 256)
	private String name;

	@NotNull
	@Size(max = 256)
	private String username;

	@NotNull
	@Size(max = 2048)
	private String password;

	@Email
	@NotNull
	@Size(max = 256)
	private String email;

	@CPF
	@NotNull
	@Size(max = 11)
	private String cpf;

	@NotNull
	@Size(max = 12)
	private String phone;

	@NotNull
	private Long areaId;

	@NotNull
	private ProfileType type;
}
