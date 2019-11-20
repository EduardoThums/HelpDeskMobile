package help.desk.mobile.app.service;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class BaseService {

    private static final String TOKEN_PREFIX = "Bearer";
    private final Retrofit retrofit;

    public BaseService() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://help-desk-mobile-api.herokuapp.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public BaseService(Context context) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://help-desk-mobile-api.herokuapp.com")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(getClient(context))
                .build();
    }

    private OkHttpClient getClient(final Context context) {
        OkHttpClient client = new OkHttpClient();

        final SharedPreferences sharedPreferences = context.getSharedPreferences("user_details", MODE_PRIVATE);
        final boolean userIsLoggedIn = sharedPreferences.contains("token");

        if (!userIsLoggedIn) {
            return client;
        }

        final String token = sharedPreferences.getString("token", "");

        client.interceptors().add(chain -> {
            final Request originalRequest = chain.request();

            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .header("Authorization", String.format("%s %s", TOKEN_PREFIX, token));

            final Request request = requestBuilder.build();

            return chain.proceed(request);
        });

        return client;
    }

    public AreaService getAreaService() {
        return this.retrofit.create(AreaService.class);
    }

    public LoginService getLoginService() {
        return this.retrofit.create(LoginService.class);
    }

    public UserService getUserService() {
        return this.retrofit.create(UserService.class);
    }

    public Retrofit getRetrofitInstance() {
        return this.retrofit;
    }
}
