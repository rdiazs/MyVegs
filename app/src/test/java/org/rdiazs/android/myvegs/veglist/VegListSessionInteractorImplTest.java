package org.rdiazs.android.myvegs.veglist;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.vegList.mvp.VegListRepository;
import org.rdiazs.android.myvegs.vegList.mvp.VegListSessionInteractor;
import org.rdiazs.android.myvegs.vegList.mvp.VegListSessionInteractorImpl;

import static org.mockito.Mockito.verify;


/**
 * Prueba el <code>Session Interactor</code>.
 */
public class VegListSessionInteractorImplTest extends BaseTest {
    @Mock
    private VegListRepository repository;

    private VegListSessionInteractor interactor;

    @Override
    public void setup() throws Exception {
        super.setup();
        interactor = new VegListSessionInteractorImpl(repository);
    }

    @Test
    public void testGetAuthMail_mailFromRepository() throws Exception {
        interactor.getAuthEmail();

        verify(repository).getAuthMail();
    }

    @Test
    public void testLogout_logoutFromRepository() throws Exception {
        interactor.logout();

        verify(repository).logout();
    }
}
