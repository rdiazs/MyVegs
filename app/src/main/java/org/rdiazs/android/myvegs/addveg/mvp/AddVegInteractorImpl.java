package org.rdiazs.android.myvegs.addveg.mvp;

import android.content.Context;

import org.rdiazs.android.myvegs.entities.Veg;

/**
 * Implementación del <code>Interactor</code> para añadir un cultivo.
 */
public class AddVegInteractorImpl implements AddVegInteractor {
    private Context context;
    private AddVegRepository repository;

    public AddVegInteractorImpl(Context context, AddVegRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public void execute(Veg veg, boolean save) {
        if (save) {
            repository.addVeg(veg);
        } else {
            repository.removeVeg(veg);
        }
    }
}
