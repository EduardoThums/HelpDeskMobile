package help.desk.mobile.app.service;

import help.desk.mobile.app.model.request.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/public/login")
    Call<String> login(@Body LoginRequest loginRequest);
}
