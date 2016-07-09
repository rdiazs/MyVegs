package org.rdiazs.android.myvegs.addveg.mvp;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Interfaz para el <code>Interactor</code> de añadir cultivo.
 */
public interface AddVegInteractor {
    void execute(Veg veg, boolean save);
}
