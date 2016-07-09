package org.rdiazs.android.myvegs.addveg.mvp;

import org.rdiazs.android.myvegs.addveg.events.AddVegEvent;
import org.rdiazs.android.myvegs.addveg.ui.AddVegView;
import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Interfaz del <code>Presenter</code> para añadir un cultivo.
 */
public interface AddVegPresenter {
    void onCreate();

    void onDestroy();

    void addVeg(Veg veg);

    void removeVeg(Veg veg);

    void onEventMainThread(AddVegEvent event);

    AddVegView getView();
}
