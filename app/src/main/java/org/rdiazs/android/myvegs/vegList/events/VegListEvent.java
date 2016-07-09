package org.rdiazs.android.myvegs.vegList.events;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Eventos para la lista de cultivos.
 */
public class VegListEvent {
    private Veg veg;
    private int type;
    private String error;

    public final static int onVegAdded = 0;
    public final static int onVegChanged = 1;
    public final static int onVegRemoved = 2;

    public final static int READ_EVENT = 3;
    public final static int DELETE_EVENT = 4;

    public Veg getVeg() {
        return veg;
    }

    public void setVeg(Veg veg) {
        this.veg = veg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
