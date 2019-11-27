package help.desk.mobile.app.service;

import help.desk.mobile.app.model.Page;
import help.desk.mobile.app.model.TicketDetails;
import help.desk.mobile.app.model.request.CreateTicketRequest;
import help.desk.mobile.app.model.request.EvaluateTicketRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TicketService {

    @GET("/ticket")
    Call<Page<TicketDetails>> loadTicketsFromUser();

    @GET("/admin/ticket/pending")
    Call<Page<TicketDetails>> loadPendingTickets();

    @POST("/ticket")
    Call<Long> creatTicket(@Body CreateTicketRequest createTicketRequest);

    @PUT("/admin/ticket/{id}")
    Call<Void> evaluateTicket(@Path("id") Long ticketId, @Body EvaluateTicketRequest ticketRequest);

    @DELETE("/ticket/{id}/cancel")
    Call<Void> cancelTicket(@Path("id") Long ticketId);
}
