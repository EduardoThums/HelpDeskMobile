package help.desk.mobile.app.activity;

import android.os.Bundle;

import com.example.helpdeskmobile.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.fragment.CreateTicketFragment;
import help.desk.mobile.app.fragment.EditAccountFragment;
import help.desk.mobile.app.fragment.TicketListFragment;

public class HomeActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @OnClick(R.id.add_ticket_button)
    protected void toCreateTicket() {
        renderFragment(new CreateTicketFragment());
    }

    @OnClick(R.id.home_button)
    protected void toHome() {
        renderFragment(new TicketListFragment());
    }


    @OnClick(R.id.edit_account_button)
    protected void toEditAccount() {
        renderFragment(new EditAccountFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toHome();
        ButterKnife.bind(this);
    }

    private void renderFragment(Fragment fragmentToRender) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.main_screen_layout, fragmentToRender);
        fragmentTransaction.commit();
    }
}
