package help.desk.mobile.app.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helpdeskmobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import help.desk.mobile.app.utils.ButtonUtils;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.to_login_screen_button)
    Button loginButton;

    @BindView(R.id.to_sign_up_screen_button)
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeComponents();
    }

    private void initializeComponents() {
        ButtonUtils.setActivityTransitionOnButton(this.loginButton, this, LoginActivity.class);
        ButtonUtils.setActivityTransitionOnButton(this.signUpButton, this, SignUpActivity.class);
    }
}
