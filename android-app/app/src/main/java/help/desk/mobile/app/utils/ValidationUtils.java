package help.desk.mobile.app.utils;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class ValidationUtils {

    public static void displayValidationErrors(List<ValidationError> errors, Context context) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(context);

            boolean fieldIsEditText = view instanceof EditText;
            if (fieldIsEditText) {
                ((EditText) view).setError(message);
            } else {
                ToastUtils.displayMessage(context, message);
            }
        }
    }
}
