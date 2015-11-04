package com.car_market_android;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.car_market_android.application.CarMarketActivity;
import com.car_market_android.model.ApiKey;
import com.car_market_android.presenters.impl.UserAuthPresenter;
import com.car_market_android.util.MessageUtils;
import com.car_market_android.util.StringUtils;
import com.car_market_android.views.IUserAuthView;

import org.apache.commons.validator.routines.EmailValidator;

public class UserAuth extends CarMarketActivity implements OnClickListener, IUserAuthView {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignIn;
    private Button mCacnel;
    private UserAuthPresenter mUserAuthPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_auth);

        mUserAuthPresenter = new UserAuthPresenter(this);

        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        mEmail = (EditText) findViewById(R.id.email_auth);
        mPassword = (EditText) findViewById(R.id.password_auth);
        mSignIn = (Button) findViewById(R.id.sign_in_auth);
        mCacnel = (Button) findViewById(R.id.cancel_auth);

        mSignIn.setOnClickListener(this);
        mCacnel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_auth:

                final String email = StringUtils.getEditTextString(mEmail);
                final String password = StringUtils.getEditTextString(mPassword);

                if (TextUtils.isEmpty(email) || !EMAIL_VALIDATOR.isValid(email)) {
                    MessageUtils.showToastLong(getContext(), R.string.email_not_valid);
                } else if (password.length() < 6) {
                    MessageUtils.showToastLong(getContext(), R.string.password_not_valid);
                } else {
                    showProgressDialog(R.string.signing_in);
                    mUserAuthPresenter.signIn(email, password);
                }
                break;
            case R.id.cancel_auth:
                finish();
            default:
                break;
        }
    }

    @Override
    public void onSignInSucceed(ApiKey apiKey) {
        dismissProgressDialog();
        getSession().saveApiKey(apiKey);
        finish();
    }

    @Override
    public void onSignInFailed(String errorMessage) {
        dismissProgressDialog();
        MessageUtils.showToastLong(getContext(), errorMessage);
    }
}
