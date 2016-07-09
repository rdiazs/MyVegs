package org.rdiazs.android.myvegs.veglist;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.domain.FirebaseActionListenerCallback;
import org.rdiazs.android.myvegs.domain.FirebaseEventListenerCallback;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.vegList.mvp.VegListRepository;
import org.rdiazs.android.myvegs.vegList.mvp.VegListRepositoryImpl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Prueba el <code>Repository</code>.
 */
public class VegListRepositoryImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;

    @Mock
    private FirebaseAPI firebaseAPI;

    @Mock
    FirebaseEventListenerCallback eventListenerCallback;

    private VegListRepository repository;

    @Override
    public void setup() throws Exception {
        super.setup();
        repository = new VegListRepositoryImpl(eventBus, firebaseAPI);
    }

    @Test
    public void testSubscribe_subscribe() throws Exception {

    }

    @Test
    public void testUnsubscribe_unsubscribe() throws Exception {
        repository.unsubscribe();

        verify(firebaseAPI).unsubscribe();
    }

    @Test
    public void testGetAuthMail_getAuthMail() throws Exception {
        repository.getAuthMail();

        verify(firebaseAPI).getAuthEmail();
    }

    @Test
    public void testLogout_logout() throws Exception {
        repository.logout();

        verify(firebaseAPI).logout();
    }
}
