package org.rdiazs.android.myvegs.login;

import org.greenrobot.eventbus.Subscribe;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.login.events.LoginEvent;
import org.rdiazs.android.myvegs.login.ui.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private EventBus eventBus;
    private LoginView view;
    private LoginInteractor interactor;

    public LoginPresenterImpl(EventBus eventBus, LoginView view, LoginInteractor
            interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void validateLogin(String email, String password) {
        if (view != null) {
            view.disableInputs();
            view.showProgress();
        }

        interactor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (view != null) {
            view.disableInputs();
            view.showProgress();
        }
        interactor.doSignUp(email, password);
    }

    private void onSignInSuccess(String email) {
        if (view != null) {
            view.setUserEmail(email);
            view.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess() {
        if (view != null) {
            view.newUserSuccess();
        }
    }

    private void onSignInError(String error) {
        if (view != null) {
            view.hideProgress();
            view.enableInputs();
            view.loginError(error);
        }
    }

    private void onSignUpError(String error) {
        if (view != null) {
            view.hideProgress();
            view.enableInputs();
            view.newUserError(error);
        }
    }

    @Subscribe
    @Override
    public void onEventMainThread(LoginEvent event) {
        switch (event.getEventType()) {
            case LoginEvent.onSignInSuccess:
                onSignInSuccess(event.getCurrentUserEmail());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    @Override
    public LoginView getView() {
        return this.view;
    }

    private void onFailedToRecoverSession() {
        if (view != null) {
            view.hideProgress();
            view.enableInputs();
        }
    }
}
