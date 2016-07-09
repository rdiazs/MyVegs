package org.rdiazs.android.myvegs;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyVegsAppModule {
    MyVegsApp app;

    public MyVegsAppModule(MyVegsApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context providesAppContext() {
        return this.app.getApplicationContext();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return this.app;
    }
}
