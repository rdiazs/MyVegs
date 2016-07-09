package org.rdiazs.android.myvegs.addveg.ui;

public interface AddVegView {
    /**
     * Habilita/Deshabilita los campos.
     *
     * @param enable Si es <em>Verdadero</em>, habilita los campos; si es <em>falso</em>, los
     *               deshabilita.
     */
    void enableFields(boolean enable);

    /**
     * Restablece los campos.
     */
    void resetFields();

    /**
     * Muestra la barra de progreso.
     */
    void showProgress();

    /**
     * Oculta la barra de progreso.
     */
    void hideProgress();

    /**
     * Cultivo añadido.
     */
    void vegAdded();

    /**
     * El cultivo se está añadiendo.
     */
    void vegAdding();

    /**
     * No se agregó el cultivo.
     */
    void vegNotAdded();

    /**
     * Oculta el teclado.
     */
    void hideKeyboard();
}
