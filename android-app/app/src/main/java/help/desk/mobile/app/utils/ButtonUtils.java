package help.desk.mobile.app.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public abstract class ButtonUtils {

    public static void setActivityTransitionOnButton(final Button button, final Context context, final Class<?> activityClass) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivityIntent = new Intent(context, activityClass);
                context.startActivity(loginActivityIntent);
            }
        });
    }

}
