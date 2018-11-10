package com.leyifu.phoneplayer.act;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.eftimoff.androipathview.PathView;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.constant.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.pathView)
    PathView pathView;
    private boolean isFirstApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GUIDE_SHARE, MODE_PRIVATE);
        isFirstApp = sharedPreferences.getBoolean(Constants.IS_FIRST_APP, false);

        init();


    }


    protected void init() {

        pathView.getPathAnimator()
                .delay(500)
                .duration(3000)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!isFirstApp) {
                            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }

                        finish();
                    }
                })
                .start();
    }

}
