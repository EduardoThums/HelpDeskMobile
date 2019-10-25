package help.desk.mobile.api.repository.ticketstatus;

import help.desk.mobile.api.domain.entity.TicketStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author eduardo.thums
 */
public interface TicketStatusRepository extends JpaRepository<TicketStatusEntity, Long> {
}
