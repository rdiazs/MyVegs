package org.rdiazs.android.myvegs.domain.di;

import org.rdiazs.android.myvegs.MyVegsAppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DomainModule.class, MyVegsAppModule.class})
public interface DomainComponent {
}
