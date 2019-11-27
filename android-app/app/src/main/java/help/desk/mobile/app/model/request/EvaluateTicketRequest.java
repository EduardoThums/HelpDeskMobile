package help.desk.mobile.app.model.request;

import help.desk.mobile.app.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateTicketRequest {

    private Status newStatus;

    private String curatorshipMessage;

}