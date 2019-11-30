package help.desk.mobile.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helpdeskmobile.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.activity.MainActivity;
import help.desk.mobile.app.fragment.adapter.TicketDetailsAdapter;
import help.desk.mobile.app.model.Page;
import help.desk.mobile.app.model.TicketDetails;
import help.desk.mobile.app.model.UserDetails;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.TicketService;
import help.desk.mobile.app.service.UserService;
import help.desk.mobile.app.utils.ToastUtils;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static help.desk.mobile.app.constants.ResultMessages.ERROR_WHILE_GETTING_TICKETS;
import static help.desk.mobile.app.model.ProfileType.ADMIN;

@NoArgsConstructor
public class TicketListFragment extends Fragment {

    @BindView(R.id.ticket_list)
    protected RecyclerView ticketListRecyclerView;

    private List<TicketDetails> tickets;

    private UserDetails user;

    private TicketDetailsAdapter adapter;

    private TicketService ticketService;

    private UserService userService;

    @OnClick(R.id.logout_button)
    protected void logout() {
        final Context context = TicketListFragment.this.getContext();
        final Intent intent = new Intent(context, MainActivity.class);
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
        context.startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ticket_list, container, false);
        ButterKnife.bind(this, view);
        setupTicketListRecyclerView();
        final BaseService baseService = new BaseService(getContext());
        userService = baseService.getUserService();
        ticketService = baseService.getTicketService();
        user = getLoggedUser();
        return view;
    }

    public UserDetails getLoggedUser() {
        userService.getLoggedUser().enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    setLoggedUser();
                    setupData();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(TicketListFragment.this.getContext());
            }
        });
        return user;
    }

    private void setLoggedUser() {
        final Context context = TicketListFragment.this.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("userRole", user.getRole().toString());
        editor.apply();
    }

    private void setupTicketListRecyclerView() {
        ticketListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tickets = new ArrayList<>();
        adapter = new TicketDetailsAdapter(getContext(), tickets);
        ticketListRecyclerView.setAdapter(adapter);
        ticketListRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }


    private void setupData() {
        if (ADMIN.equals(user.getRole())) {
            loadTickets(ticketService.loadPendingTickets());
            return;
        }
        loadTickets(ticketService.loadTicketsFromUser());
    }

    private void loadTickets(final Call<Page<TicketDetails>> getTicketsCall) {

        getTicketsCall.enqueue(new Callback<Page<TicketDetails>>() {
            @Override
            public void onResponse(Call<Page<TicketDetails>> call, Response<Page<TicketDetails>> response) {
                if (!response.isSuccessful()) {
                    ToastUtils.displayMessage(TicketListFragment.this.getContext(), ERROR_WHILE_GETTING_TICKETS);
                    return;
                }
                tickets.clear();
                tickets.addAll(response.body().getContent());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Page<TicketDetails>> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(TicketListFragment.this.getContext());
            }
        });
    }
}
