package org.rdiazs.android.myvegs.addveg.events;

/**
 * Eventos a la hora de añadir un nuevo cultivo.
 */
public class AddVegEvent {
    public static final int VEG_EXISTS = 0;
    public static final int VEG_NOT_EXISTS = 1;
    public static final int UPLOAD_INIT = 2;
    public static final int UPLOAD_COMPLETE = 3;
    public static final int UPLOAD_ERROR = 4;

    private int type;
    private String error;

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
