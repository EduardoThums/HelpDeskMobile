package help.desk.mobile.api.repository.ticket;

import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.dto.TicketDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author eduardo.thums
 */
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

	@Query("SELECT NEW help.desk.mobile.api.dto.TicketDetailsDto( " +
			"t.id, " +
			"t.title, " +
			"t.description, " +
			"a.id, " +
			"a.name, " +
			"ts.id, " +
			"ts.status) " +
			"FROM TicketEntity t " +
			"INNER JOIN TicketStatusEntity ts " +
			"ON t.id = ts.ticketId " +
			"INNER JOIN AreaEntity a " +
			"ON t.areaId = a.id " +
			"WHERE t.deleted = FALSE " +
			"AND ts.currentStatus = TRUE " +
			"ORDER BY t.createdDate DESC")
	List<TicketDetailsDto> findAllTicketDetailsDto();
}
