package com.pasc.lib.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Represents an arbitrary listener for image loading.
 */
public interface Target {

    /**
     * Callback when an image has been successfully loaded.
     */
    void onBitmapLoaded(Bitmap bitmap);

    /**
     * Callback indicating the image could not be successfully loaded.
     */
    void onBitmapFailed(Drawable errorDrawable);

    /**
     * Callback invoked right before your request is submitted.
     */
    void onPrepareLoad(Drawable placeHolderDrawable);
}
