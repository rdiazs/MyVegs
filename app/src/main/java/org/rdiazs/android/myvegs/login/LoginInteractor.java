package org.rdiazs.android.myvegs.login;

public interface LoginInteractor {
    void doSignUp(String email, String password);

    void doSignIn(String email, String password);
}
