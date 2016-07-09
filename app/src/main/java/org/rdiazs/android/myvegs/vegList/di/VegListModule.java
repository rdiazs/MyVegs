package org.rdiazs.android.myvegs.vegList.di;

import android.content.Context;

import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.libs.base.ImageLoader;
import org.rdiazs.android.myvegs.vegList.mvp.VegListInteractor;
import org.rdiazs.android.myvegs.vegList.mvp.VegListInteractorImpl;
import org.rdiazs.android.myvegs.vegList.mvp.VegListPresenter;
import org.rdiazs.android.myvegs.vegList.mvp.VegListPresenterImpl;
import org.rdiazs.android.myvegs.vegList.mvp.VegListRepository;
import org.rdiazs.android.myvegs.vegList.mvp.VegListRepositoryImpl;
import org.rdiazs.android.myvegs.vegList.mvp.VegListSessionInteractor;
import org.rdiazs.android.myvegs.vegList.mvp.VegListSessionInteractorImpl;
import org.rdiazs.android.myvegs.vegList.ui.VegListView;
import org.rdiazs.android.myvegs.vegList.ui.adapters.OnItemClickListener;
import org.rdiazs.android.myvegs.vegList.ui.adapters.VegListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class VegListModule {
    private VegListView view;
    private OnItemClickListener listener;

    public VegListModule(VegListView view, OnItemClickListener listener) {
        this.view = view;
        this.listener = listener;
    }

    @Provides
    @Singleton
    VegListView providesVegListView() {
        return this.view;
    }

    @Provides
    @Singleton
    VegListPresenter providesVegListPresenter(EventBus eventBus,
                                              VegListView view,
                                              VegListInteractor listInteractor,
                                              VegListSessionInteractor sessionInteractor) {
        return new VegListPresenterImpl(eventBus, view, listInteractor, sessionInteractor);
    }

    @Provides
    @Singleton
    VegListInteractor providesVegListInteractor(VegListRepository repository) {
        return new VegListInteractorImpl(repository);
    }

    @Provides
    @Singleton
    VegListSessionInteractor providesVegListSessionInteractor(VegListRepository repository) {
        return new VegListSessionInteractorImpl(repository);
    }

    @Provides
    @Singleton
    VegListRepository providesVegListRepository(EventBus eventBus,
                                                FirebaseAPI firebaseAPI) {
        return new VegListRepositoryImpl(eventBus, firebaseAPI);
    }

    @Provides
    @Singleton
    VegListAdapter providesVegListAdapter(Context context,
                                          List<Veg> vegList,
                                          ImageLoader imageLoader,
                                          OnItemClickListener onItemClickListener) {
        return new VegListAdapter(context, vegList, imageLoader, onItemClickListener);
    }

    @Provides
    @Singleton
    List<Veg> providesVegsList() { return new ArrayList<>(); }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.listener;
    }
}
