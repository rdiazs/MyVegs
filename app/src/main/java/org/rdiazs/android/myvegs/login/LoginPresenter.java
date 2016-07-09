package org.rdiazs.android.myvegs.login;

import org.rdiazs.android.myvegs.login.events.LoginEvent;
import org.rdiazs.android.myvegs.login.ui.LoginView;

public interface LoginPresenter {
    void onCreate();

    void onDestroy();

    void validateLogin(String email, String password);

    void registerNewUser(String email, String password);

    void onEventMainThread(LoginEvent event);

    LoginView getView();
}
