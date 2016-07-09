package org.rdiazs.android.myvegs.addveg.mvp;

import android.content.Context;

import org.greenrobot.eventbus.Subscribe;
import org.rdiazs.android.myvegs.addveg.events.AddVegEvent;
import org.rdiazs.android.myvegs.addveg.ui.AddVegView;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;

/**
 * Implementación del <code>Presenter</code> para añadir un cultivo.
 */
public class AddVegPresenterImpl implements AddVegPresenter {
    private Context context;
    private EventBus eventBus;
    private AddVegView view;
    private AddVegInteractor interactor;

    public AddVegPresenterImpl(Context context, EventBus eventBus, AddVegView view,
                               AddVegInteractor interactor) {
        this.context = context;
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        this.view = null;
    }

    @Override
    public void addVeg(Veg veg) {
        if (view != null) {
            view.resetFields();
        }

        interactor.execute(veg, true);
    }

    @Override
    public void removeVeg(Veg veg) {
        if (view != null) {
            view.resetFields();
        }

        interactor.execute(veg, false);
    }

    @Subscribe
    @Override
    public void onEventMainThread(AddVegEvent event) {
        if (view != null) {
            if (event.getError() != null) {
                view.vegNotAdded();
            } else {
                if (event.getType() == AddVegEvent.UPLOAD_INIT) {
                    view.showProgress();
                    view.enableFields(false);
                    view.vegAdding();
                } else if (event.getType() == AddVegEvent.UPLOAD_COMPLETE) {
                    view.hideProgress();
                    view.resetFields();
                    view.enableFields(true);
                    view.vegAdded();
                } else if (event.getType() == AddVegEvent.UPLOAD_ERROR) {
                    view.enableFields(true);
                    view.vegNotAdded();
                }
            }
        }
    }

    @Override
    public AddVegView getView() {
        return this.view;
    }
}
