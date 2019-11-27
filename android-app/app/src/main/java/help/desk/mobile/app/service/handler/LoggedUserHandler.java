package help.desk.mobile.app.service.handler;

import android.content.Context;

import help.desk.mobile.app.model.UserDetails;
import help.desk.mobile.app.service.BaseService;
import help.desk.mobile.app.service.UserService;
import help.desk.mobile.app.utils.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggedUserHandler {

    private UserService userService;

    private Context context;

    private UserDetails loggedUser;

    public LoggedUserHandler(Context context) {
        this.userService = new BaseService(context).getUserService();
        this.context = context;
    }

    public UserDetails getLoggedUser() {
        userService.getLoggedUser().enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    loggedUser = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                ToastUtils.displayErrorWhileCallingServer(context);
            }
        });
        return loggedUser;
    }
}
