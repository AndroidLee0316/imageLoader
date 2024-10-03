package com.pasc.lib.imageloader.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.pasc.lib.imageloader.R;
import com.pasc.lib.imageloader.glide.progress.OnGlideImageViewListener;
import com.pasc.lib.imageloader.glide.progress.OnProgressListener;
import com.pasc.lib.imageloader.glide.progress.ProgressManager;
import com.pasc.lib.imageloader.glide.transformation.GlideCircleTransformation;
import java.lang.ref.WeakReference;


public class GlideImageLoader {
    private static final int DEFAULT_IMG_COLOR = R.color.violet_f0f5ff;

    private static final String ANDROID_RESOURCE = "android.resource://";
    private static final String FILE = "file://";
    private static final String SEPARATOR = "/";
    private static final String HTTP = "http";

    private WeakReference<ImageView> mImageView;
    private Object mImageUrlObj;
    private long mTotalBytes = 0;
    private long mLastBytesRead = 0;
    private boolean mLastStatus = false;
    private Handler mMainThreadHandler;

    private OnProgressListener internalProgressListener;
    private OnGlideImageViewListener onGlideImageViewListener;
    private OnProgressListener onProgressListener;

    public static GlideImageLoader create(ImageView imageView) {
        return new GlideImageLoader(imageView);
    }

