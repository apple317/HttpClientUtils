package com.apple.images;

import android.view.View;

/**
 * Created by apple_hsp on 15/12/2.
 */
public interface BaseImageProgressListener {
    /**
     * Is called when image loading progress changed.
     *
     * @param imageUri Image URI
     * @param view     View for image. Can be <b>null</b>.
     * @param current  Downloaded size in bytes
     * @param total    Total size in bytes
     */
    void onProgressUpdate(String imageUri, View view, int current, int total);
}
