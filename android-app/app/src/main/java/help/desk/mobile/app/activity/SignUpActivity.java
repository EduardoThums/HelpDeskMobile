package help.desk.mobile.app.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.helpdeskmobile.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.model.APIError;
import help.desk.mobile.app.model.Area;
import help.desk.mobile.app.model.request.UserRequest;
import help.desk.mobile.app.service.AreaService;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.UserService;
import help.desk.mobile.app.service.handler.LoginHandler;
import help.desk.mobile.app.utils.ErrorUtils;
import help.desk.mobile.app.utils.ToastUtils;
import help.desk.mobile.app.utils.ValidationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static help.desk.mobile.app.constants.ResultMessages.USER_REGISTERED_WITH_SUCCESS;
import static help.desk.mobile.app.constants.ValidationMessages.INVALID_EMAIL;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_CPF;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_EMAIL;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_NAME;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_PASSWORD;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_PHONE;

public class SignUpActivity extends AppCompatActivity implements Validator.ValidationListener {

    private AreaService areaService;

    private List<Area> areas;

    private Validator validator;

    private UserService userService;

    private LoginHandler loginHandler;

    @BindView(R.id.signup_name_edit_text)
    @NotEmpty(message = REQUIRED_NAME)
    protected EditText nameEditText;

    @BindView(R.id.signup_email_edit_text)
    @Email(message = INVALID_EMAIL)
    @NotEmpty(message = REQUIRED_EMAIL)
    protected EditText emailEditText;

    @BindView(R.id.signup_password_edit_text)
    @NotEmpty(message = REQUIRED_PASSWORD)
    protected EditText passwordEditText;

    @BindView(R.id.signup_cpf_edit_text)
    @NotEmpty(message = REQUIRED_CPF)
    protected EditText cpfEditText;

    @BindView(R.id.signup_phone_edit_text)
    @NotEmpty(message = REQUIRED_PHONE)
    protected EditText phoneEditText;

    @BindView(R.id.area_spinner)
    protected Spinner areaSpinner;

    @BindView(R.id.sign_up_action_button)
    protected Button button;

    @OnClick(R.id.sign_up_action_button)
    protected void submit() {
        validator.validate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        areaService = new BaseService().getAreaService();
        userService = new BaseService().getUserService();
        loginHandler = new LoginHandler(this);
        areas = new ArrayList<>();
        ButterKnife.bind(this);
        initializeValidator();
        loadAreas();
    }

    @Override
    public void onValidationSucceeded() {
        UserRequest userRequest = buildUserRequest();
        Call<Long> createUserCall = userService.createUser(userRequest);

        createUserCall.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(SignUpActivity.this);
            }
        });
    }

    private void handleResponse(Response<Long> response) {
        boolean userCreateSuccessfully = response.isSuccessful();
        if (!userCreateSuccessfully) {
            APIError error = ErrorUtils.parseError(response);
            ToastUtils.displayMessage(SignUpActivity.this, error.getMessage());
            return;
        }
        ToastUtils.displayMessage(SignUpActivity.this, USER_REGISTERED_WITH_SUCCESS);
        loginHandler.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidationUtils.displayValidationErrors(errors, this);
    }

    private void initializeComponents() {
        setAreaSpinner();
    }


    private void initializeValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void loadAreas() {
        Call<List<Area>> loadAreasCall = areaService.getAllAreas();
        loadAreasCall.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                areas = response.body();
                initializeComponents();
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(SignUpActivity.this);
            }
        });
    }


    private UserRequest buildUserRequest() {
        return UserRequest.builder()
                .email(emailEditText.getText().toString())
                .password(passwordEditText.getText().toString())
                .name(nameEditText.getText().toString())
                .cpf(cpfEditText.getText().toString())
                .phone(phoneEditText.getText().toString())
                .areaId(getSelectedArea().getId())
                .build();
    }

    private Area getSelectedArea() {
        return (Area) areaSpinner.getSelectedItem();
    }

    private void setAreaSpinner() {
        ArrayAdapter<Area> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areas);
        areaSpinner.setAdapter(adapter);
    }
}
