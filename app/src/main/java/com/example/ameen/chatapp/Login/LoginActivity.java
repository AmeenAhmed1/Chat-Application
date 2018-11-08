package com.example.ameen.chatapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ameen.chatapp.Login.LoginContract;
import com.example.ameen.chatapp.Login.LoginPresenter;
import com.example.ameen.chatapp.R;

public class LoginActivity extends AppCompatActivity
        implements LoginContract.View, View.OnClickListener {

    private static final String TAG = "LoginActivity";

    LoginPresenter mLoginPresenter;

    Button mLogin, mLogout;
    TextView mText;
    //ProgressBar mProgressBar;
    ProgressDialog progressDialog;

    private int REQUEST_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init the presenter & attach the view to it
        mLoginPresenter = new LoginPresenter(this, this);

        initViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE)
            mLoginPresenter.loginWithCurrentAccount(data);

    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(active) progressDialog.show();
        else progressDialog.dismiss();
    }

    @Override
    public void initViews() {

//        mProgressBar = new ProgressBar(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please, wait!!");
        progressDialog.setCanceledOnTouchOutside(false);

        mLogin = findViewById(R.id.button);
        mLogin.setOnClickListener(this);

        mLogout = findViewById(R.id.button2);
        mLogout.setOnClickListener(this);

        mText = findViewById(R.id.textView);

        mLoginPresenter.isUserSignedIn(); //Check if the user did not signOut.
    }

    @Override
    public void updateText(String text) {
        mText.setText(text);
    }


    @Override
    public void showGoogleAccounts(Intent intent, final int requestCode) {
        REQUEST_CODE = requestCode;
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){ //Getting the view id for listner
            case R.id.button:
                mLoginPresenter.userGoogleSignIn();
                break;

            case R.id.button2:
                mLoginPresenter.userGoogleLogOut();
                break;
        }
    }
}
