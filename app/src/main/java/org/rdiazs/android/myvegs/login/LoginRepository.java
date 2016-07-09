package org.rdiazs.android.myvegs.login;

public interface LoginRepository {
    void signUp(String email, String password);

    void signIn(String email, String password);
}
