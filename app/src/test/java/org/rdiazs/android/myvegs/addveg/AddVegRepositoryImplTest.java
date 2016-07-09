package org.rdiazs.android.myvegs.addveg;

import android.content.Context;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.rdiazs.android.myvegs.BaseTest;
import org.rdiazs.android.myvegs.addveg.events.AddVegEvent;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegRepository;
import org.rdiazs.android.myvegs.addveg.mvp.AddVegRepositoryImpl;
import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.libs.base.ImageStorage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

/**
 * Prueba el <code>Repository</code>.
 */
public class AddVegRepositoryImplTest extends BaseTest {
    @Mock
    private Context context;

    @Mock
    private EventBus eventBus;

    @Mock
    private FirebaseAPI firebaseAPI;

    @Mock
    private ImageStorage imageStorage;

    @Mock
    private Veg veg = new Veg();

    @Mock
    private AddVegRepository repository;

    private ArgumentCaptor<AddVegEvent> addVegMainEventArgumentCaptor;

    @Override
    public void setup() throws Exception {
        super.setup();
        repository = new AddVegRepositoryImpl(context, eventBus, firebaseAPI, imageStorage);
        addVegMainEventArgumentCaptor = ArgumentCaptor.forClass(AddVegEvent.class);
    }

//    @Test
//    public void testAddVeg_eventPosted() throws Exception {
//        String url = "url";
//
//        assertNull(veg.getImage());
//
//        verify(eventBus).post(addVegMainEventArgumentCaptor.capture());
//        AddVegEvent event=addVegMainEventArgumentCaptor.getValue();
//
//        assertEquals(AddVegEvent.UPLOAD_INIT, event.getType());
//        assertNull(event.getError());
//
//        verify(veg).setImage(url);
//        verify(firebaseAPI).update(veg);
//
//        assertEquals(AddVegEvent.UPLOAD_COMPLETE,event.getType());
//        assertNull(event.getError());
//    }
}
