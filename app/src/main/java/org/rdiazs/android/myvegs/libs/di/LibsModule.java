package org.rdiazs.android.myvegs.libs.di;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;

import org.rdiazs.android.myvegs.libs.CloudinaryImageStorage;
import org.rdiazs.android.myvegs.libs.GlideImageLoader;
import org.rdiazs.android.myvegs.libs.GreenRobotEventBus;
import org.rdiazs.android.myvegs.libs.base.EventBus;
import org.rdiazs.android.myvegs.libs.base.ImageLoader;
import org.rdiazs.android.myvegs.libs.base.ImageStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//@Module
//public class LibsModule {
//    private Fragment activity;
//
//    public LibsModule(Fragment activity) {
//        this.activity = activity;
//    }
//
//    @Provides
//    @Singleton
//    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
//        return new GreenRobotEventBus(eventBus);
//    }
//
//    @Provides
//    @Singleton
//    org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
//        return org.greenrobot.eventbus.EventBus.getDefault();
//    }
//
//    @Provides
//    @Singleton
//    ImageLoader providesImageLoader(RequestManager requestManager) {
//        return new GlideImageLoader(requestManager);
//    }
//
//    @Provides
//    @Singleton
//    RequestManager providesRequestManager(Fragment activity) {
//        return Glide.with(activity);
//    }
//
//    @Provides
//    @Singleton
//    Fragment providesFragment() {
//        return this.activity;
//    }
//
//    @Provides
//    @Singleton
//    ImageStorage providesImageStorage(Cloudinary cloudinary) {
//        return new CloudinaryImageStorage(cloudinary);
//    }
//
//    @Provides
//    @Singleton
//    Cloudinary providesCloudinary(Context context) {
//        return new Cloudinary(Utils.cloudinaryUrlFromContext(context));
//    }
//}

@Module
public class LibsModule {
    private Activity activity;

    public LibsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus) {
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus() {
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager) {
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Activity activity) {
        return Glide.with(activity);
    }

    @Provides
    @Singleton
    Activity providesActivity() {
        return this.activity;
    }

    @Provides
    @Singleton
    ImageStorage providesImageStorage(Cloudinary cloudinary) {
        return new CloudinaryImageStorage(cloudinary);
    }

    @Provides
    @Singleton
    Cloudinary providesCloudinary(Context context) {
        return new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }
}
