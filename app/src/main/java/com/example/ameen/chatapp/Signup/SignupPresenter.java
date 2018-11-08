package com.example.ameen.chatapp.Signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.ameen.chatapp.Login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupPresenter implements SignupContract.Presenter {

    private static final String TAG = "SignupPresenter";

    FirebaseAuth mFirebaseAuth;

    SignupContract.View mViewReference; //Reference to the activity
    Context mContext;

    public SignupPresenter(SignupContract.View mViewReference, Context mContext) {
        this.mViewReference = mViewReference;
        this.mContext = mContext;

        startFirebase();
    }

    @Override
    public void userSignUpWithEmail(String email, String password) {

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();

                            navigateBetweenViews();

                        } else {
                            Log.e(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void startFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void navigateBetweenViews() {
        //Navigate to the sign in screen
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
    }
}
