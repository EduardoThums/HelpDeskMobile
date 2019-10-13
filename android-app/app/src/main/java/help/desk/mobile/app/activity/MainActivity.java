package help.desk.mobile.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import help.desk.mobile.app.utils.ButtonUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helpdeskmobile.R;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
    }

    private void initializeComponents() {
        this.loginButton = findViewById(R.id.login_button);
        ButtonUtils.setActivityTransitionOnButton(this.loginButton,this, SignUpActivity.class);
        
        this.signUpButton = findViewById(R.id.sign_up_button);
        ButtonUtils.setActivityTransitionOnButton(this.signUpButton,this, SignUpActivity.class);
    }
}
