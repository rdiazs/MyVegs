package org.rdiazs.android.myvegs.domain;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.rdiazs.android.myvegs.entities.Veg;

import java.util.Map;

public class FirebaseAPI {
    private Firebase firebase;
    private ChildEventListener eventListener;

    private final static String EMAIL_KEY = "email";
    public final static String USERS_PATH = "users";
    public final static String VEGS_PATH = "vegs";

    public FirebaseAPI(Firebase firebase) {
        this.firebase = firebase;
    }

    public void checkForData(final FirebaseActionListenerCallback listener) {
        this.firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    listener.onSuccess();
                } else {
                    listener.onError(null);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onError(firebaseError);
            }
        });
    }

    public void subscribe(final FirebaseEventListenerCallback listenerCallback) {
        if (this.eventListener == null) {
            this.eventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    listenerCallback.onCancelled(firebaseError);
                }
            };

            this.firebase.addChildEventListener(this.eventListener);
        }
    }

    public void unsubscribe() {
        if (this.eventListener != null) {
            this.firebase.removeEventListener(this.eventListener);
        }
    }

    public String createId() {
        return firebase.push().getKey();
    }

    public void update(Veg veg) {
        this.firebase.getRoot()
                .child(USERS_PATH)
                .child(getAuthEmail().replace(".", "_"))
//                .child(VEGS_PATH)
                .child(veg.getName())
                .setValue(veg);
    }

    public void remove(Veg veg, FirebaseActionListenerCallback listenerCallback) {
        this.firebase.getRoot()
                .child(USERS_PATH)
                .child(getAuthEmail().replace(".", "_"))
//                .child(VEGS_PATH)
                .child(veg.getName())
                .removeValue();
        listenerCallback.onSuccess();
    }

    public String getAuthEmail() {
        String email = null;

        if (this.firebase.getAuth() != null) {
            Map<String, Object> providerData = this.firebase.getAuth().getProviderData();
            email = providerData.get(EMAIL_KEY).toString();
        }

        return email;
    }

    public void logout() {
        this.firebase.unauth();
    }

    public void login(String email, String password, final FirebaseActionListenerCallback
            listenerCallback) {
        this.firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listenerCallback.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listenerCallback.onError(firebaseError);
            }
        });
    }

    public void signUp(String email, String password, final FirebaseActionListenerCallback
            listenerCallback) {
        this.firebase.createUser(
                email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        listenerCallback.onSuccess();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        listenerCallback.onError(firebaseError);
                    }
                });
    }

    public void checkForSession(FirebaseActionListenerCallback listenerCallback) {
        if (this.firebase.getAuth() != null) {
            listenerCallback.onSuccess();
        } else {
            listenerCallback.onError(null);
        }
    }

    public void createUser(String email) {
        if (email != null) {
            String emailKey = email.replace(".", "_");
            this.firebase.getRoot().child(USERS_PATH).child(EMAIL_KEY).setValue(emailKey);
        }
    }

    public void createVeg(Veg veg) {
        String email = getAuthEmail();
        if (veg != null) {
            this.firebase.child(getAuthEmail()).child(VEGS_PATH).setValue(veg);
        }
    }

//    public Firebase getDataReference() {
//        return dataReference;
//    }
//
//
//    public Firebase getUserReference(String email) {
//        Firebase userReference = null;
//
//        if (email != null) {
//            String emailKey = email.replace(".", "_");
//            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
//        }
//
//        return userReference;
//    }
//
//    public Firebase getMyUserReference() {
//        return getUserReference(getAuthUserEmail());
//    }
//
//    public Firebase getVegsReference(String email) {
//        return getUserReference(email).child(VEGS_PATH);
//    }
//
//    public Firebase getMyVegsReference() {
//        return getVegsReference(getAuthUserEmail());
//    }
}
