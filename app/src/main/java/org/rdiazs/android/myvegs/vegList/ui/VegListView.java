package org.rdiazs.android.myvegs.vegList.ui;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Operaciones relativas al cultivo.
 */
public interface VegListView {
    void onVegAdded(Veg veg);

    void onVegChanged(Veg veg);

    void onVegRemoved(Veg veg);

    void onVegError(String error);
}
