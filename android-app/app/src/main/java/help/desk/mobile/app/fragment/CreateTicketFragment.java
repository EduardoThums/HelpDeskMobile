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
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import help.desk.mobile.app.model.APIError;
import help.desk.mobile.app.model.Area;
import help.desk.mobile.app.model.request.CreateTicketRequest;
import help.desk.mobile.app.service.AreaService;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.TicketService;
import help.desk.mobile.app.utils.ErrorUtils;
import help.desk.mobile.app.utils.ToastUtils;
import help.desk.mobile.app.utils.ValidationUtils;
import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static help.desk.mobile.app.constants.ResultMessages.TICKET_CREATED_WITH_SUCCESS;
import static help.desk.mobile.app.constants.ValidationMessages.DESCRIPTION_MAX_SIZE;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_DESCRIPTION;
import static help.desk.mobile.app.constants.ValidationMessages.REQUIRED_TITLE;
import static help.desk.mobile.app.constants.ValidationMessages.TITLE_MAX_SIZE;

@NoArgsConstructor
public class CreateTicketFragment extends Fragment implements Validator.ValidationListener {

    private AreaService areaService;

    private Validator validator;

    private TicketService ticketService;

    private List<Area> areas;

    @BindView(R.id.create_ticket_title)
    @NotEmpty(message = REQUIRED_TITLE)
    @Length(max = 50, message = TITLE_MAX_SIZE)
    protected EditText titleEditText;

    @BindView(R.id.create_ticket_description)
    @NotEmpty(message = REQUIRED_DESCRIPTION)
    @Length(max = 512, message = DESCRIPTION_MAX_SIZE)
    protected EditText descriptionEditText;

    @BindView(R.id.create_ticket_area_spinner)
    protected Spinner areaSpinner;

    @OnClick(R.id.create_ticket_button)
    protected void submit() {
        validator.validate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_create_ticket, container, false);
        ButterKnife.bind(this, view);
        final BaseService baseService = new BaseService(this.getContext());
        areaService = baseService.getAreaService();
        ticketService = baseService.getTicketService();
        areas = new ArrayList<>();
        initializeValidator();
        loadAreas();
        return view;
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
                setAreaSpinner();
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(CreateTicketFragment.this.getContext());
            }
        });
    }

    private void setAreaSpinner() {
        ArrayAdapter<Area> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, areas);
        areaSpinner.setAdapter(adapter);
    }


    @Override
    public void onValidationSucceeded() {
        ticketService.creatTicket(buildCreateTicketRequest()).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(CreateTicketFragment.this.getContext());
            }
        });
    }

    private void handleResponse(final Response<Long> response) {
        if (response.isSuccessful()) {
            ToastUtils.displayMessage(CreateTicketFragment.this.getContext(), TICKET_CREATED_WITH_SUCCESS);
            clearFields();
            return;
        }

        final APIError error = ErrorUtils.parseError(response);
        ToastUtils.displayMessage(CreateTicketFragment.this.getContext(), error.getMessage());
    }

    private CreateTicketRequest buildCreateTicketRequest() {
        return CreateTicketRequest.builder()
                .title(titleEditText.getText().toString())
                .description(descriptionEditText.getText().toString())
                .areaId(getSelectedArea().getId())
                .build();
    }

    private void clearFields() {
        titleEditText.setText("");
        descriptionEditText.setText("");
        areaSpinner.setSelection(0);
    }

    private Area getSelectedArea() {
        return (Area) areaSpinner.getSelectedItem();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        ValidationUtils.displayValidationErrors(errors, this.getContext());
    }
}
