package midian.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link com.kapp.storm.utils.ScreenUtils#dpToPx(android.content.Context, float)}</li>
 * <li>{@link com.kapp.storm.utils.ScreenUtils#pxToDp(android.content.Context, float)}</li>
 * </ul>
 */
public class ScreenUtils {

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int pxToDpCeilInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }

    public static int GetScreenWidthPx(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int GetScreenHeightPx(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static float GetScreenWidthDp(Activity activity) {
        int widthPx = GetScreenWidthPx(activity);
        return pxToDp(activity, widthPx);
    }
}