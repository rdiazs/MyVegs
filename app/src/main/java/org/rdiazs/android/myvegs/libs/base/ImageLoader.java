package org.rdiazs.android.myvegs.libs.base;

import android.widget.ImageView;

public interface ImageLoader {
    void load(ImageView imageView, String url);

    void setOnFinishedImageLoadingListener(Object listener);
}
