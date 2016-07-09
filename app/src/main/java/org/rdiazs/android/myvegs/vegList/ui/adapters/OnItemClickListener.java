package org.rdiazs.android.myvegs.vegList.ui.adapters;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Interfaz para definir los tipos de pulsaciones.
 */
public interface OnItemClickListener {
    /**
     * Pulsación corta, que accede a la información del elemento.
     * @param veg
     */
    void onItemClick(Veg veg);

    /**
     * Pulsación larga, que elimina el elemento.
     * @param veg
     */
    void onItemLongClick(Veg veg);
}
