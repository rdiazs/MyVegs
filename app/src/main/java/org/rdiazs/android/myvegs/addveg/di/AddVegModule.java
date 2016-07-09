package org.rdiazs.android.myvegs.addveg.di;

import android.content.Context;

import org.rdiazs.android.myvegs.addveg.mvp.AddVegInteractor;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegInteractorImpl;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegPresenter;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegPresenterImpl;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegRepository;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegRepositoryImpl;
import org.rdiazs.android.myvegs.addveg.ui.AddVegView;
import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.libs.base.ImageStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AddVegModule {
    private AddVegView view;

    public AddVegModule(AddVegView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    AddVegView providesAddVegView() {
        return this.view;
    }

    @Provides
    @Singleton
    AddVegPresenter providesAddVegPresenter(Context context,
                                            EventBus eventBus,
                                            AddVegView view,
                                            AddVegInteractor interactor) {
        return new AddVegPresenterImpl(context, eventBus, view, interactor);
    }

    @Provides
    @Singleton
    AddVegInteractor providesAddVegInteractor(Context context, AddVegRepository repository) {
        return new AddVegInteractorImpl(context, repository);
    }

    @Provides
    @Singleton
    AddVegRepository providesAddVegRepository(Context context,
                                              EventBus eventBus,
                                              FirebaseAPI firebaseAPI,
                                              ImageStorage imageStorage) {
        return new AddVegRepositoryImpl(context, eventBus, firebaseAPI, imageStorage);
    }
}
