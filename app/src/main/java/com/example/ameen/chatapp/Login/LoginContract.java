package com.example.ameen.chatapp.Login;

import android.content.Intent;

public interface LoginContract {

    interface View {

        void setProgressIndicator(boolean active);

        void initViews();

        void updateText(String text);

        void showGoogleAccounts(Intent intent, final int requestCode);
    }


    interface Presenter {

        void userGoogleSignIn();

        void userGoogleLogOut();

        void isUserSignedIn();

        void startFirebase();

    }
}
