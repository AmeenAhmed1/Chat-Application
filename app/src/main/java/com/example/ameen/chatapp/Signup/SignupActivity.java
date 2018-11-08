package com.example.ameen.chatapp.Signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ameen.chatapp.R;

public class SignupActivity extends AppCompatActivity
        implements SignupContract.View, View.OnClickListener {

    private static final String TAG = "SignupActivity";

    EditText mEmailText, mPassText, mConfirmPassText;
    CheckBox mTermsCheckBox;
    Button mSignupButton;

    SignupPresenter mSignupPresenter;

    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.i(TAG, "onCreate: ");

        mSignupPresenter = new SignupPresenter(this, this);

        initViews();

    }

    @Override
    public void initViews() {

        mEmailText = findViewById(R.id.emailEditText);
        mPassText = findViewById(R.id.passwordEditText);
        mConfirmPassText = findViewById(R.id.confirmPassEditText);

        mTermsCheckBox = findViewById(R.id.termsCheckBox);

        mSignupButton = findViewById(R.id.signUpButton);
        mSignupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick: ");

        switch (v.getId()) {
            case R.id.signUpButton:
                if (checkData())
                    mSignupPresenter.userSignUpWithEmail(email, pass);

                Log.i(TAG, "onClick: Email --> " + email + "Pass --> " + pass);
                break;

            default:
                break;
        }

    }


    private boolean checkData() {

        email = mEmailText.getText().toString();
        pass = mPassText.getText().toString();
        String confirmPass = mConfirmPassText.getText().toString();

        if (pass.equals(confirmPass))
            return true;
        else return false;
    }
}
