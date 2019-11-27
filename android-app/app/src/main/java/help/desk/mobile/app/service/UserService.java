package help.desk.mobile.app.service;

import help.desk.mobile.app.model.UserDetails;
import help.desk.mobile.app.model.request.EditUserRequest;
import help.desk.mobile.app.model.request.UserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @POST("/public/user")
    Call<Long> createUser(@Body UserRequest userRequest);

    @GET("/user")
    Call<UserDetails> getLoggedUser();

    @PUT("/user")
    Call<Void> editUser(@Body EditUserRequest editUserRequest);
}
