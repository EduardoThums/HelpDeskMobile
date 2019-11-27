package help.desk.mobile.app.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.helpdeskmobile.R;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.model.APIError;
import help.desk.mobile.app.model.Area;
import help.desk.mobile.app.model.request.EditUserRequest;
import help.desk.mobile.app.service.AreaService;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.UserService;
import help.desk.mobile.app.utils.ErrorUtils;
import help.desk.mobile.app.utils.ToastUtils;
import help.desk.mobile.app.utils.ValidationUtils;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static help.desk.mobile.app.constants.ResultMessages.ACCOUNT_UPDATED_WITH_SUCCESS;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_CONFIRM_PASSWORD;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_PASSWORD;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_PHONE;

@NoArgsConstructor
public class EditAccountFragment extends Fragment implements Validator.ValidationListener {

    private AreaService areaService;

    private Validator validator;

    private UserService userService;

    private List<Area> areas;

    @BindView(R.id.edit_account_password_edit_text)
    @NotEmpty(message = REQUIRED_PASSWORD)
    @Password(message = "Senha inválida")
    protected EditText passwordEditText;

    @BindView(R.id.edit_account_confirm_password_edit_text)
    @NotEmpty(message = REQUIRED_CONFIRM_PASSWORD)
    @ConfirmPassword(message = "Senhas não são iguais")
    protected EditText confirmPasswordEditText;

    @BindView(R.id.edit_account_phone_edit_text)
    @NotEmpty(message = REQUIRED_PHONE)
    protected EditText phoneEditText;

    @BindView(R.id.edit_account_area_spinner)
    protected Spinner areaSpinner;

    @OnClick(R.id.edit_account_button)
    protected void submit() {
        validator.validate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_edit_account, container, false);
        ButterKnife.bind(this, view);
        final BaseService baseService = new BaseService(this.getContext());
        areaService = baseService.getAreaService();
        userService = baseService.getUserService();
        areas = new ArrayList<>();
        initializeValidator();
        loadAreas();
        return view;
    }

    private void initializeComponents() {
        setAreaSpinner();
    }

    private void initializeValidator() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void loadAreas() {
        areaService.getAllAreas().enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                areas = response.body();
                initializeComponents();
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(EditAccountFragment.this.getContext());
            }
        });
    }


    private Area getSelectedArea() {
        return (Area) areaSpinner.getSelectedItem();
    }

    private void setAreaSpinner() {
        ArrayAdapter<Area> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, areas);
        areaSpinner.setAdapter(adapter);
    }

    @Override
    public void onValidationSucceeded() {
        EditUserRequest editUserRequest = buildEditUserRequest();

        Call<Void> editUserCall = userService.editUser(editUserRequest);
        editUserCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(EditAccountFragment.this.getContext());
            }
        });
    }

    private EditUserRequest buildEditUserRequest() {
        return EditUserRequest.builder()
                .areaId(getSelectedArea().getId())
                .password(passwordEditText.getText().toString())
                .confirmPassword(confirmPasswordEditText.getText().toString())
                .phone(phoneEditText.getText().toString())
                .build();
    }

    private void handleResponse(final Response<Void> response) {
        if (response.isSuccessful()) {
            ToastUtils.displayMessage(EditAccountFragment.this.getContext(), ACCOUNT_UPDATED_WITH_SUCCESS);
            clearFields();
            return;
        }
        final APIError error = ErrorUtils.parseError(response);
        ToastUtils.displayMessage(EditAccountFragment.this.getContext(), error.getMessage());
    }

    private void clearFields() {
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
        phoneEditText.setText("");
        areaSpinner.setSelection(0);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidationUtils.displayValidationErrors(errors, this.getContext());
    }
}
