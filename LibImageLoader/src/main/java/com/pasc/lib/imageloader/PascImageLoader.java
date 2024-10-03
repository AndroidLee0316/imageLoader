package com.pasc.lib.imageloader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.widget.ImageView;

import com.pasc.lib.imageloader.glide.GlideImageLoaderImpl;
import com.pasc.lib.imageloader.picasso.PicassoImageLoader;

/**
 * Created by huangtebian535 on 2018/09/13.
 *
 * PASC ImageLoader 对外静态方法。
 */
public class PascImageLoader {
    private static PascImageLoader INSTANCE;

    public static final int SCALE_DEFAULT = -1;
    public static final int SCALE_FIT = 0;
    public static final int SCALE_CENTER_INSIDE = 1;
    public static final int SCALE_CENTER_CROP = 2;

    public static final int PICASSO_CORE = 0;
    public static final int GLIDE_CORE = 1;

    private ImageLoader imageLoader;

    /**
     * 获取实例对象
     */
    public static PascImageLoader getInstance() {
        if (null == INSTANCE) {
            synchronized (PascImageLoader.class) {
                if (null == INSTANCE) {
                    INSTANCE = new PascImageLoader();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context, @CoreType int coreType, @DrawableRes int defaultImage) {
        if (coreType == PICASSO_CORE) {
            imageLoader = new PicassoImageLoader();
            imageLoader.init(context, coreType, defaultImage);
        } else {
            imageLoader = new GlideImageLoaderImpl();
            imageLoader.init(context, coreType, defaultImage);
        }
    }

    public void loadImageUrl(String imageUrl, ImageView imageView) {
        imageLoader.loadImageUrl(imageUrl, imageView);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @ScaleType int scaleType) {
        imageLoader.loadImageUrl(imageUrl, imageView, scaleType);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @ScaleType int scaleType) {
        imageLoader.loadImageUrl(imageUrl, imageView, defaultImage, scaleType);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @DrawableRes int error, @ScaleType int scaleType) {
        imageLoader.loadImageUrl(imageUrl, imageView, defaultImage, error, scaleType);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @ScaleType int scaleType, PascCallback callback) {
        imageLoader.loadImageUrl(imageUrl, imageView, defaultImage, scaleType, callback);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @DrawableRes int errorImage, @ScaleType int scaleType, PascCallback callback) {
        imageLoader.loadImageUrl(imageUrl, imageView, defaultImage, errorImage, scaleType, callback);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @ScaleType int scaleType,
        PascCallback callback, int crossFadeDuration) {
        imageLoader.loadImageUrl(imageUrl, imageView, scaleType, callback, crossFadeDuration);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
        @DrawableRes int errorImage, @ScaleType int scaleType, int crossFadeDuration, PascCallback callback) {
        imageLoader.loadImageUrl(imageUrl, imageView, defaultImage, errorImage, scaleType, crossFadeDuration, callback);
    }

    public void loadSpecificImage(String imageUrl, ImageView imageView,
            @DrawableRes int defaultImage, @ScaleType int scaleType, int height, int width) {
        imageLoader.loadSpecificImage(imageUrl, imageView, defaultImage, scaleType, height, width);
    }

    public void cacheImage(String imageUrl) {
        imageLoader.cacheImage(imageUrl);
    }

    public void loadLocalImage(String imagePath, ImageView imageView) {
        imageLoader.loadLocalImage(imagePath, imageView);
    }

    public void loadLocalImage(String imagePath, ImageView imageView, @ScaleType int scaleType) {
        imageLoader.loadLocalImage(imagePath, imageView, scaleType);
    }

    public void loadLocalImage(String imagePath, ImageView imageView, @DrawableRes int defaultImage,
            @ScaleType int scaleType) {
        imageLoader.loadLocalImage(imagePath, imageView, defaultImage, scaleType);
    }

    public void loadImageRes(@DrawableRes int resId, ImageView imageView) {
        imageLoader.loadImageRes(resId, imageView);
    }

    public void loadImageRes(@DrawableRes int resId, ImageView imageView,
            @ScaleType int scaleType) {
        imageLoader.loadImageRes(resId, imageView, scaleType);
    }

    public void loadImageRes(@DrawableRes int resId, ImageView imageView,
            @DrawableRes int defaultImage, @ScaleType int scaleType) {
        imageLoader.loadImageRes(resId, imageView, defaultImage, scaleType);
    }

    public void loadBitmap(String imageUrl, Target target) {
        imageLoader.loadBitmap(imageUrl, target);
    }

    @IntDef({ SCALE_DEFAULT, SCALE_CENTER_CROP, SCALE_CENTER_INSIDE, SCALE_FIT })
    public @interface ScaleType {
    }

    public @IntDef({ PICASSO_CORE, GLIDE_CORE }) @interface CoreType {
    }
}
