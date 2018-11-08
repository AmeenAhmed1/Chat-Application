package com.example.ameen.chatapp.Login;

import android.content.Intent;

import com.example.ameen.chatapp.Base.BaseInterface;

public interface LoginContract {

    interface View extends BaseInterface.View {

        void setProgressIndicator(boolean active);

        void showGoogleAccounts(Intent intent, final int requestCode);
    }


    interface Presenter extends BaseInterface.Presenter {

        void userGoogleSignIn();

        void userGoogleLogOut();

        void isUserSignedIn();

        //void startFirebase();

        void userEmailLogin(String username, String password);

    }
}
