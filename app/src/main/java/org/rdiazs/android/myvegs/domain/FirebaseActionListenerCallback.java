package org.rdiazs.android.myvegs.domain;

import com.firebase.client.FirebaseError;

public interface FirebaseActionListenerCallback {
    void onSuccess();

    void onError(FirebaseError error);
}
