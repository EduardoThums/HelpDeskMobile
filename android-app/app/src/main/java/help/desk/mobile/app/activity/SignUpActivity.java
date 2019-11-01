package help.desk.mobile.app.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpdeskmobile.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.mock.AreasMock;
import help.desk.mobile.app.model.Area;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.signup_name_edit_text)
    protected EditText nameEditText;

    @BindView(R.id.signup_email_edit_text)
    protected EditText emailEditText;

    @BindView(R.id.signup_password_edit_text)
    protected EditText passwordEditText;

    @BindView(R.id.signup_cpf_edit_text)
    protected EditText cpfEditText;

    @BindView(R.id.signup_phone_edit_text)
    protected EditText phoneEditText;

    @BindView(R.id.area_spinner)
    protected Spinner areaSpinner;

    @BindView(R.id.sign_up_action_button)
    protected Button button;

    @OnClick(R.id.sign_up_action_button)
    protected void submit() {
        // TODO: call api
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        initializeComponents();
    }

    private void initializeComponents() {
        setAreaSpinner();
    }

    private void setAreaSpinner() {
        List<Area> areas = AreasMock.getMockedAreas();
        ArrayAdapter<Area> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        areaSpinner.setAdapter(adapter);
    }
}
