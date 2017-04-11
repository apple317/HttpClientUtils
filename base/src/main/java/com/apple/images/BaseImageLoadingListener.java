package com.apple.images;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by apple_hsp on 15/12/2.
 * 图片组件事件提炼解耦
 */
public interface BaseImageLoadingListener {
    /**
     * Is called when image loading task was started
     *
     * @param imageUri Loading image URI
     * @param view     View for image
     */
    void onLoadingStarted(String imageUri, View view);

    /**
     * Is called when an error was occurred during image loading
     *
     * @param imageUri   Loading image URI
     * @param view       View for image. Can be <b>null</b>.
     * @param failReason  why image
     *                   loading was failed
     */
    void onLoadingFailed(String imageUri, View view, int failReason);

    /**
     * Is called when image is loaded successfully (and displayed in View if one was specified)
     *
     * @param imageUri    Loaded image URI
     * @param view        View for image. Can be <b>null</b>.
     * @param loadedImage Bitmap of loaded and decoded image
     */
    void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

    /**
     * Is called when image loading task was cancelled because View for image was reused in newer task
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     */
    void onLoadingCancelled(String imageUri, View view);
}
