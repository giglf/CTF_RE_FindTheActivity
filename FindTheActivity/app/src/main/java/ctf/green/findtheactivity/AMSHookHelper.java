package ctf.green.findtheactivity;

import android.os.Handler;

import java.lang.reflect.Field;

/**
 * Created by giglf on 18-3-14.
 */

public class AMSHookHelper {



    public static void hookActivityThreadHandler() throws Exception {

        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
        currentActivityThreadField.setAccessible(true);
        Object currentActivityThread = currentActivityThreadField.get(null);

        Field mHField = activityThreadClass.getDeclaredField("mH");
        mHField.setAccessible(true);
        Handler mH = (Handler)mHField.get(currentActivityThread);

        Field mCallBackField = Handler.class.getDeclaredField("mCallback");
        mCallBackField.setAccessible(true);

        mCallBackField.set(mH, new ActivtyThreadHandlerCallback(mH));

    }

}
