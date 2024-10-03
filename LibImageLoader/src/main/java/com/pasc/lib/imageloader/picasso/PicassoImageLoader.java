package com.pasc.lib.imageloader.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.pasc.lib.imageloader.ImageLoader;
import com.pasc.lib.imageloader.PascCallback;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.imageloader.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.File;
import okhttp3.OkHttpClient;

import static com.pasc.lib.imageloader.PascImageLoader.SCALE_DEFAULT;

/**
 * Created by huangtebian535 on 2018/09/13.
 *
 * Picasso 实现类.
 */
public class PicassoImageLoader implements ImageLoader {
    private static final String TAG = PicassoImageLoader.class.getSimpleName();
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.RGB_565;

    private Context mContext;
    private Picasso sInstance;

    private int mDefaultImage;

    @Override public void init(Context context, int coreType, @DrawableRes int defaultImage) {
        mContext = context.getApplicationContext();
        mDefaultImage = defaultImage;
        if (sInstance == null) {
            String cacheFilePath = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File externalCacheFile = mContext.getExternalCacheDir();
                if (externalCacheFile != null) {
                    cacheFilePath = externalCacheFile.getAbsolutePath() + File.separator + "image";
                }
            } else {
                File cacheFile = mContext.getCacheDir();
                if (cacheFile != null) {
                    cacheFilePath = cacheFile.getAbsolutePath() + File.separator + "image";
                }
            }

            if (TextUtils.isEmpty(cacheFilePath)) {
                cacheFilePath = "/sdcard/temp/image";
            }

            File cacheFileDir = new File(cacheFilePath);
            if (!cacheFileDir.exists()) {
                cacheFileDir.mkdirs();
            }
            OkHttpClient.Builder builder = OkHttpClientManager.getDefaultOkHttpBuilder();
            builder.cache(new okhttp3.Cache(cacheFileDir, 1024 * 1024 * 100));
            if (isStethoPresent()) {
                builder.addNetworkInterceptor(new StethoInterceptor());
            }
            sInstance = new Picasso.Builder(mContext).defaultBitmapConfig(DEFAULT_CONFIG)
                    .downloader(new CacheOkHttpDownloader(builder.build()))
                    .loggingEnabled(false)
                    .listener(new Picasso.Listener() {
                        @Override public void onImageLoadFailed(Picasso picasso, Uri uri,
                                Exception exception) {
                            if (null != exception) {
                                exception.printStackTrace();
                            }
                        }
                    })
                    .build();
            Picasso.setSingletonInstance(sInstance);
        }
    }

    private static boolean isStethoPresent() {
        try {
            Class.forName("com.facebook.stetho.Stetho");
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override public void loadImageUrl(String imageUrl, ImageView imageView) {
        loadImageUrl(imageUrl, imageView, SCALE_DEFAULT);
    }

    @Override public void loadImageUrl(String imageUrl, ImageView imageView, int scaleType) {
        loadImageUrl(imageUrl, imageView, mDefaultImage, scaleType);
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            int scaleType) {
        loadImageUrl(imageUrl, imageView, defaultImage, defaultImage, scaleType);
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @DrawableRes int error, int scaleType) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl) && !"null".equals(imageUrl)) {
                setScaleType(sInstance.load(imageUrl), scaleType).placeholder(defaultImage)
                        .error(error)
                        .into(imageView);
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            int scaleType, final PascCallback callback) {
        loadImageUrl(imageUrl, imageView, defaultImage, defaultImage, scaleType, callback);
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, int defaultImage, int errorImage,
            int scaleType, final PascCallback callback) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl) && !"null".equals(imageUrl)) {
                setScaleType(sInstance.load(imageUrl), scaleType).placeholder(defaultImage)
                        .error(errorImage)
                        .into(imageView, new Callback() {
                            @Override public void onSuccess() {
                                callback.onSuccess();
                            }

                            @Override public void onError(Exception e) {
                                callback.onError();
                            }

                            //@Override public void onError() {
                            //    callback.onError();
                            //}
                        });
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    @Override public void loadImageUrl(String imageUrl, ImageView imageView, int scaleType,
        final PascCallback callback, int crossFadeDuration) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl) && !"null".equals(imageUrl)) {
                setScaleType(sInstance.load(imageUrl), scaleType).into(imageView, new Callback() {
                        @Override public void onSuccess() {
                            callback.onSuccess();
                        }

                        @Override public void onError(Exception e) {
                            callback.onError();
                        }

                        //@Override public void onError() {
                        //    callback.onError();
                        //}
                    });
            }
        }
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, int defaultImage, int errorImage,
        int scaleType, int crossFadeDuration, final PascCallback callback) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl) && !"null".equals(imageUrl)) {
                setScaleType(sInstance.load(imageUrl), scaleType).placeholder(defaultImage)
                    .error(errorImage)
                    .into(imageView, new Callback() {
                        @Override public void onSuccess() {
                            callback.onSuccess();
                        }

                        @Override public void onError(Exception e) {
                            callback.onError();
                        }

                        //@Override public void onError() {
                        //    callback.onError();
                        //}
                    });
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    @Override public void loadSpecificImage(String imageUrl, ImageView imageView,
            @DrawableRes int defaultImage, int scaleType, int height, int width) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imageUrl)) {
                setScaleType(sInstance.load(imageUrl), scaleType).resize(height, width)
                        .placeholder(defaultImage)
                        .error(defaultImage)
                        .into(imageView);
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    @Override public void cacheImage(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl) && imageUrl.startsWith("http")) {
            sInstance.load(imageUrl).fetch();
        }
    }

    @Override public void loadLocalImage(String imagePath, ImageView imageView) {
        loadLocalImage(imagePath, imageView, SCALE_DEFAULT);
    }

    @Override public void loadLocalImage(String imagePath, ImageView imageView, int scaleType) {
        loadLocalImage(imagePath, imageView, mDefaultImage, scaleType);
    }

    @Override
    public void loadLocalImage(String imagePath, ImageView imageView, @DrawableRes int defaultImage,
            int scaleType) {
        if (imageView != null) {
            if (!TextUtils.isEmpty(imagePath)) {
                setScaleType(sInstance.load(new File(imagePath)), scaleType).placeholder(
                        defaultImage).error(defaultImage).into(imageView);
            } else {
                imageView.setImageResource(defaultImage);
            }
        }
    }

    @Override public void loadImageRes(@DrawableRes int resId, ImageView imageView) {
        loadImageRes(resId, imageView, SCALE_DEFAULT);
    }

    @Override public void loadImageRes(@DrawableRes int resId, ImageView imageView, int scaleType) {
        loadImageRes(resId, imageView, mDefaultImage, scaleType);
    }

    @Override public void loadImageRes(@DrawableRes int resId, ImageView imageView,
            @DrawableRes int defaultImage, int scaleType) {
        if (imageView != null) {
            setScaleType(sInstance.load(resId), scaleType).placeholder(defaultImage)
                    .error(defaultImage)
                    .into(imageView);
        }
    }

    @Override public void loadBitmap(String imageUrl, final Target target) {
        sInstance.load(imageUrl).into(new com.squareup.picasso.Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                target.onBitmapLoaded(bitmap);
            }

            @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                target.onBitmapFailed(errorDrawable);
            }

            //@Override public void onBitmapFailed(Drawable errorDrawable) {
            //    target.onBitmapFailed(errorDrawable);
            //}

            @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
                target.onPrepareLoad(placeHolderDrawable);
            }
        });
    }

    private RequestCreator setScaleType(RequestCreator requestCreator, int scaleType) {
        switch (scaleType) {
            case PascImageLoader.SCALE_CENTER_CROP:
                requestCreator.fit().centerCrop();
                break;
            case PascImageLoader.SCALE_CENTER_INSIDE:
                requestCreator.fit().centerInside();
                break;
            case PascImageLoader.SCALE_FIT:
                requestCreator.fit();
                break;
        }
        return requestCreator;
    }
}
