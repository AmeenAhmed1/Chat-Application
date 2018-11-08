package com.example.ameen.chatapp.Signup;

import com.example.ameen.chatapp.Base.BaseInterface;

public interface SignupContract {

    interface View extends BaseInterface.View {

    }

    interface Presenter extends BaseInterface.Presenter {

        void userSignUpWithEmail(String email, String password);
    }
}
