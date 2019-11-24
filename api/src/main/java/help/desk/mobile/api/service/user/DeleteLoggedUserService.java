package help.desk.mobile.api.service.user;

import help.desk.mobile.api.config.security.CustomUserDetailsService;
import help.desk.mobile.api.domain.entity.TicketEntity;
import help.desk.mobile.api.domain.entity.UserEntity;
import help.desk.mobile.api.exception.user.InvalidUserException;
import help.desk.mobile.api.repository.user.UserRepository;
import help.desk.mobile.api.service.ticket.FindAllTicketsByAuthorIdService;
import help.desk.mobile.api.service.ticket.SaveAllTicketsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteLoggedUserService {

    private UserRepository userRepository;

    private CustomUserDetailsService customUserDetailsService;

    private FindAllTicketsByAuthorIdService findAllTicketsByAuthorIdService;

    private SaveAllTicketsService saveAllTicketsService;

    public DeleteLoggedUserService(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, FindAllTicketsByAuthorIdService findAllTicketsByAuthorIdService, SaveAllTicketsService saveAllTicketsService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.findAllTicketsByAuthorIdService = findAllTicketsByAuthorIdService;
        this.saveAllTicketsService = saveAllTicketsService;
    }

    public void deleteLoggedUser(){
        final Long loggedUserId = getLoggedUserId();

        final List<TicketEntity> tickets = findAllTicketsByAuthorIdService.findAllByAuthorId(loggedUserId);

        tickets.forEach(ticketEntity -> ticketEntity.setDeleted(true));

        saveAllTicketsService.saveAll(tickets);

        final UserEntity loggedUser = userRepository.findById(loggedUserId)
                .orElseThrow(InvalidUserException::new);

        loggedUser.setDeleted(true);

        userRepository.save(loggedUser);
    }

    private Long getLoggedUserId(){
        return customUserDetailsService.getUser().getId();
    }
}
