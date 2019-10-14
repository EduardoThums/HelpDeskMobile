package help.desk.mobile.api.domain.entity;

import help.desk.mobile.api.domain.type.PostgreSQLEnumType;
import help.desk.mobile.api.domain.type.ProfileType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author eduardo.thums
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "\"user\"")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
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
	@Type(type = "pgsql_enum")
	@Enumerated(EnumType.STRING)
	private ProfileType profile;

	public UserEntity(@NotNull @Size(max = 256) String name, @NotNull @Size(max = 256) String username, @NotNull @Size(max = 2048) String password, @Email @NotNull @Size(max = 256) String email, @CPF @NotNull @Size(max = 11) String cpf, @NotNull @Size(max = 12) String phone, @NotNull Long areaId, @NotNull ProfileType profile) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.cpf = cpf;
		this.phone = phone;
		this.areaId = areaId;
		this.profile = profile;
	}
}
