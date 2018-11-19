package com.leyifu.phoneplayer.act;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding3.InitialValueObservable;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.leyifu.phoneplayer.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

//import com.jakewharton.rxbinding3.widget.RxTextView;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.toolbar_login)
    Toolbar toolbarLogin;
    @BindView(R.id.et_msisdn)
    EditText etMsisdn;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
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
        toolbarLogin.setTitle("登陆");
        toolbarLogin.setNavigationIcon(R.drawable.icon_app);
//        setSupportActionBar(toolbarLogin);

//        msisdn = etMsisdn.getText().toString();
//        password = etPassword.getText().toString();


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

    }

}
