package com.leyifu.phoneplayer.act;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.util.ActivityCollections;
import com.leyifu.phoneplayer.util.DeviceLogUtils;
import com.leyifu.phoneplayer.util.ShowUtil;

import java.util.ArrayList;
import java.util.List;

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


        initPermission();

        //显示用户手机设备
        DeviceLogUtils.initDevice(this);

        ActivityCollections.addActivity(this);

        unbinder = ButterKnife.bind(this);

        init();

        handleMaterialStatusBar();

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




    String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE};

    //未授权的权限
    List<String> mPermissionsList = new ArrayList<>();

    private void initPermission() {

        mPermissionsList.clear();

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionsList.add(permission);
            }
        }

        if (mPermissionsList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions, Constants.PERMISSION_CODE);
        } else {
            // TODO: 2018/11/13 0013 权限通过
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.PERMISSION_CODE) {

            for (int i = 0; i < grantResults.length; i++) {

                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ShowUtil.toast(this, "没有授权，请授权后再只用该功能");
                }
            }

        }
    }
}
