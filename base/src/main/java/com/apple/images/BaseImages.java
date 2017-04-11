package com.apple.images;

import android.graphics.Bitmap;
import android.widget.ImageView;

/*******************************************************************************
 *
 * 图片库结构解耦
 * 公共扩张借口
 * 作用任何一个图片组件可以继承并扩展
 *
 *******************************************************************************/
public interface BaseImages{

    /**
     *
     * @param uri       Image URI (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")
     * @param imageView {@link ImageView} which should display image
     * @throws IllegalArgumentException if passed <b>imageView</b> is null
     */
    public void displayImage(String uri, ImageView imageView);

    /**
     * Adds display image task to execution pool. Image will be set to ImageView when it's turn.<br />
     * @param uri       Image URI (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")
     * @param imageView {@link ImageView} which should display image
     * @param options   {@linkplain  AppImageOptions} for image
     *                  decoding and displaying. If <b>null</b> - default display image options
     *                 will be used.
     * @throws IllegalArgumentException if passed <b>imageView</b> is null
     */
     void displayImage(String uri, ImageView imageView, AppImageOptions options);

    /**
     *
     * @param uri       Image URI (i.e. "http://site.com/image.png", "file:///mnt/sdcard/image.png")
     * @param imageView {@link ImageView} which should display image
     * @param listener  {@linkplain BaseImageLoadingListener Listener} for image loading process. Listener fires events on
     *                  UI thread if this method is called on UI thread.
     * @throws IllegalArgumentException if passed <b>imageView</b> is null
     */
     void displayImage(String uri, ImageView imageView, AppImageOptions options, BaseImageLoadingListener listener);
    /**
     * Adds load image task to execution pool. Image will be returned with
     * @param         ( "http://site.com/image.png", "file:///mnt/sdcard/image.png")
     * @param  {@linkplain BaseImageLoadingListener Listener} for image loading process. Listener fires events on UI
     *                 thread if this method is called on UI thread.
     */
     void loadImage(String uri, AppImageOptions options, BaseImageLoadingListener listener);

     void loadImage(String uri, BaseImageLoadingListener listener);
     Bitmap loadImage(String uri);
    void initImagesHelper();
    long getImageSize();
    void clearImages();
}
