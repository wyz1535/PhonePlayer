package com.leyifu.phoneplayer.act;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding3.InitialValueObservable;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.leyifu.phoneplayer.R;
import com.leyifu.phoneplayer.bean.loginbean.LoginDataBean;
import com.leyifu.phoneplayer.bean.loginbean.LoginRequestBean;
import com.leyifu.phoneplayer.constant.Constants;
import com.leyifu.phoneplayer.interf.HttpApi;
import com.leyifu.phoneplayer.interf.IgetLogin;
import com.leyifu.phoneplayer.presenter.Persenter;
import com.leyifu.phoneplayer.util.ACache;
import com.leyifu.phoneplayer.util.RxBus;
import com.leyifu.phoneplayer.widget.ButtonLoading;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;


public class LoginActivity extends BaseActivity implements IgetLogin {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.toolbar_login)
    Toolbar toolbarLogin;
    @BindView(R.id.et_msisdn)
    EditText etMsisdn;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    ButtonLoading btnLogin;
    @BindView(R.id.til_msisdn)
    TextInputLayout tilMsisdn;
    private String msisdn;
    private String password;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        toolbarLogin.setTitle(getResources().getString(R.string.login));
        toolbarLogin.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarLogin.setNavigationIcon(R.drawable.ic_back_svg);
//        setSupportActionBar(toolbarLogin);

        toolbarLogin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        InitialValueObservable<CharSequence> obMsisdn = RxTextView.textChanges(etMsisdn);
        InitialValueObservable<CharSequence> obPassword = RxTextView.textChanges(etPassword);

        Observable.combineLatest(obMsisdn, obPassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence msisdn, CharSequence password) throws Exception {
                return isPhoneValid(msisdn.toString()) && isPassWordValid(password.toString());
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                btnLogin.setEnabled(aBoolean);
            }
        });

//        btnLogin.noLoad();
    }

    private boolean isPhoneValid(String phone) {
        return phone.startsWith("1") && phone.length() == 11;
    }

    private boolean isPassWordValid(String password) {
        return password.length() >= 6;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
//        tilMsisdn.setError("输入错误");

        msisdn = etMsisdn.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.setEmail(msisdn);
        loginRequestBean.setPassword(password);

        Persenter.pGetLogin(this, HttpApi.class, loginRequestBean);

//        ApiUtils.okHttpPost(this, Constants.BASE_URL + "login", loginRequestBean);

    }


    private void saveData(LoginDataBean loginDataBean) {
        ACache aCache = ACache.get(this);
        aCache.put(Constants.USER, loginDataBean.getData().getUser());
        aCache.put(Constants.TOKEN, loginDataBean.getData().getToken());
    }

    @Override
    public void iGetLoginSuccess(LoginDataBean loginDataBean) {
        btnLogin.showLoading();
        if (loginDataBean.getStatus() == 1) {
            RxBus.post(loginDataBean.getData().getUser());
            saveData(loginDataBean);
            Toast.makeText(this, getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
//            CategoryFragment.newInstance(getResources().getString(R.string.login_refresh)).init();
            finish();

        } else {
            Toast.makeText(this, loginDataBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void iGetLoginFailed(Throwable throwable) {
        Log.e(TAG, "iGetLoginFailed: " + throwable);
//        Toast.makeText(this, "登录账户或者密码错误", Toast.LENGTH_SHORT).show();
        btnLogin.showButtonText();

    }

    @Override
    public void iGetShowLoading() {
        btnLogin.showButtonText();
    }

    @Override
    public void iGetCompleted() {
        btnLogin.showButtonText();
    }

}
