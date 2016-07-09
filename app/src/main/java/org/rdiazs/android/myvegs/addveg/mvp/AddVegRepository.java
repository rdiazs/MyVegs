package org.rdiazs.android.myvegs.addveg.mvp;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Interfaz del <code>Repository</code> para añadir un cultivo.
 */
public interface AddVegRepository {
    /**
     * Añade un cultivo.
     *
     * @param veg Cultivo a añadir.
     */
    void addVeg(Veg veg);

    /**
     * Elimina un cultivo.
     * @param veg Cultivo a eliminar.
     */
    void removeVeg(Veg veg);
}
