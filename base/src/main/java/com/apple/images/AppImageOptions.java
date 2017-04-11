/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.apple.images;

import android.graphics.drawable.Drawable;


public class AppImageOptions {

    public int imageResOnLoading = 0;
    public int imageResForEmptyUri = 0;
    public int imageResOnFail = 0;
    public Drawable imageOnLoading = null;
    public Drawable imageForEmptyUri = null;
    public Drawable imageOnFail = null;
    public boolean hasPic = false;
    public int width=0;
    public int height=0;
    public int rotation=180;
    public AppImageOptions() {
    }

    /**
     * Stub image will be displayed in
     * image aware view during image loading
     *
     */
    public AppImageOptions showHasPic(boolean show) {
        this.hasPic = show;
        return this;
    }

    public AppImageOptions setHeight(int height) {
        this.height = height;
        return this;
    }

    public AppImageOptions setWidth(int width) {
        this.width = width;
        return this;
    }


    public AppImageOptions setRotation(int rotation) {
        this.rotation = rotation;
        return this;
    }

    public int getRotation() {
        return rotation;
    }

    /**
     * Incoming image will be displayed
     * image aware view} during image loading
     *
     * @param imageRes Image resource
     */
    public AppImageOptions showImageOnLoading(int imageRes) {
        imageResOnLoading = imageRes;
        return this;
    }

    /**
     * Incoming drawable will be displayed in
     * image aware view} during image loading.
     * This option will be ignored if {@link AppImageOptions#showImageOnLoading(int)} is set.
     */
    public AppImageOptions showImageOnLoading(Drawable drawable) {
        imageOnLoading = drawable;
        return this;
    }

    /**
     * Incoming image will be displayed
     * image aware view} if empty URI (null or empty
     * string) will be passed to <b>ImageLoader.displayImage(...)</b> method.
     *
     * @param imageRes Image resource
     */
    public AppImageOptions showImageForEmptyUri(int imageRes) {
        imageResForEmptyUri = imageRes;
        return this;
    }

    /**
     * Incoming drawable will be displayed in
     * image aware view} if empty URI (null or empty
     * string) will be passed to <b>ImageLoader.displayImage(...)</b> method.
     * This option will be ignored if {@link AppImageOptions#showImageForEmptyUri(int)} is set.
     */
    public AppImageOptions showImageForEmptyUri(Drawable drawable) {
        imageForEmptyUri = drawable;
        return this;
    }

    /**
     * Incoming image will be displayed in
     * image aware view} if some error occurs during
     * requested image loading/decoding.
     *
     * @param imageRes Image resource
     */
    public AppImageOptions showImageOnFail(int imageRes) {
        imageResOnFail = imageRes;
        return this;
    }

    /**
     * Incoming drawable will be displayed
     * image aware view} if some error occurs during
     * requested image loading/decoding.
     * This option will be ignored if {@link AppImageOptions#showImageOnFail(int)} is set.
     */
    public AppImageOptions showImageOnFail(Drawable drawable) {
        imageOnFail = drawable;
        return this;
    }


    /** Builds configured {@link AppImageOptions} object */
    public AppImageOptions build() {
        return new AppImageOptions();
    }
}
