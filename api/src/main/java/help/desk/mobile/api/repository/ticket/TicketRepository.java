package help.desk.mobile.api.repository.ticket;

import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.dto.TicketDetailsDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
			"AND t.authorId = :authorId " +
			"ORDER BY t.createdDate DESC")
	List<TicketDetailsDto> findAllTicketDetailsDtoByAuthorId(@Param("authorId") Long authorId);

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
			"AND ts.status = 'PENDING' " +
			"AND t.authorId != :loggedUserId " +
			"ORDER BY t.createdDate DESC")
	List<TicketDetailsDto> findAllTicketDetailsDtoByPendingStatus(@Param("loggedUserId") Long loggedUserId, Pageable pageable);
}
