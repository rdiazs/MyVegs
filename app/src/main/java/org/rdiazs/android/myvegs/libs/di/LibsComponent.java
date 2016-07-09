package org.rdiazs.android.myvegs.libs.di;

import org.rdiazs.android.myvegs.MyVegsAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LibsModule.class, MyVegsAppModule.class})
public interface LibsComponent {
}
