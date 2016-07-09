package org.rdiazs.android.myvegs.login.di;

import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.login.LoginInteractor;
import org.rdiazs.android.myvegs.login.LoginInteractorImpl;
import org.rdiazs.android.myvegs.login.LoginPresenter;
import org.rdiazs.android.myvegs.login.LoginPresenterImpl;
import org.rdiazs.android.myvegs.login.LoginRepository;
import org.rdiazs.android.myvegs.login.LoginRepositoryImpl;
import org.rdiazs.android.myvegs.login.ui.LoginView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView() {
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView view, LoginInteractor
            loginInteractor) {
        return new LoginPresenterImpl(eventBus, view, loginInteractor);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository loginRepository) {
        return new LoginInteractorImpl(loginRepository);
    }

    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseAPI firebaseAPI) {
        return new LoginRepositoryImpl(eventBus, firebaseAPI);
    }
}
