package org.rdiazs.android.myvegs.addveg;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.addveg.events.AddVegEvent;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegInteractor;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegPresenterImpl;
import org.rdiazs.android.myvegs.addveg.ui.AddVegView;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Prueba la implementación del <code>Presenter</code> de Añadir cultivo.
 */
public class AddVegPresenterImplTest extends BaseTest {
    @Mock
    private Context context;

    @Mock
    private EventBus eventBus;

    @Mock
    private AddVegView view;

    @Mock
    private AddVegInteractor interactor;

    @Mock
    private Veg veg = new Veg();

    @Mock
    AddVegEvent event;

    private AddVegPresenterImpl presenter;

    @Override
    public void setup() throws Exception {
        super.setup();
        presenter = new AddVegPresenterImpl(context, eventBus, view, interactor);
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
    public void testAddVeg_executeInteractor() throws Exception {
        presenter.addVeg(veg);

        assertNotNull(presenter.getView());
        verify(view).resetFields();
        verify(interactor).execute(veg, true);
    }

    @Test
    public void testRemoveVeg_executeInteractor() throws Exception {
        presenter.removeVeg(veg);

        assertNotNull(presenter.getView());
        verify(view).resetFields();
        verify(interactor).execute(veg, false);
    }

    @Test
    public void testOnEvent_hasError() throws Exception {
        String error = "error";

        when(event.getError()).thenReturn(error);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).vegNotAdded();
    }

    @Test
    public void testOnUploadInitEvent_updateUIAndVegAdding() throws Exception {
        when(event.getType()).thenReturn(AddVegEvent.UPLOAD_INIT);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).showProgress();
        verify(view).enableFields(false);
        verify(view).vegAdding();
    }

    @Test
    public void testOnUploadCompleteEvent_updateUIAndVegAdded() throws Exception {
        when(event.getType()).thenReturn(AddVegEvent.UPLOAD_COMPLETE);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).hideProgress();
        verify(view).resetFields();
        verify(view).enableFields(true);
        verify(view).vegAdded();
    }

    @Test
    public void testOnUploadErrorEvent_enableFields() throws Exception {
        when(event.getType()).thenReturn(AddVegEvent.UPLOAD_ERROR);

        presenter.onEventMainThread(event);

        assertNotNull(presenter.getView());
        verify(view).enableFields(true);
        verify(view).vegNotAdded();
    }
}
