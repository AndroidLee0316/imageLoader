package com.pasc.lib.imageloader.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.pasc.lib.imageloader.ImageLoader;
import com.pasc.lib.imageloader.PascCallback;
import com.pasc.lib.imageloader.PascImageLoader;
import com.pasc.lib.imageloader.R;
import com.pasc.lib.imageloader.Target;
import com.pasc.lib.imageloader.glide.progress.OnProgressListener;

/**
 * imageView ScaleType 默认 ScaleType.FIT_CENTER
 */
public class GlideImageLoaderImpl implements ImageLoader {

    private int DEFAULT_COLOR = R.color.violet_f0f5ff;
    private Context mContext;

    @Override
    public void init(Context context, int type, @DrawableRes int defaultImage) {
        if (context != null) {
            mContext = context.getApplicationContext();
            if (isResource(context, defaultImage)) {
                DEFAULT_COLOR = defaultImage;
            }

        }

    }

    private boolean isResource(Context context, int resId) {
        if (context != null) {
            try {
                return context.getResources().getResourceName(resId) != null;
            } catch (Resources.NotFoundException ignore) {
                ignore.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView) {
        loadImageUrl(imageUrl, DEFAULT_COLOR, imageView);
    }

    public void loadImageUrl(String imageUrl, @DrawableRes int defaultImage, ImageView imageView) {
        GlideImageLoader.create(imageView)
                .loadImage(imageUrl, defaultImage);
    }

    public void loadImageUrl(String imageUrl, @DrawableRes int defaultImage, @DrawableRes int errorImage, ImageView imageView) {
        GlideImageLoader.create(imageView)
                .loadImage(imageUrl, defaultImage);
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @PascImageLoader.ScaleType int scaleType) {
        loadImageUrl(imageUrl, imageView, DEFAULT_COLOR, scaleType);
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage, @PascImageLoader.ScaleType int scaleType) {
        loadImageUrl(imageUrl, imageView, defaultImage, scaleType, null);

    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
                             @DrawableRes int errorImage, @PascImageLoader.ScaleType int scaleType) {
        loadImageUrl(imageUrl, imageView, defaultImage, errorImage, scaleType, null);

    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView,
                             @DrawableRes int defaultImage, @PascImageLoader.ScaleType int scaleType, PascCallback callback) {
        loadImageUrl(imageUrl, imageView, defaultImage, defaultImage, scaleType, callback);
    }

    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage, @DrawableRes int errorImage,
                             @PascImageLoader.ScaleType int scaleType, final PascCallback callback) {
        GlideImageLoader glideImageLoader = GlideImageLoader.create(imageView);
        if (callback != null) {
            glideImageLoader.setOnProgressListener(imageUrl, new OnProgressListener() {
                @Override
                public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
                    if (callback != null) {
                        if (isDone && exception == null)
                            callback.onSuccess();
                        else if (isDone && exception != null)
                            callback.onError();
                    }
                }
            });
        }
        glideImageLoader.load(imageUrl, getRequestOptions(scaleType).error(errorImage).placeholder(defaultImage));
    }

    @Override public void loadImageUrl(String imageUrl, ImageView imageView, int scaleType,
        final PascCallback callback, int crossFadeDuration) {
        GlideImageLoader glideImageLoader = GlideImageLoader.create(imageView);
        if (callback != null) {
            glideImageLoader.setOnProgressListener(imageUrl, new OnProgressListener() {
                @Override
                public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
                    if (callback != null) {
                        if (isDone && exception == null)
                            callback.onSuccess();
                        else if (isDone && exception != null)
                            callback.onError();
                    }
                }
            });
        }
        glideImageLoader.load(imageUrl, getRequestOptions(scaleType), crossFadeDuration);
    }

    @Override
    public void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage, @DrawableRes int errorImage,
        @PascImageLoader.ScaleType int scaleType, int crossFadeDuration, final PascCallback callback) {
        GlideImageLoader glideImageLoader = GlideImageLoader.create(imageView);
        if (callback != null) {
            glideImageLoader.setOnProgressListener(imageUrl, new OnProgressListener() {
                @Override
                public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
                    if (callback != null) {
                        if (isDone && exception == null)
                            callback.onSuccess();
                        else if (isDone && exception != null)
                            callback.onError();
                    }
                }
            });
        }
        glideImageLoader.load(imageUrl, getRequestOptions(scaleType).error(errorImage).placeholder(defaultImage), crossFadeDuration);
    }

    @Override
    public void loadSpecificImage(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
                                  @PascImageLoader.ScaleType int scaleType, int height, int width) {
        GlideImageLoader.create(imageView)
                .load(imageUrl, getRequestOptions(scaleType)
                        .error(defaultImage)
                        .placeholder(defaultImage).override(width, height));
    }

    @Override
    public void cacheImage(String imageUrl) {

        if (mContext != null) {
            Glide.with(mContext).load(imageUrl).preload();
        }
    }

    @Override
    public void loadLocalImage(String imagePath, ImageView imageView) {
        GlideImageLoader.create(imageView)
                .loadLocalImage(imagePath, DEFAULT_COLOR);
    }

    @Override
    public void loadLocalImage(String imagePath, ImageView imageView, @PascImageLoader.ScaleType int scaleType) {
        loadLocalImage(imagePath, imageView, DEFAULT_COLOR, scaleType);
    }

    @Override
    public void loadLocalImage(String imagePath, ImageView imageView, int defaultImage, @PascImageLoader.ScaleType int scaleType) {
        GlideImageLoader.create(imageView)
                .loadLocalImage(imagePath, getRequestOptions(scaleType)
                        .error(defaultImage)
                        .placeholder(defaultImage));
    }

    @Override
    public void loadImageRes(int resId, ImageView imageView) {
        GlideImageLoader.create(imageView)
                .loadResImage(resId, DEFAULT_COLOR);
    }

    @Override
    public void loadImageRes(int resId, ImageView imageView, @PascImageLoader.ScaleType int scaleType) {
        loadImageRes(resId, imageView, DEFAULT_COLOR, scaleType);
    }

    @Override
    public void loadImageRes(int resId, ImageView imageView, int defaultImage, @PascImageLoader.ScaleType int scaleType) {
        GlideImageLoader.create(imageView).load(resId, getRequestOptions(scaleType).error(defaultImage).placeholder(defaultImage));
    }

    @Override
    public void loadBitmap(String imageUrl, final Target target) {
        if (mContext != null) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(imageUrl)
                    .apply(requestOptions(DEFAULT_COLOR,DEFAULT_COLOR))
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            if (target != null) {
                                target.onPrepareLoad(placeholder);
                            }
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            if (target != null) {
                                target.onBitmapFailed(errorDrawable);
                            }
                        }

                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (target != null) {
                                target.onBitmapLoaded(resource);
                            }
                        }
                    });

        }
    }
    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }
    /**
     * 加载网络图片
     *
     * @param url     图片网址
     * @param options RequestOptions对象
     */
    private static void load(ImageView imageView, String url, RequestOptions options) {
        GlideImageLoader.create(imageView)
                .load(url, options);
    }


    @NonNull
    private static RequestOptions getRequestOptions(int scaleType) {
        RequestOptions requestOptions;
        if (scaleType == PascImageLoader.SCALE_CENTER_CROP) {
            requestOptions = new RequestOptions().centerCrop();
        } else if (scaleType == PascImageLoader.SCALE_CENTER_INSIDE) {
            requestOptions = new RequestOptions().centerInside();
        } else {
            requestOptions = new RequestOptions().fitCenter();
        }
        return requestOptions;
    }

}

