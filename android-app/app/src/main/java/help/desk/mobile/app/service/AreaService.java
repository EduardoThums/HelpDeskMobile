package help.desk.mobile.app.service;

import java.util.List;

import help.desk.mobile.app.model.Area;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AreaService {

    @GET("/public/area")
    Call<List<Area>> getAllAreas();
}
