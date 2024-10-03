package com.pasc.lib.imageloader;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by huangtebian535 on 2018/09/13.
 *
 * PASC ImageLoader 统一接口定义
 */

public interface ImageLoader {

    /**
     * @param context 上下文
     * @param coreType 图片加载内核类型
     * @param defaultImage 默认图片背景
     */
    void init(Context context, int coreType, @DrawableRes int defaultImage);

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     */
    void loadImageUrl(String imageUrl, ImageView imageView);

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param scaleType 测量方式
     */
    void loadImageUrl(String imageUrl, ImageView imageView,
            @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param defaultImage 默认图片
     * @param scaleType 测量方式
     */
    void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param defaultImage 默认图片
     * @param errorImage 错误图片
     * @param scaleType 测量方式
     */
    void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @DrawableRes int errorImage, @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param defaultImage 默认图片
     * @param scaleType 测量方式
     * @param callback 结果加调
     */
    void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @PascImageLoader.ScaleType int scaleType, PascCallback callback);

    /**
     * 加载网络图片
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param defaultImage 默认图片
     * @param errorImage 错误图片
     * @param scaleType 测量方式
     * @param callback 结果加调
     */
    void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @DrawableRes int errorImage, @PascImageLoader.ScaleType int scaleType,
            PascCallback callback);

    /**
     * 加载网络图片，带渐入动画
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param scaleType 测量方式
     * @param callback 结果加调
     */
    void loadImageUrl(String imageUrl, ImageView imageView, @PascImageLoader.ScaleType int scaleType,
        PascCallback callback, int crossFadeDuration);

    /**
     * 加载网络图片，带渐入动画
     *
     * @param imageUrl 图片url
     * @param imageView ImageView对象
     * @param defaultImage 默认图片
     * @param errorImage 错误图片
     * @param scaleType 测量方式
     * @param callback 结果加调
     */
    void loadImageUrl(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
        @DrawableRes int errorImage, @PascImageLoader.ScaleType int scaleType, int crossFadeDuration,
        PascCallback callback);

    /**
     * 加载特定尺寸的图片
     *
     * @param imageUrl 图片链接地址
     * @param imageView 图片视图
     * @param defaultImage 默认图片
     * @param scaleType 测量方式
     * @param height 图片高度
     * @param width 图片宽度
     */
    void loadSpecificImage(String imageUrl, ImageView imageView, @DrawableRes int defaultImage,
            @PascImageLoader.ScaleType int scaleType, int height, int width);

    /**
     * 缓存网络图片不展示
     *
     * @param imageUrl 图片链接地址
     */
    void cacheImage(String imageUrl);

    /**
     * 加载本地图片
     *
     * @param imagePath 本地图片路径
     * @param imageView 图片视图
     */
    void loadLocalImage(String imagePath, ImageView imageView);

    /**
     * 加载本地图片
     *
     * @param imagePath 本地图片路径
     * @param imageView 图片视图
     * @param scaleType 测量方式
     */
    void loadLocalImage(String imagePath, ImageView imageView,
            @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载本地图片
     *
     * @param imagePath 本地图片路径
     * @param imageView 图片视图
     * @param defaultImage 默认图片
     * @param scaleType 测量方式
     */
    void loadLocalImage(String imagePath, ImageView imageView, @DrawableRes int defaultImage,
            @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载资源图片
     *
     * @param resId 本地图片id
     * @param imageView 图片视图
     */
    void loadImageRes(@DrawableRes int resId, ImageView imageView);

    /**
     * 加载资源图片
     *
     * @param resId 本地图片id
     * @param imageView 图片视图
     * @param scaleType 测量方式
     */
    void loadImageRes(@DrawableRes int resId, ImageView imageView,
            @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载资源图片
     *
     * @param resId 本地图片id
     * @param imageView 图片视图
     * @param defaultImage 默认图片
     * @param scaleType 测量方式
     */
    void loadImageRes(@DrawableRes int resId, ImageView imageView, @DrawableRes int defaultImage,
            @PascImageLoader.ScaleType int scaleType);

    /**
     * 加载bitmap
     *
     * @param imageUrl 图片链接地址
     * @param target Bitmap 加载回调
     */
    void loadBitmap(String imageUrl, Target target);
}
