package org.rdiazs.android.myvegs.login.di;

import org.rdiazs.android.myvegs.MyVegsAppModule;
import org.rdiazs.android.myvegs.domain.di.DomainModule;
import org.rdiazs.android.myvegs.libs.di.LibsModule;
import org.rdiazs.android.myvegs.login.ui.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class,
        MyVegsAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
