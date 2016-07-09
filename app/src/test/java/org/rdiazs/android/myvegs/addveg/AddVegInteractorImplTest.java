package org.rdiazs.android.myvegs.addveg;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegInteractor;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegInteractorImpl;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegRepository;
import org.rdiazs.android.myvegs.entities.Veg;

import static org.mockito.Mockito.verify;

/**
 * Prueba la implementación del <code>Interactor</code>.
 */
public class AddVegInteractorImplTest extends BaseTest {
    @Mock
    private Context context;

    @Mock
    private AddVegRepository repository;

    @Mock
    private Veg veg;

    private AddVegInteractor interactor;

    @Override
    public void setup() throws Exception {
        super.setup();
        interactor = new AddVegInteractorImpl(context, repository);
    }

    @Test
    public void testExecute_addVeg() throws Exception {
        interactor.execute(veg, true);
        verify(repository).addVeg(veg);
    }

    @Test
    public void testExecute_removeVeg() throws Exception {
        interactor.execute(veg, false);
        verify(repository).removeVeg(veg);
    }
}
