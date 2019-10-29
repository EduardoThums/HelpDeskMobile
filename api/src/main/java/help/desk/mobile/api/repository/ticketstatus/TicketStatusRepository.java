package help.desk.mobile.api.repository.ticketstatus;

import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author eduardo.thums
 */
public interface TicketStatusRepository extends JpaRepository<TicketStatusEntity, Long> {

	//	@Query("SELECT ts FROM TicketStatus ts " +
//			"WHERE ts.tickerId = :ticketId " +
//			"ORDER BY ticker")
//
	Optional<TicketStatusEntity> findByTicketIdAndCurrentStatusTrue(Long ticketId);
}
