package com.pasc.lib.imageloader.glide.progress;


public interface OnGlideImageViewListener {

    void onProgress(int percent, boolean isDone, Exception exception);
}
