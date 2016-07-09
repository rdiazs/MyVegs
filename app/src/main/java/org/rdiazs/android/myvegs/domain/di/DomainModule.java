package org.rdiazs.android.myvegs.domain.di;

import android.content.Context;
import android.location.Geocoder;

import com.firebase.client.Firebase;

import org.rdiazs.android.myvegs.domain.FirebaseAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    String firebaseURL;

    public DomainModule(String firebaseURL) {
        this.firebaseURL = firebaseURL;
    }

    @Provides
    @Singleton
    FirebaseAPI providesFirebaseAPI(Firebase firebase) {
        return new FirebaseAPI(firebase);
    }

    @Provides
    @Singleton
    Firebase providesFirebase(String firebaseURL) {
        return new Firebase(firebaseURL);
    }

    @Provides
    @Singleton
    String providesFirebaseURL() {
        return this.firebaseURL;
    }
}
