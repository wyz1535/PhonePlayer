package com.leyifu.phoneplayer.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hahaha on 2018/11/7 0007.
 */

public class ActivityCollections {

    static List<Activity> list = new ArrayList<>();

    public static final void addActivity(Activity activity) {

        list.add(activity);
    }

    public static final void removeActivity(Activity activity) {

        list.remove(activity);
    }

    public static final void finishActivity() {

        for (Activity activity : list) {

            if (!activity.isFinishing()) {

                activity.finish();
            }
        }
    }
}
