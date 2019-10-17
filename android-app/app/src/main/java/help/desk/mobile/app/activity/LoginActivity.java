package help.desk.mobile.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.helpdeskmobile.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_button) private Button loginButton;
    @BindView(R.id.login_email_edit_text) private EditText emailEditText;
    @BindView(R.id.login_password_edit_text) private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
