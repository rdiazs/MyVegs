package org.rdiazs.android.myvegs.veglist;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.vegList.mvp.VegListInteractor;
import org.rdiazs.android.myvegs.vegList.mvp.VegListInteractorImpl;
import org.rdiazs.android.myvegs.vegList.mvp.VegListRepository;

import static org.mockito.Mockito.verify;

/**
 * Prueba el <code>List Interactor</code>.
 */
public class VegListInteratorImplTest extends BaseTest {
    @Mock
    private VegListRepository repository;

    @Mock
    private Veg veg;

    private VegListInteractor interactor;

    @Override
    public void setup() throws Exception {
        super.setup();
        interactor = new VegListInteractorImpl(repository);
    }

    @Test
    public void testSubscribe_subscribe() throws Exception {
        interactor.subscribe();

        verify(repository).subscribe();
    }

    @Test
    public void testUnsubscribe_unsubscribe() throws Exception {
        interactor.unsubscribe();

        verify(repository).unsubscribe();
    }

    @Test
    public void testRemoveVeg_removeVeg() throws Exception {
        interactor.removeVeg(veg);

        verify(repository).removeVeg(veg);
    }
}
