package com.example.ameen.chatapp.Base;

public interface BaseInterface {

    interface View {
        void initViews();
    }

    interface Presenter {
        void startFirebase();

        void navigateBetweenViews();
    }
}
