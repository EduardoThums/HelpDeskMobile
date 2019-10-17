package help.desk.mobile.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import help.desk.mobile.app.utils.ButtonUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helpdeskmobile.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.to_login_screen_button) private Button loginButton;
    @BindView(R.id.to_sign_up_screen_button) private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeComponents();
    }

    private void initializeComponents() {
        ButtonUtils.setActivityTransitionOnButton(this.loginButton,this, SignUpActivity.class);
        ButtonUtils.setActivityTransitionOnButton(this.signUpButton,this, SignUpActivity.class);
    }
}
