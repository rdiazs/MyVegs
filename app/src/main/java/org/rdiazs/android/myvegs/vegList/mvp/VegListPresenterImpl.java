package org.rdiazs.android.myvegs.vegList.mvp;

import org.greenrobot.eventbus.Subscribe;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.vegList.events.VegListEvent;
import org.rdiazs.android.myvegs.vegList.ui.VegListView;

/**
 * Implementación del <code>Presenter</code> para la lista de cultivos.
 */
public class VegListPresenterImpl implements VegListPresenter {
    private EventBus eventBus;
    private VegListView view;
    private VegListInteractor listInteractor;
    private VegListSessionInteractor sessionInteractor;

    private static final String EMPTY_LIST = "Listado vacío";

    public VegListPresenterImpl(EventBus eventBus, VegListView view, VegListInteractor
            listInteractor, VegListSessionInteractor sessionInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.listInteractor = listInteractor;
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        listInteractor.subscribe();
    }

    @Override
    public void unsubscribe() {
        listInteractor.unsubscribe();
    }

    @Override
    public String getAuthEmail() {
        return sessionInteractor.getAuthEmail();
    }

    @Override
    public void logout() {
        sessionInteractor.logout();
    }

    @Override
    public void removeVeg(Veg veg) {
        listInteractor.removeVeg(veg);
    }

    @Subscribe
    @Override
    public void onEventMainThread(VegListEvent event) {
        String error = event.getError();

        if (error != null) {
            if (error.isEmpty()) {
                view.onVegError(EMPTY_LIST);
            } else {
                view.onVegError(error);
            }
        } else {
            if (event.getType() == VegListEvent.READ_EVENT) {
                view.onVegAdded(event.getVeg());
            } else if (event.getType() == VegListEvent.DELETE_EVENT) {
                view.onVegRemoved(event.getVeg());
            }
        }

//        Veg veg = event.getVeg();
//
//        switch (event.getType()) {
//            case VegListEvent.onVegAdded:
//                onVegAdded(veg);
//                break;
//            case VegListEvent.onVegChanged:
//                onVegChanged(veg);
//                break;
//            case VegListEvent.onVegRemoved:
//                onVegRemoved(veg);
//                break;

    }

    @Override
    public VegListView getView() {
        return this.view;
    }

//    private void onVegAdded(Veg veg) {
//        if (view != null) {
//            view.onVegAdded(veg);
//        }
//    }
//
//    private void onVegChanged(Veg veg) {
//        if (view != null) {
//            view.onVegChanged(veg);
//        }
//    }
//
//    private void onVegRemoved(Veg veg) {
//        if (view != null) {
//            view.onVegRemoved(veg);
//        }
//    }
}
