package com.leyifu.phoneplayer.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hahaha on 2018/11/7 0007.
 */

public class ShowUtil {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;
    private static final int level = VERBOSE;
    private static Toast toast;

    public static void v(String TAG, String msg) {
        if (level <= VERBOSE) {
            Log.v(TAG, "msg" + msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (level <= DEBUG) {
            Log.d(TAG, "msg" + msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (level <= INFO) {
            Log.i(TAG, "msg" + msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (level <= WARN) {
            Log.w(TAG, "msg" + msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (level <= ERROR) {
            Log.e(TAG, "msg" + msg);
        }
    }

    public static final void toast(Context context, String msg) {

        if (toast != null) {

            toast.setText(msg);

            toast.setDuration(Toast.LENGTH_SHORT);

            toast.show();

        } else {

            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

            toast.show();
        }

    }
}
