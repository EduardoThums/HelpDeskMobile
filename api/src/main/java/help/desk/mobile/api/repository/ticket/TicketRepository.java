package help.desk.mobile.api.repository.ticket;

import help.desk.mobile.api.domain.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author eduardo.thums
 */
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {


}
