package help.desk.mobile.app.service;

import help.desk.mobile.app.model.request.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("/public/user")
    Call<Long> createUser(@Body UserRequest userRequest);
}
