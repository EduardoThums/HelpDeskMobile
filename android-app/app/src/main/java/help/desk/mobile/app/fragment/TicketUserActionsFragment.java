package help.desk.mobile.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helpdeskmobile.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.activity.HomeActivity;
import help.desk.mobile.app.model.APIError;
import help.desk.mobile.app.model.TicketDetails;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.TicketService;
import help.desk.mobile.app.utils.ErrorUtils;
import help.desk.mobile.app.utils.ToastUtils;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static help.desk.mobile.app.constants.ResultMessages.TICKET_CANCELLED_WITH_SUCCESS;

@NoArgsConstructor
public class TicketUserActionsFragment extends Fragment {

    private TicketDetails ticket;

    private TicketService ticketService;

    @OnClick(R.id.cancel_ticket_button)
    protected void cancelTicket() {
        this.ticketService.cancelTicket(ticket.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    APIError error = ErrorUtils.parseError(response);
                    ToastUtils.displayMessage(TicketUserActionsFragment.this.getContext(), error.getMessage());
                    return;
                }
                handleSuccessfulResponse();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(TicketUserActionsFragment.this.getContext());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.ticket = (TicketDetails) getArguments().getSerializable("ticket");
        this.ticketService = new BaseService(getContext()).getTicketService();

        final View view = inflater.inflate(R.layout.fragment_ticket_user_actions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void handleSuccessfulResponse() {
        final Context context = TicketUserActionsFragment.this.getContext();
        ToastUtils.displayMessage(context, TICKET_CANCELLED_WITH_SUCCESS);
        final Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

}
