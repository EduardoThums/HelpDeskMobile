package help.desk.mobile.app.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import help.desk.mobile.app.model.APIError;
import help.desk.mobile.app.service.BaseService;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                new BaseService().getRetrofitInstance()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}