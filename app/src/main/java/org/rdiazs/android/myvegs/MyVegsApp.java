package org.rdiazs.android.myvegs;

import android.app.Application;

import com.firebase.client.Firebase;

import org.rdiazs.android.myvegs.addveg.di.AddVegComponent;
import org.rdiazs.android.myvegs.addveg.di.AddVegModule;
import org.rdiazs.android.myvegs.addveg.di.DaggerAddVegComponent;
import org.rdiazs.android.myvegs.addveg.ui.AddVegView;
import org.rdiazs.android.myvegs.domain.di.DomainModule;
import org.rdiazs.android.myvegs.libs.di.LibsModule;
import org.rdiazs.android.myvegs.login.di.DaggerLoginComponent;
import org.rdiazs.android.myvegs.login.di.LoginComponent;
import org.rdiazs.android.myvegs.login.di.LoginModule;
import org.rdiazs.android.myvegs.login.ui.LoginView;
import org.rdiazs.android.myvegs.vegList.di.DaggerVegListComponent;
import org.rdiazs.android.myvegs.vegList.di.VegListComponent;
import org.rdiazs.android.myvegs.vegList.di.VegListModule;
import org.rdiazs.android.myvegs.vegList.ui.VegListActivity;
import org.rdiazs.android.myvegs.vegList.ui.VegListView;
import org.rdiazs.android.myvegs.vegList.ui.adapters.OnItemClickListener;

public class MyVegsApp extends Application {
    private final static String FIREBASE_URL = "https://myvegs.firebaseio.com/";

    private DomainModule domainModule;
    private MyVegsAppModule myVegsAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    private void initModules() {
        myVegsAppModule = new MyVegsAppModule(this);
        domainModule = new DomainModule(FIREBASE_URL);
    }

    public LoginComponent getLoginComponent(LoginView view) {
        return DaggerLoginComponent
                .builder()
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }

    public AddVegComponent getAddVegComponent(AddVegView view) {
        return DaggerAddVegComponent
                .builder()
                .myVegsAppModule(myVegsAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .addVegModule(new AddVegModule(view))
                .build();
    }

    public VegListComponent getVegListComponent(VegListActivity activity, VegListView view,
                                                OnItemClickListener listener) {
        return DaggerVegListComponent
                .builder()
                .myVegsAppModule(myVegsAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(activity))
                .vegListModule(new VegListModule(view, listener))
                .build();
    }
}
