package org.rdiazs.android.myvegs.vegList.mvp;

import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.vegList.events.VegListEvent;
import org.rdiazs.android.myvegs.vegList.ui.VegListView;

/**
 * <code>Presenter</code> para la lista de cultivos.
 */
public interface VegListPresenter {
    void onCreate();

    void onDestroy();

    void subscribe();

    void unsubscribe();

    String getAuthEmail();

    void logout();

    void removeVeg(Veg veg);

    void onEventMainThread(VegListEvent event);

    VegListView getView();
}
