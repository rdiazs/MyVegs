package org.rdiazs.android.myvegs.addveg.mvp;

import android.content.Context;

import org.rdiazs.android.myvegs.R;
import org.rdiazs.android.myvegs.addveg.events.AddVegEvent;
import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.libs.base.ImageStorage;
import org.rdiazs.android.myvegs.libs.base.ImageStorageFinishedListener;

import java.io.File;

/**
 * Implementación del <code>Repository</code> para añadir un cultivo.
 */
public class AddVegRepositoryImpl implements AddVegRepository {
    private Context context;
    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;
    private ImageStorage imageStorage;

    private final static String VEGS_FOLDER = "MyVegs/";

    public AddVegRepositoryImpl(Context context, EventBus eventBus, FirebaseAPI firebaseAPI,
                                ImageStorage imageStorage) {
        this.context = context;
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
        this.imageStorage = imageStorage;
    }

    @Override
    public void addVeg(final Veg veg) {
        ImageStorageFinishedListener listener = null;
        boolean uploadImage = false;
        final String imageId = VEGS_FOLDER + firebaseAPI.createId();

        if (veg.getImage() == null) { // Imagen por defecto
            postEvent(AddVegEvent.UPLOAD_INIT);
            String url = imageStorage.getImageUrl(
                    VEGS_FOLDER + context.getResources().getString(R.string.LOGO_IMAGE_ID));
            veg.setImage(url);
            firebaseAPI.update(veg);
            postEvent(AddVegEvent.UPLOAD_COMPLETE);
        } else { // Guardar la imagen en Cloudinary
            postEvent(AddVegEvent.UPLOAD_INIT);
            uploadImage = true;

            listener = new ImageStorageFinishedListener() {
                @Override
                public void onSuccess() {
                    String url = imageStorage.getImageUrl(imageId);
                    veg.setImage(url);
                    firebaseAPI.update(veg);
                    postEvent(AddVegEvent.UPLOAD_COMPLETE);
                }

                @Override
                public void onError(String error) {
                    postEvent(AddVegEvent.UPLOAD_ERROR);
                }
            };
        }
        if (uploadImage) {
            imageStorage.upload(new File(veg.getImage()), imageId, listener);
        }
    }

    @Override
    public void removeVeg(Veg veg) {

    }

    private void postEvent(int type) {
        postEvent(type, null);
    }

    private void postEvent(int type, String error) {
        AddVegEvent event = new AddVegEvent();
        event.setType(type);
        event.setError(error);
        eventBus.post(event);
    }
}
