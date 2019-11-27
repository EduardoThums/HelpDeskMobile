package help.desk.mobile.app.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helpdeskmobile.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import help.desk.mobile.app.fragment.holder.TicketDetailsHolder;
import help.desk.mobile.app.model.TicketDetails;

public class TicketDetailsAdapter extends RecyclerView.Adapter<TicketDetailsHolder> {
    private Context context;
    private List<TicketDetails> tickets;

    public TicketDetailsAdapter(Context context, List<TicketDetails> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ticket_item_row, parent, false);
        return new TicketDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketDetailsHolder holder, int position) {
        TicketDetails ticket = tickets.get(position);
        holder.setDetails(ticket);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }
}
