package com.example.ameen.chatapp.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.ameen.chatapp.R;
import com.example.ameen.chatapp.Signup.SignupActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";

    final int RC_SIGN_IN = 123;

    LoginContract.View mViewReference; //Reference to the activity
    Context mContext;

    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mFirebasaeAuth;

    public LoginPresenter(LoginContract.View mViewReference, Context context) {
        this.mViewReference = mViewReference;

        this.mContext = context;

        startFirebase(); //Starting to init the firebase
    }


    @Override
    public void userGoogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mViewReference.showGoogleAccounts(signInIntent, RC_SIGN_IN);
    }

    public void loginWithCurrentAccount(@Nullable Intent data) {

        mViewReference.setProgressIndicator(true);

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Log.w(TAG, "Google sign in failed", e);
            // ...
        }
    }

    @Override
    public void userGoogleLogOut() {

        mViewReference.setProgressIndicator(true);

        mFirebasaeAuth.signOut();
        mGoogleSignInClient.signOut();

        //mViewReference.updateText("Logged out");

        mViewReference.setProgressIndicator(false);
    }

    @Override
    public void isUserSignedIn() {

        FirebaseUser mFirebaseUser = mFirebasaeAuth.getCurrentUser();

        //Check if the user logged in before
//        if (mFirebaseUser != null)
//            mViewReference.updateText(mFirebaseUser.getDisplayName());
//        else mViewReference.updateText("You have to login");
    }

    @Override
    public void startFirebase() {

        mFirebasaeAuth = FirebaseAuth.getInstance(); //Getting instance of FirebaseAuth

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
    }

    @Override
    public void navigateBetweenViews() {
        mContext.startActivity(new Intent(mContext, SignupActivity.class));
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebasaeAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebasaeAuth.getCurrentUser();

                            mViewReference.setProgressIndicator(false);
                            //mViewReference.updateText(user.getDisplayName());
                            Log.i(TAG, "onComplete: User : " + user.getDisplayName());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


    @Override
    public void userEmailLogin(final String email, String password) {

        mViewReference.setProgressIndicator(true); //Loading bar

        //Start login with given email and password
        mFirebasaeAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Sign in success with email and password
                            //Log.i(TAG, "onComplete: Email" + email);

                            //Getting the current signed in user
                            FirebaseUser user = mFirebasaeAuth.getCurrentUser();

                            mViewReference.setProgressIndicator(false);
                            Log.i(TAG, "onComplete: User : " + user.getEmail());
                            //mViewReference.updateText(user.getEmail());

                        } else {

                            //Sign in with email and password failed
                            Toast.makeText(mContext, "incorrect email or password", Toast.LENGTH_SHORT).show();
                            mViewReference.setProgressIndicator(false);

                        }
                    }
                });
    }
}
