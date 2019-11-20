package help.desk.mobile.app.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    public static void displayErrorWhileCallingServer(final Context context) {
        Toast.makeText(context, "Erro ao se comunicar com o servidor", Toast.LENGTH_SHORT)
                .show();
    }

    public static void displayMessage(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .show();
    }

}
