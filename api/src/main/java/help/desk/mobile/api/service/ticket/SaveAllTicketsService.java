package help.desk.mobile.api.service.ticket;

import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.repository.ticket.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveAllTicketsService {

    private TicketRepository ticketRepository;

    public SaveAllTicketsService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void saveAll(List<TicketEntity> tickets){
        ticketRepository.saveAll(tickets);
    }
}