    private GlideImageLoader(ImageView imageView) {
        mImageView = new WeakReference<>(imageView);
        mMainThreadHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 加载网络图片，默认使用 {DEFAULT_IMG_COLOR}作为占位图资源
     *
     * @param url 图片url
     */
    public void loadImage(String url) {
        load(url, requestOptions(DEFAULT_IMG_COLOR));
    }

    /**
     * 加载网络图片
     *
     * @param url              图片url
     * @param placeholderResId 占位图资源ID
     */
       public void loadImage(String url, int placeholderResId) {
        load(url, requestOptions(placeholderResId));
    }


    /**
     * 加载本地图片
     *
     * @param resId            图片本地资源id
     * @param placeholderResId 占位图资源ID
     */
    public void loadResImage(@DrawableRes int resId, int placeholderResId) {
        load(resId, requestOptions(placeholderResId));
    }

    /**
     * 加载本地图片
     *
     * @param localPath        图片本地路径
     * @param placeholderResId 占位图资源ID
     */
    public void loadLocalImage(String localPath, int placeholderResId) {
        load(FILE + localPath, requestOptions(placeholderResId));
    }

    public void loadLocalImage(String localPath, RequestOptions options) {
        load(FILE + localPath, options);
    }

    /**
     * 加载圆形图片（网络）
     *
     * @param url              图片网址
     * @param placeholderResId 占位图资源ID
     */
    public void loadCircleImage(String url, int placeholderResId) {
        load(url, circleRequestOptions(placeholderResId));

    }

    /**
     * 加载圆形图片（本地）
     *
     * @param resId            本地资源id
     * @param placeholderResId 占位图资源ID
     */
    public void loadLocalCircleImage(@DrawableRes int resId, int placeholderResId) {
        load(resId, circleRequestOptions(placeholderResId));
    }

    /**
     * 加载圆形图片（本地存储）
     *
     * @param localPath        本地资源路径
     * @param placeholderResId 占位图资源ID
     */
    public void loadLocalCircleImage(String localPath, int placeholderResId) {
        load(FILE + localPath, circleRequestOptions(placeholderResId));
    }


    /**
     * 加载本地图片
     *
     * @param resId   资源Id
     * @param options RequestOptions对象
     */
    public void load(int resId, RequestOptions options) {
        load(resId2Uri(resId), options);
    }

    /**
     * 加载本地图片
     *
     * @param uri     图片uri路径
     * @param options RequestOptions对象
     */
    public void load(Uri uri, RequestOptions options) {
        if (uri == null  || isActivityDestroy()){
            return;
        }
        requestBuilder(uri, options).into(getImageView());
    }

    /**
     * 加载本地图片，有渐入动画
     *
     * @param uri     图片uri路径
     * @param options RequestOptions对象
     * @param crossFadeDuration 渐入动画时长
     */
    public void load(Uri uri, RequestOptions options, int crossFadeDuration) {
        if (uri == null  || isActivityDestroy()){
            return;
        }
        requestBuilder(uri, options, crossFadeDuration).into(getImageView());
    }

    /**
     * 加载网络图片
     *
     * @param url     图片网址
     * @param options RequestOptions对象
     */
    public void load(String url, RequestOptions options) {
        if (url == null  || isActivityDestroy()){
            return;
        }
        requestBuilder(url, options).into(getImageView());
    }

    /**
     * 加载网络图片，有渐入动画
     *
     * @param url     图片网址
     * @param options RequestOptions对象
     * @param crossFadeDuration 渐入动画时长
     */
    public void load(String url, RequestOptions options, int crossFadeDuration) {
        if (url == null  || isActivityDestroy()){
            return;
        }
        requestBuilder(url, options, crossFadeDuration).into(getImageView());
    }

    /**
     * Activity 是否已经销毁
     * @return
     */
    private boolean isActivityDestroy(){
        if (getContext()==null ){
            return true;
        }
        if (getContext() instanceof Activity){
            Activity activity= (Activity) getContext();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 添加网络图片加载进度监听
     *
     * @param imageUrl           图片网址
     * @param onProgressListener 监听回调
     */
    public void setOnProgressListener(String imageUrl, OnProgressListener onProgressListener) {
        this.mImageUrlObj = imageUrl;
        this.onProgressListener = onProgressListener;
        addProgressListener();
    }

    /**
     * 添加网络图片加载进度监听----针对GlideImageView控件
     *
     * @param imageUrl                 图片网址
     * @param onGlideImageViewListener 监听回调
     */
    public void setOnGlideImageViewListener(String imageUrl, OnGlideImageViewListener onGlideImageViewListener) {
        this.mImageUrlObj = imageUrl;
        this.onGlideImageViewListener = onGlideImageViewListener;
        addProgressListener();
    }

    /**
     * 资源ID转Uri
     */
    public Uri resId2Uri(int resourceId) {
        if (getContext() == null) return null;
        return Uri.parse(ANDROID_RESOURCE + getContext().getPackageName() + SEPARATOR + resourceId);
    }

    /**
     * 获取当前ImageView对象
     */
    public ImageView getImageView() {
        if (mImageView != null) {
            return mImageView.get();
        }
        return null;
    }

    /**
     * 获取ImageView依附的上下文
     */
    public Context getContext() {
        if (getImageView() != null) {
            return getImageView().getContext();
        }
        return null;
    }

    /**
     * 获取图片url网址
     */
    public String getImageUrl() {
        if (mImageUrlObj == null) return null;
        if (!(mImageUrlObj instanceof String)) return null;
        return (String) mImageUrlObj;
    }

    /**
     * 添加加载进度监听
     */
    private void addProgressListener() {
        if (getImageUrl() == null) return;
        final String url = getImageUrl();
        if (!url.startsWith(HTTP)) return;

        internalProgressListener = new OnProgressListener() {
            @Override
            public void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception) {
                if (totalBytes == 0) return;
                if (!url.equals(imageUrl)) return;
                if (mLastBytesRead == bytesRead && mLastStatus == isDone) return;

                mLastBytesRead = bytesRead;
                mTotalBytes = totalBytes;
                mLastStatus = isDone;
                mainThreadCallback(bytesRead, totalBytes, isDone, exception);

                if (isDone) {
                    ProgressManager.removeProgressListener(this);
                }
            }
        };
        ProgressManager.addProgressListener(internalProgressListener);
    }

    private void mainThreadCallback(final long bytesRead, final long totalBytes, final boolean isDone, final Exception exception) {
        mMainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                final int percent = (int) ((bytesRead * 1.0f / totalBytes) * 100.0f);
                if (onProgressListener != null) {
                    onProgressListener.onProgress((String) mImageUrlObj, bytesRead, totalBytes, isDone, exception);
                }

                if (onGlideImageViewListener != null) {
                    onGlideImageViewListener.onProgress(percent, isDone, exception);
                }
            }
        });
    }

    public RequestBuilder<Drawable> requestBuilder(Object obj, RequestOptions options) {
        this.mImageUrlObj = obj;
        RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, e);
                ProgressManager.removeProgressListener(internalProgressListener);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, null);
                ProgressManager.removeProgressListener(internalProgressListener);
                return false;
            }
        };
        return Glide.with(getContext())
                .load(obj)
                .apply(options)
                .listener(requestListener);
    }

    public RequestBuilder<Drawable> requestBuilder(Object obj, RequestOptions options, int crossFadeDuration) {
        this.mImageUrlObj = obj;
        RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, e);
                ProgressManager.removeProgressListener(internalProgressListener);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mainThreadCallback(mLastBytesRead, mTotalBytes, true, null);
                ProgressManager.removeProgressListener(internalProgressListener);
                return false;
            }
        };
        DrawableCrossFadeFactory drawableCrossFadeFactory =
            new DrawableCrossFadeFactory.Builder(crossFadeDuration).setCrossFadeEnabled(true).build();
        return Glide.with(getContext())
            .load(obj)
            .apply(options)
            .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
            .listener(requestListener);
    }

    public RequestOptions requestOptions(int placeholderResId) {
        return requestOptions(placeholderResId, placeholderResId);
    }

    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }

    public RequestOptions circleRequestOptions(int placeholderResId) {
        return circleRequestOptions(placeholderResId, placeholderResId);
    }

    public RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId)
                .transform(new GlideCircleTransformation());
    }
}
