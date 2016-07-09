package org.rdiazs.android.myvegs.login;

import com.firebase.client.FirebaseError;

import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.domain.FirebaseActionListenerCallback;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.login.events.LoginEvent;

public class LoginRepositoryImpl implements LoginRepository {
    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebaseAPI.signUp(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSignUpSuccess);
                firebaseAPI.createUser(email);
                signIn(email, password);
            }

            @Override
            public void onError(FirebaseError error) {
                postEvent(LoginEvent.onSignUpError, error.getMessage(), null);
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        if (email != null && password != null) {
            firebaseAPI.login(email, password, new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onSignInError, error.getMessage(), null);
                }
            });
        } else {
            firebaseAPI.checkForSession(new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onFailedToRecoverSession);
                }
            });
        }
    }

    private void postEvent(int type, String errorMessage, String currentUserEmail) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.setErrorMessage(errorMessage);
        loginEvent.setCurrentUserEmail(currentUserEmail);
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null, null);
    }

    private void postEvent(int type, String currentUserEmail) {
        postEvent(type, null, currentUserEmail);
    }
}
