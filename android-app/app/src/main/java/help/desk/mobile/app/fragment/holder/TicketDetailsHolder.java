package help.desk.mobile.app.fragment.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.helpdeskmobile.R;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.activity.TicketDetailsActivity;
import help.desk.mobile.app.model.TicketDetails;

public class TicketDetailsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.id_text_view)
    protected TextView idTextView;

    @BindView(R.id.title_text_view)
    protected TextView titleTextView;

    @BindView(R.id.area_text_view)
    protected TextView areaTextView;

    private TicketDetails ticket;

    private View itemView;

    @OnClick
    void onClick(View view) {
        final Context context = itemView.getContext();
        Intent intent = new Intent(context, TicketDetailsActivity.class);

        intent.putExtras(buildBundle());
        context.startActivity(intent);
    }

    private Bundle buildBundle() {
        final Bundle bundle = new Bundle();
        bundle.putSerializable("ticket", ticket);
        return bundle;
    }

    public TicketDetailsHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        ButterKnife.bind(this, itemView);
    }

    public void setDetails(TicketDetails ticket) {
        this.ticket = ticket;
        idTextView.setText(String.format("#%s", ticket.getId()));
        titleTextView.setText(ticket.getTitle());
        areaTextView.setText(ticket.getArea().getName());
    }
}