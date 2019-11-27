package help.desk.mobile.app.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.helpdeskmobile.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.service.handler.LoginHandler;
import help.desk.mobile.app.utils.ValidationUtils;

import static help.desk.mobile.app.constants.ValidationMessages.INVALID_EMAIL;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_EMAIL;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_PASSWORD;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Validator validator;

    private LoginHandler loginHandler;

    @BindView(R.id.login_email_edit_text)
    @NotEmpty(message = REQUIRED_EMAIL)
    @Email(message = INVALID_EMAIL)
    protected EditText emailEditText;

    @BindView(R.id.login_password_edit_text)
    @NotEmpty(message = REQUIRED_PASSWORD)
    protected EditText passwordEditText;

    @BindView(R.id.login_button)
    protected Button loginButton;

    @OnClick(R.id.login_button)
    public void submit() {
        validator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializeValidator();
        loginHandler = new LoginHandler(this);
    }

    private void initializeValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded() {
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        loginHandler.login(email, password);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidationUtils.displayValidationErrors(errors, this);
    }
}
