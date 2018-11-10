package com.leyifu.phoneplayer.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xingxing on 2018/11/10.
 */

public class PointSelector {

    private static View view;

    public static void getPointSelector(Context context, int number, LinearLayout llGuidePoint,int drawableResource){

        for (int i = 0; i < number; i++) {

            view = new View(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.rightMargin = 10;
            view.setLayoutParams(params);
            view.setBackgroundResource(drawableResource);

            llGuidePoint.addView(view);
        }
    }
}
