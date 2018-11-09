package com.leyifu.phoneplayer.act;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.util.ActivityCollections;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hahaha on 2018/11/7 0007.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        handleMaterialStatusBar();

        ActivityCollections.addActivity(this);

        unbinder = ButterKnife.bind(this);

        init();

    }

    public abstract int setLayout();

    protected abstract void init();

    /**
     * 适配沉浸状态栏
     */
    private void handleMaterialStatusBar() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            return;
        }

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.setStatusBarColor(getResources().getColor(R.color.cyan_600));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.e(TAG, "onStart: " + getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityCollections.removeActivity(this);

        if (unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
    }
}
