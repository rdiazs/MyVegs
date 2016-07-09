package org.rdiazs.android.myvegs.vegList.mvp;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import org.rdiazs.android.myvegs.domain.FirebaseAPI;
import org.rdiazs.android.myvegs.domain.FirebaseActionListenerCallback;
import org.rdiazs.android.myvegs.domain.FirebaseEventListenerCallback;
import org.rdiazs.android.myvegs.entities.Veg;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.vegList.events.VegListEvent;

/**
 * Implementa el <code>Repository</code> para la lista de cultivos.
 */
public class VegListRepositoryImpl implements VegListRepository {
    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public VegListRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void subscribe() {
        firebaseAPI.checkForData(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(FirebaseError error) {
                if (error != null) {
                    postEvent(VegListEvent.READ_EVENT, error.getMessage());
                } else {
                    postEvent(VegListEvent.READ_EVENT, "");
                }
            }
        });

        firebaseAPI.subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChildAdded(DataSnapshot snapshot) {
                String email = firebaseAPI.getAuthEmail().replace(".", "_");

                for (DataSnapshot child : snapshot.child(email).getChildren()) {
                    Veg veg = child.getValue(Veg.class);
                    postEvent(VegListEvent.READ_EVENT, veg);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(FirebaseError error) {
                postEvent(VegListEvent.READ_EVENT, error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebaseAPI.unsubscribe();
    }

    @Override
    public String getAuthMail() {
        return this.firebaseAPI.getAuthEmail();
    }

    @Override
    public void logout() {
        firebaseAPI.logout();
    }

    @Override
    public void removeVeg(final Veg veg) {
        firebaseAPI.remove(veg, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(VegListEvent.DELETE_EVENT, veg);
            }

            @Override
            public void onError(FirebaseError error) {
                postEvent(VegListEvent.DELETE_EVENT, error.getMessage());
            }
        });
    }

    private void postEvent(int type, Veg veg) {
        postEvent(type, null, veg);
    }

    private void postEvent(int type, String error) {
        postEvent(type, error, null);
    }

    private void postEvent(int type, String error, Veg veg) {
        VegListEvent event = new VegListEvent();
        event.setType(type);
        event.setError(error);
        event.setVeg(veg);
        eventBus.post(event);
    }


//    @Override
//    public void signOff() {
//        helper.signOff();
//    }
//
//    @Override
//    public String getCurrentUserEmail() {
//        return helper.getAuthUserEmail();
//    }
//
//    @Override
//    public void removeVeg(Veg veg) {
//        Firebase myVegsReference = helper.getMyVegsReference();
//        myVegsReference.child(veg.getName()).removeValue();
//    }
//
//    @Override
//    public void destroyListener() {
//        eventListener = null;
//    }
//
//    @Override
//    public void subscribeToVegListEvents() {
//        if (eventListener == null) {
//            eventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    handleVeg(dataSnapshot, VegListEvent.onVegAdded);
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    handleVeg(dataSnapshot, VegListEvent.onVegChanged);
//                }
//
//                @Override
//                public void onChildRemoved(DataSnapshot dataSnapshot) {
//                    handleVeg(dataSnapshot, VegListEvent.onVegRemoved);
//                }
//
//                @Override
//                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                    // No hacer nada
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//                    //No hacer nada
//                }
//            };
//        }
//
//        helper.getMyVegsReference().addChildEventListener(eventListener);
//    }
//
//    private void handleVeg(DataSnapshot dataSnapshot, int type) {
//        Veg veg = dataSnapshot.getValue(Veg.class);
//
//        post(type, veg);
//    }
//
//    private void post(int type, Veg veg) {
//        VegListEvent event = new VegListEvent();
//
//        event.setType(type);
//        event.setVeg(veg);
//        eventBus.post(event);
//    }
//
//    @Override
//    public void unsubscribeFromVegListEvents() {
//        if (eventListener != null) {
//            helper.getMyVegsReference().removeEventListener(eventListener);
//        }
//    }
}
