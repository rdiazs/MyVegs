package org.rdiazs.android.myvegs.vegList.di;

import org.rdiazs.android.myvegs.MyVegsAppModule;
import org.rdiazs.android.myvegs.domain.di.DomainModule;
import org.rdiazs.android.myvegs.libs.di.LibsModule;
import org.rdiazs.android.myvegs.vegList.ui.VegListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        VegListModule.class,
        DomainModule.class,
        LibsModule.class,
        MyVegsAppModule.class})
public interface VegListComponent {
    void inject(VegListActivity activity);
}
