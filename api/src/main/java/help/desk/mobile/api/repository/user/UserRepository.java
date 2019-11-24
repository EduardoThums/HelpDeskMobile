package help.desk.mobile.api.repository.user;

import help.desk.mobile.api.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByIdAndDeletedFalse(Long id);

	Optional<UserEntity> findByUsernameAndDeletedFalse(String username);

	@Query("SELECT count(1) > 0 " +
			"FROM UserEntity u " +
			"WHERE u.phone = :phone " +
			"AND u.id != :id")
	boolean existsByPhoneAndId(@Param("phone") String phone, @Param("id") Long id);

	@Query("SELECT count(1) > 0 " +
			"FROM UserEntity u " +
			"WHERE u.email LIKE :email " +
			"OR u.cpf LIKE :cpf " +
			"OR u.phone LIKE :phone")
	boolean existsByEmailOrCpfOrPhone(@Param("email") String email, @Param("cpf") String cpf, @Param("phone") String phone);
}
