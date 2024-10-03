//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.pasc.lib.imageloader.picasso;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;
import java.io.File;
import java.io.IOException;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Call.Factory;
import okhttp3.OkHttpClient.Builder;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR2;

public final class CacheOkHttpDownloader implements Downloader {
  private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
  private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
  @VisibleForTesting final Call.Factory client;
  private final Cache cache;
  private boolean sharedClient = true;
  private static final String PICASSO_CACHE = "picasso-cache";

  /**
   * Create new downloader that uses OkHttp. This will install an image cache into your application
   * cache directory.
   */
  public CacheOkHttpDownloader(final Context context) {
    this(createDefaultCacheDir(context));
  }

  /**
   * Create new downloader that uses OkHttp. This will install an image cache into the specified
   * directory.
   *
   * @param cacheDir The directory in which the cache should be stored
   */
  public CacheOkHttpDownloader(final File cacheDir) {
    this(cacheDir, calculateDiskCacheSize(cacheDir));
  }

  /**
   * Create new downloader that uses OkHttp. This will install an image cache into your application
   * cache directory.
   *
   * @param maxSize The size limit for the cache.
   */
  public CacheOkHttpDownloader(final Context context, final long maxSize) {
    this(createDefaultCacheDir(context), maxSize);
  }

  /**
   * Create new downloader that uses OkHttp. This will install an image cache into the specified
   * directory.
   *
   * @param cacheDir The directory in which the cache should be stored
   * @param maxSize The size limit for the cache.
   */
  public CacheOkHttpDownloader(final File cacheDir, final long maxSize) {
    this(new OkHttpClient.Builder().cache(new Cache(cacheDir, maxSize)).build());
    sharedClient = false;
  }

  /**
   * Create a new downloader that uses the specified OkHttp instance. A response cache will not be
   * automatically configured.
   */
  public CacheOkHttpDownloader(OkHttpClient client) {
    this.client = client;
    this.cache = client.cache();
  }

  /** Create a new downloader that uses the specified {@link Call.Factory} instance. */
  public CacheOkHttpDownloader(Call.Factory client) {
    this.client = client;
    this.cache = null;
  }

  @NonNull public Response loadCache(@NonNull Request request) throws IOException {
    okhttp3.Request.Builder builder =
        (new okhttp3.Request.Builder()).cacheControl(CacheControl.FORCE_CACHE).url(request.url());
    okhttp3.Response response = this.client.newCall(builder.build()).execute();
    int responseCode = response.code();
    if (responseCode >= 300) {
      response.body().close();
      throw new IOException(responseCode + " " + response.message());
    }
    return response;
  }

  @NonNull @Override public Response load(@NonNull Request request) throws IOException {
    Response responseDiskCache = null;

    try {
      responseDiskCache = this.loadCache(request);
    } catch (Exception var10) {

    }
    long contentLength=0;
    try{
      contentLength=Long.parseLong(responseDiskCache.headers().get("Content-Length").trim());
    }catch (Exception e){

    }
    Log.d(PICASSO_CACHE,"response contentLength "+contentLength+" header contentLength "+contentLength);
    if (responseDiskCache != null
        && responseDiskCache.body() != null
        && responseDiskCache.body().contentLength()==contentLength) {
      return responseDiskCache;
    }
    return client.newCall(request).execute();
  }

  @Override public void shutdown() {
    if (!sharedClient && cache != null) {
      try {
        cache.close();
      } catch (IOException ignored) {
      }
    }
  }

  @TargetApi(JELLY_BEAN_MR2)
  static long calculateDiskCacheSize(File dir) {
    long size = MIN_DISK_CACHE_SIZE;

    try {
      StatFs statFs = new StatFs(dir.getAbsolutePath());
      //noinspection deprecation
      long blockCount =
          SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockCount() : statFs.getBlockCountLong();
      //noinspection deprecation
      long blockSize =
          SDK_INT < JELLY_BEAN_MR2 ? (long) statFs.getBlockSize() : statFs.getBlockSizeLong();
      long available = blockCount * blockSize;
      // Target 2% of the total space.
      size = available / 50;
    } catch (IllegalArgumentException ignored) {
    }

    // Bound inside min/max size for disk cache.
    return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
  }

  static File createDefaultCacheDir(Context context) {
    File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
    if (!cache.exists()) {
      //noinspection ResultOfMethodCallIgnored
      cache.mkdirs();
    }
    return cache;
  }
}
