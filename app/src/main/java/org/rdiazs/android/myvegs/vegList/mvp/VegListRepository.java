package org.rdiazs.android.myvegs.vegList.mvp;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * <code>Repository</code> para la lista de cultivos.
 */
public interface VegListRepository {
    void subscribe();

    void unsubscribe();

    String getAuthMail();

    void logout();

    void removeVeg(Veg veg);

//    /**
//     * Cierra la sesión de un usuario.
//     */
//    void signOff();
//
//    /**
//     * Recupera el email del usuario logeado actualmente.
//     */
//    String getCurrentUserEmail();
//
//    /**
//     * Elimina un cultivo
//     *
//     * @param veg Cultivo a eliminar.
//     */
//    void removeVeg(Veg veg);
//
//    /**
//     * Destruye el <code>Listener</code> para evitar el <em>Memory Leack</em>.
//     */
//    void destroyListener();
//
//    /**
//     * Se suscribe a los eventos de la lista de cultivos.
//     */
//    void subscribeToVegListEvents();
//
//    /**
//     * Se desuscribe de los eventos de la lista de cultivos.
//     */
//    void unsubscribeFromVegListEvents();
}
