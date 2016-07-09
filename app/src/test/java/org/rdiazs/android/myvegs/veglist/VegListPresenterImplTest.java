package org.rdiazs.android.myvegs.veglist;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.vegList.events.VegListEvent;
import org.rdiazs.android.myvegs.vegList.mvp.VegListInteractor;
import org.rdiazs.android.myvegs.vegList.mvp.VegListPresenter;
import org.rdiazs.android.myvegs.vegList.mvp.VegListPresenterImpl;
import org.rdiazs.android.myvegs.vegList.mvp.VegListSessionInteractor;
import org.rdiazs.android.myvegs.vegList.ui.VegListView;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Prueba el <code>Presenter</code>.
 */
public class VegListPresenterImplTest extends BaseTest {
    @Mock
    private EventBus eventBus;

    @Mock
    private VegListView view;

    @Mock
    private VegListInteractor listInteractor;

    @Mock
    private VegListSessionInteractor sessionInteractor;

    @Mock
    private Veg veg;

    @Mock
    private VegListEvent event;

    private VegListPresenter presenter;

    @Override
    public void setup() throws Exception {
        super.setup();
        presenter = new VegListPresenterImpl(eventBus, view, listInteractor, sessionInteractor);
    }

    @Test
    public void testOnCreate_subscribedToEventBus() throws Exception {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void testOnDestroy_unsubscribedFromEventBus() throws Exception {
        presenter.onDestroy();
        verify(eventBus).unregister(presenter);

        assertNull(presenter.getView());
    }

    @Test
    public void testGetAuthMail_executeSessionInteractor() throws Exception {
        presenter.getAuthEmail();

        verify(sessionInteractor).getAuthEmail();
    }


    @Test
    public void testOnLogout_executeSessionInteractor() throws Exception {
        presenter.logout();

        verify(sessionInteractor).logout();
    }

    @Test
    public void testOnRemoveVeg_executeListInteractor() throws Exception {
        presenter.removeVeg(veg);

        verify(listInteractor).removeVeg(veg);
    }

    @Test
    public void testOnEvent_hasError() throws Exception {
        String error = "error";

        when(event.getError()).thenReturn(error);

        presenter.onEventMainThread(event);

        assertNotNull(error);
        verify(view).onVegError(error);
    }

    @Test
    public void testOnReadEvent_addVeg() throws Exception {
        when(event.getType()).thenReturn(VegListEvent.READ_EVENT);

        presenter.onEventMainThread(event);

        verify(view).onVegAdded(event.getVeg());
    }

    @Test
    public void testOnDeleteEvent_removeVeg() throws Exception {
        when(event.getType()).thenReturn(VegListEvent.DELETE_EVENT);

        presenter.onEventMainThread(event);

        verify(view).onVegRemoved(event.getVeg());
    }
}
