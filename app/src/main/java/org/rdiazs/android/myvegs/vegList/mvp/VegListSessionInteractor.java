package org.rdiazs.android.myvegs.vegList.mvp;

/**
 * <code>Interactor</code> para el control de la sesión del usuario.
 */
public interface VegListSessionInteractor {
    String getAuthEmail();

    void logout();
}
