package com.car_market_android;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.car_market_android.application.CarMarketActivity;
import com.car_market_android.model.ApiKey;
import com.car_market_android.presenters.impl.UserCreatePresenter;
import com.car_market_android.util.MessageUtils;
import com.car_market_android.util.StringUtils;
import com.car_market_android.views.IUserCreateView;

import org.apache.commons.validator.routines.EmailValidator;

public class UserCreate extends CarMarketActivity implements OnClickListener, IUserCreateView {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();
    private EditText mNickname;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirmation;
    private TextView mTerms;
    private Button mSignUp;
    private Button mCacnel;
    private UserCreatePresenter mUserCreatePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_create);

        mUserCreatePresenter = new UserCreatePresenter(this);

        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        this.mNickname = (EditText) this.findViewById(R.id.nickname_user_create);
        this.mEmail = (EditText) this.findViewById(R.id.email_user_create);
        this.mPassword = (EditText) this.findViewById(R.id.password_user_create);
        this.mPasswordConfirmation = (EditText) this.findViewById(R.id.password_confirmation_user_create);
        this.mTerms = (TextView) this.findViewById(R.id.terms_and_policy_user_create);
        this.mSignUp = (Button) this.findViewById(R.id.sign_up_user_create);
        this.mCacnel = (Button) this.findViewById(R.id.cancel_user_create);

        this.mTerms.setOnClickListener(this);
        this.mSignUp.setOnClickListener(this);
        this.mCacnel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_user_create:

                final String nickname = StringUtils.getEditTextString(mNickname);
                final String email = StringUtils.getEditTextString(mEmail);
                final String password = StringUtils.getEditTextString(mPassword);
                final String passwordConfirmation = StringUtils.getEditTextString(mPasswordConfirmation);

                if (TextUtils.isEmpty(nickname)) {
                    MessageUtils.showToastLong(getContext(), "nickname cannot be empty...");
                } else if (TextUtils.isEmpty(email) || !EMAIL_VALIDATOR.isValid(email)) {
                    MessageUtils.showToastLong(getContext(), R.string.email_not_valid);
                } else if (TextUtils.isEmpty(password) || password.length() < 6) {
                    MessageUtils.showToastLong(getContext(), R.string.password_not_valid);
                } else if (TextUtils.isEmpty(passwordConfirmation) || !password.equals(passwordConfirmation)) {
                    MessageUtils.showToastLong(getContext(), "password and password confirmation do not match...");
                } else {
                    showProgressDialog(R.string.signing_up);
                    mUserCreatePresenter.signUp(nickname, "n/a", "n/a", email, password, "active");
                }
                break;
            case R.id.terms_and_policy_user_create:
                break;
            case R.id.cancel_user_create:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSignUpSucceed(ApiKey apiKey) {
        dismissProgressDialog();
        getSession().saveApiKey(apiKey);
        finish();
    }

    @Override
    public void onSignUpFailed(String errorMessage) {
        dismissProgressDialog();
        MessageUtils.showToastLong(getContext(), errorMessage);
    }
}
