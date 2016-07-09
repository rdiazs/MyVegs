package org.rdiazs.android.myvegs.addveg.di;

import org.rdiazs.android.myvegs.MyVegsAppModule;
import org.rdiazs.android.myvegs.addveg.ui.AddVegActivity;
import org.rdiazs.android.myvegs.domain.di.DomainModule;
import org.rdiazs.android.myvegs.libs.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AddVegModule.class,
        DomainModule.class,
        LibsModule.class,
        MyVegsAppModule.class})
public interface AddVegComponent {
    void inject(AddVegActivity activity);
}
