package com.example.ameen.chatapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ameen.chatapp.R;

public class LoginActivity extends AppCompatActivity
        implements LoginContract.View, View.OnClickListener {

    private static final String TAG = "LoginActivity";

    LoginPresenter mLoginPresenter;

    Button mEmailLogin,mGoogleLoginButton;
    EditText mEmailText, mPasswordText;
    ProgressBar mProgressBar;
    TextView mSignup;
    //ProgressDialog progressDialog;

    private int REQUEST_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG, "onCreate: ");

        //init the presenter & attach the view to it
        mLoginPresenter = new LoginPresenter(this, this);

        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: ");

        if (requestCode == REQUEST_CODE)
            mLoginPresenter.loginWithCurrentAccount(data);

    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) mProgressBar.setVisibility(View.VISIBLE);
        else mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void initViews() {

        Log.i(TAG, "initViews: Making a view referneces");

        mProgressBar = findViewById(R.id.loginProgressBar);
        mProgressBar.setVisibility(View.GONE);

        mEmailLogin = findViewById(R.id.emailLoginButton);
        mEmailLogin.setOnClickListener(this);

        mGoogleLoginButton = findViewById(R.id.googleLoginButton);
        mGoogleLoginButton.setOnClickListener(this);

        mEmailText = findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);

        mSignup = findViewById(R.id.signUpText);
        mSignup.setOnClickListener(this);

        //mLoginPresenter.isUserSignedIn(); //Check if the user did not signOut.

    }

//    private void setFocus() {
//        mEmailText.clearFocus();
//        mPasswordText.clearFocus();
//    }

    private int checkIsEmpty(String email, String pass) {

        Log.i(TAG, "checkIsEmpty: Email & Password");

        if (email.trim().isEmpty() && pass.trim().isEmpty())
            return 3;
        else {
            if (email.trim().isEmpty())
                return 1;
            if (pass.trim().isEmpty())
                return 2;
        }
        return 0;
    }

    @Override
    public void showGoogleAccounts(Intent intent, final int requestCode) {
        REQUEST_CODE = requestCode;
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick: Buttons --> " + v.getId());

        switch (v.getId()) { //Getting the view id for listner

            case R.id.googleLoginButton:
                mLoginPresenter.userGoogleSignIn();
                break;

            case R.id.emailLoginButton:

                String email = mEmailText.getText().toString();
                String pass = mPasswordText.getText().toString();

                switch (checkIsEmpty(email, pass)) {
                    case 0:
                        mLoginPresenter.userEmailLogin(email, pass);
                        //setFocus();
                        break;

                    case 1:
                        Toast.makeText(this, "Email cant be empty", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(this, "Password cant be empty", Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(this, "Email & Password are empty", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;


            case R.id.signUpText:
                mLoginPresenter.navigateBetweenViews();
                break;
//            case R.id.button2:
//                mLoginPresenter.userGoogleLogOut();
//                break;
        }
    }
}
