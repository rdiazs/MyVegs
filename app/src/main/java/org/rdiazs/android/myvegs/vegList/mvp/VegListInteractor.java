package org.rdiazs.android.myvegs.vegList.mvp;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * <code>Interactor</code> para la lista de cultivos.
 */
public interface VegListInteractor {
    void subscribe();

    void unsubscribe();

    void removeVeg(Veg veg);
}
