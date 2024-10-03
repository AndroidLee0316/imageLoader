package com.pasc.lib.imageloader.glide.progress;

public interface OnProgressListener {

    void onProgress(String imageUrl, long bytesRead, long totalBytes, boolean isDone, Exception exception);
}
