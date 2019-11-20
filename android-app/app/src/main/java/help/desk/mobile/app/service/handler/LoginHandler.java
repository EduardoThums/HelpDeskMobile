package help.desk.mobile.app.service.handler;

import android.content.Context;
import android.content.SharedPreferences;

import help.desk.mobile.app.activity.HomeActivity;
import help.desk.mobile.app.model.APIError;
import help.desk.mobile.app.model.request.LoginRequest;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.LoginService;
import help.desk.mobile.app.utils.ErrorUtils;
import help.desk.mobile.app.utils.ToastUtils;
import help.desk.mobile.app.utils.TransitionUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class LoginHandler {

    private LoginService loginService;

    private Context context;

    public LoginHandler(Context context) {
        this.loginService = new BaseService().getLoginService();
        this.context = context;
    }

    public void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<String> loginCall = loginService.login(loginRequest);

        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(context);
            }
        });
    }

    private void handleResponse(Response<String> response) {
        if (response.isSuccessful()) {
            setToken(response.body());
            TransitionUtils.changeActivity(context, HomeActivity.class);
            return;
        }
        APIError error = ErrorUtils.parseError(response);
        ToastUtils.displayMessage(context, "Usuário ou Senha inválidos");
    }

    private void setToken(String token) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", token);
        editor.apply();
    }

}
