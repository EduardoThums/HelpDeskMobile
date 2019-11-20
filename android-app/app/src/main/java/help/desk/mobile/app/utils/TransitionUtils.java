package help.desk.mobile.app.utils;

import android.content.Context;
import android.content.Intent;

public class TransitionUtils {

    public static void changeActivity(final Context context, final Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }
}
