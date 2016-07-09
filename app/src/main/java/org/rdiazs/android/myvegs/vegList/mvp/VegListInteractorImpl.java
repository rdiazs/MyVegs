package org.rdiazs.android.myvegs.vegList.mvp;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Implementación del <code>Interactor</code> para la lista de cultivos.
 */
public class VegListInteractorImpl implements VegListInteractor {
    private VegListRepository repository;

    public VegListInteractorImpl(VegListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void removeVeg(Veg veg) {
        repository.removeVeg(veg);
    }


}
