package com.apple.tool;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.apple.images.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * 图片处理工具类
 *
 * @author hushaoping
 * @version 1.0.0
 *          Date: 13-3-20
 *          Time: 上午10:11
 */


@SuppressLint("NewApi")
public class BitmapUtils {

    /**
     * bitmap with alpha channel
     */
    public static final String LOG_TAG = "BitmapUtils";
    private static final String ALPHA_BITMAP_MIME_TYPE_END = "png";

    private static final int DEAFULT_QUALITY = 100;
    private BitmapFactory.Options mInitBitmapFactoryOptions;
    private BitmapFactory.Options mBitmapFactoryOptions;
    private boolean mPreferUseRgb8888 = SDKVersionUtils.hasHoneycombMR2();
    private boolean mPreDecoded = false;

    /**
     * 构造方法
     *
     * @param bitmapFactoryOptions 解码参数
     */
    public BitmapUtils(BitmapFactory.Options bitmapFactoryOptions) {
        if (bitmapFactoryOptions != null) {
            mInitBitmapFactoryOptions = bitmapFactoryOptions;
        } else {
            mInitBitmapFactoryOptions = createDefaultOptions();
        }
    }

    /**
     * 默认的构造方法
     */
    public BitmapUtils() {
        mInitBitmapFactoryOptions = createDefaultOptions();
    }

    /**
     * set prefer use rgb 8888
     *
     * @param enable enable
     */
    public void setPreferUseRgb8888(boolean enable) {
        mPreferUseRgb8888 = enable;
    }

    public BitmapFactory.Options createDefaultOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inDither = mPreferUseRgb8888;
        return options;
    }

    /**
     * @param cancelLastDecode 是否重新new解码参数，通常为false
     */
    private void resetBitmapFactoryOptions(boolean cancelLastDecode) {
        if (cancelLastDecode && mBitmapFactoryOptions != null) {
            mBitmapFactoryOptions.requestCancelDecode();
        }
        mBitmapFactoryOptions = newBitmapOptionsWithInitOptions();
        mPreDecoded = false;
    }

    private BitmapFactory.Options newBitmapOptionsWithInitOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = mInitBitmapFactoryOptions.inDither;
        options.inJustDecodeBounds = mInitBitmapFactoryOptions.inJustDecodeBounds;
        options.inPreferredConfig = mInitBitmapFactoryOptions.inPreferredConfig;
        options.inDensity = mInitBitmapFactoryOptions.inDensity;
        options.inInputShareable = mInitBitmapFactoryOptions.inInputShareable;
        options.inTempStorage = mInitBitmapFactoryOptions.inTempStorage;
        options.inTargetDensity = mInitBitmapFactoryOptions.inTargetDensity;
        options.inScreenDensity = mInitBitmapFactoryOptions.inScreenDensity;
        options.inSampleSize = mInitBitmapFactoryOptions.inSampleSize;
        options.inPurgeable = mInitBitmapFactoryOptions.inPurgeable;
        return options;
    }

    /**
     * get bitmap factory options
     *
     * @return bitmap factory options
     */
    public BitmapFactory.Options getBitmapFactoryOptions() {
        return mInitBitmapFactoryOptions;
    }

    /**
     * 解码图片.
     *
     * @param filePath 文件路径
     * @return 位图
     */
    public Bitmap decodeBitmap(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        resetBitmapFactoryOptions(false);
        if (needResetAlphaChannel()) {
            mBitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, mBitmapFactoryOptions);
            initAlphaChannel(mBitmapFactoryOptions);
            mBitmapFactoryOptions.inJustDecodeBounds = false;
        }
        return processBitmapAlpha(BitmapFactory.decodeFile(filePath, mBitmapFactoryOptions));
    }

    /**
     * 解码图片
     *
     * @param filePath  路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 图片
     */
    public Bitmap decodeBitmap(String filePath, int maxWidth, int maxHeight) {
        return decodeBitmap(filePath, maxWidth, maxHeight, false);
    }

    /**
     * 解码图片
     *
     * @param filePath         路径
     * @param maxWidth         最大宽度
     * @param maxHeight        最大高度
     * @param cancelLastDecode 是否重新new解码参数，通常为false
     * @return 图片
     */
    public Bitmap decodeBitmap(String filePath, int maxWidth, int maxHeight, boolean cancelLastDecode) {
        try {
            if (!TextUtils.isEmpty(filePath) && maxWidth > 0 && maxHeight > 0) {
                resetBitmapFactoryOptions(cancelLastDecode);
                mBitmapFactoryOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, mBitmapFactoryOptions);
                if (initBitmapFactoryOptions(mBitmapFactoryOptions, maxWidth, maxHeight)) {
                    if (needResetAlphaChannel()) {
                        initAlphaChannel(mBitmapFactoryOptions);
                    }
                    return processBitmapAlpha(BitmapFactory.decodeFile(filePath, mBitmapFactoryOptions));
                }
            }
        } catch (OutOfMemoryError error) {
        }

        return null;
    }

    private Bitmap processBitmapAlpha(Bitmap bitmap) {
        // 需要先判断是否为空才能设置，磁盘满或者其它情况都会导致传入的bitmap为空
        if (bitmap == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            bitmap.setHasAlpha(!mayNotHasAlphaChannel(mBitmapFactoryOptions));
        }
        return bitmap;
    }

    private boolean needResetAlphaChannel() {
        return mBitmapFactoryOptions.inPreferredConfig == Bitmap.Config.ARGB_8888 && !mPreferUseRgb8888;
    }

    /**
     * @param bitmapOptions bitmap options
     * @param maxWidth      max width
     * @param maxHeight     max height
     * @return true if success
     */
    public static boolean initBitmapFactoryOptions(BitmapFactory.Options bitmapOptions, int maxWidth, int maxHeight) {
        if (bitmapOptions.inJustDecodeBounds && bitmapOptions.outHeight > 0 && bitmapOptions.outWidth > 0) {
            if (bitmapOptions.outWidth > (maxWidth << 1) || bitmapOptions.outHeight > (maxHeight << 1)) {
                bitmapOptions.inSampleSize = Math.max(
                        bitmapOptions.outWidth / maxWidth
                        , bitmapOptions.outHeight / maxHeight);
            }
            bitmapOptions.inJustDecodeBounds = false;
            return true;
        }
        return false;
    }

    /**
     * set bitmap options preferred config
     *
     * @param bitmapOptions bitmap options
     * @return true if options is valid
     */
    public static boolean initAlphaChannel(BitmapFactory.Options bitmapOptions) {
        if (!TextUtils.isEmpty(bitmapOptions.outMimeType)) {
            if (mayNotHasAlphaChannel(bitmapOptions)) {
                bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmapOptions.inDither = false;
            } else {
                bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
            }
            return true;
        }
        return false;
    }

    /**
     * FavoriteIdLoader if bitmap may not contain alpha channel
     *
     * @param bitmapFactoryOptions bitmap factory option, which has decoded
     * @return true if yes
     */
    public static boolean mayNotHasAlphaChannel(BitmapFactory.Options bitmapFactoryOptions) {
        String outMimeType = bitmapFactoryOptions.outMimeType;
        return !TextUtils.isEmpty(outMimeType)
                && !outMimeType.toLowerCase(Locale.US).endsWith(ALPHA_BITMAP_MIME_TYPE_END);
    }

    /**
     * 解码资源图片
     *
     * @param res   资源
     * @param resId 资源id
     * @return bitmap
     */
    public Bitmap decodeBitmap(Resources res, int resId) {
        if (resId != 0) {
            resetBitmapFactoryOptions(false);
            if (needResetAlphaChannel()) {
                mBitmapFactoryOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(res, resId, mBitmapFactoryOptions);
                initAlphaChannel(mBitmapFactoryOptions);
                mBitmapFactoryOptions.inJustDecodeBounds = false;
            }
            return processBitmapAlpha(BitmapFactory.decodeResource(res, resId, mBitmapFactoryOptions));
        }
        return null;
    }

    /**
     * 解码资源图片
     *
     * @param res       资源
     * @param resId     资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public Bitmap decodeBitmap(Resources res, int resId, int maxWidth, int maxHeight) {
        if (resId != 0 && maxHeight > 0 && maxWidth > 0) {
            resetBitmapFactoryOptions(false);
            mBitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, mBitmapFactoryOptions);
            if (initBitmapFactoryOptions(mBitmapFactoryOptions, maxWidth, maxHeight)) {
                if (needResetAlphaChannel()) {
                    initAlphaChannel(mBitmapFactoryOptions);
                }
                return processBitmapAlpha(BitmapFactory.decodeResource(res, resId, mBitmapFactoryOptions));
            }
        }
        return null;
    }

    /**
     * 从byte中解码
     *
     * @param bytes  数据
     * @param offset 偏移
     * @param length 长度
     * @return bitmap
     */
    public Bitmap decodeBitmap(byte[] bytes, int offset, int length) {
        if (bytes != null) {
            resetBitmapFactoryOptions(false);
            if (needResetAlphaChannel()) {
                mBitmapFactoryOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bytes, offset, length, mBitmapFactoryOptions);
                initAlphaChannel(mBitmapFactoryOptions);
                mBitmapFactoryOptions.inJustDecodeBounds = false;
            }
            return processBitmapAlpha(BitmapFactory.decodeByteArray(bytes, offset, length, mBitmapFactoryOptions));
        }
        return null;
    }

    /**
     * 解码图片
     *
     * @param bytes     数据
     * @param offset    偏移
     * @param length    长度
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 图片
     */
    public Bitmap decodeBitmap(byte[] bytes, int offset, int length, int maxWidth, int maxHeight) {
        if (bytes != null && maxHeight > 0 && maxWidth > 0) {
            resetBitmapFactoryOptions(false);
            mBitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, offset, length, mBitmapFactoryOptions);
            if (initBitmapFactoryOptions(mBitmapFactoryOptions, maxWidth, maxHeight)) {
                if (needResetAlphaChannel()) {
                    initAlphaChannel(mBitmapFactoryOptions);
                }
                return processBitmapAlpha(BitmapFactory.decodeByteArray(bytes, offset, length, mBitmapFactoryOptions));
            }
        }
        return null;
    }

    /**
     * 解码图片
     *
     * @param inputStream 输入流
     * @return 输出
     */
    public Bitmap decodeBitmap(InputStream inputStream) {
        if (inputStream != null) {
            if (!mPreDecoded) {
                resetBitmapFactoryOptions(false);
                if (needResetAlphaChannel()) {
                    mBitmapFactoryOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream, null, mBitmapFactoryOptions);
                    initAlphaChannel(mBitmapFactoryOptions);
                    mBitmapFactoryOptions.inJustDecodeBounds = false;
                }
            }
            return processBitmapAlpha(BitmapFactory.decodeStream(inputStream, null, mBitmapFactoryOptions));
        }
        return null;
    }

    /**
     * 预解码图片，流数据需要重新打开以读取图片
     *
     * @param inputStream 输入流
     * @param maxWidth    最大宽度
     * @param maxHeight   最大高度
     */
    public void preDecodeBitmap(InputStream inputStream, int maxWidth, int maxHeight) {
        if (inputStream != null && maxHeight > 0 && maxWidth > 0) {
            resetBitmapFactoryOptions(false);
            mBitmapFactoryOptions.inJustDecodeBounds = true;
            try {
                inputStream.mark(inputStream.available());
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapFactory.decodeStream(inputStream, null, mBitmapFactoryOptions);
            if (initBitmapFactoryOptions(mBitmapFactoryOptions, maxWidth, maxHeight) && needResetAlphaChannel()) {
                initAlphaChannel(mBitmapFactoryOptions);
            }
            mPreDecoded = true;
        }
    }

    /**
     * 解码图片为方形图片.
     *
     * @param filePath         文件路径
     * @param squareLength     长度
     * @param cancelLastDecode 是否重新new解码参数，通常为false
     * @return 位图
     */
    public Bitmap decodeFileToSquareBitmap(String filePath, int squareLength, boolean cancelLastDecode) {
//		TTLog.e(LOG_TAG, String.format("decodeFileToSquareBitmap ThreadId=%d %s squareLength=%d"
//				, Thread.currentThread().getId(), filePath, squareLength));

        Bitmap decodedBitmap = decodeBitmap(filePath, squareLength, squareLength, cancelLastDecode);
        if (decodedBitmap != null) {
            Bitmap bitmap = cropBitmapToSquare(decodedBitmap, squareLength);
            if (decodedBitmap != bitmap) {
                decodedBitmap.recycle();
            } else {
            }
            return bitmap;
        }
        return null;
    }

    /**
     * 裁剪图片为方形图片
     *
     * @param bitmap       图片
     * @param squareLength 长度
     * @return 位图
     */
    public static Bitmap cropBitmapToSquare(Bitmap bitmap, int squareLength) {
        if (bitmap != null) {
            int imageSquareLength = Math.min(bitmap.getWidth(), bitmap.getHeight());
            int croppedLength = Math.min(imageSquareLength, squareLength);
            float scale = (float) croppedLength / imageSquareLength;
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);

            try {
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, imageSquareLength, imageSquareLength, matrix, true);
                if (SDKVersionUtils.hasHoneycombMR1()) {
                    newBitmap.setHasAlpha(bitmap.hasAlpha());
                }
                return newBitmap;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 裁剪图片为方形图片
     *
     * @param bitmapPath   图片路径
     * @param squareLength 长度
     * @return 位图
     */
    public static Bitmap cropBitmapToSquare(String bitmapPath, int squareLength) {
        Bitmap bitmap = decodeSampledBitmapFromFile(bitmapPath, squareLength, squareLength);
        return cropBitmapToSquare(bitmap, squareLength);
    }


    /**
     * 从资源文件中解码图片, 会根据需要拉伸或压缩图片
     *
     * @param res       Resources
     * @param resId     resId
     * @param reqWidth  需要显示的图片的宽度
     * @param reqHeight 需要显示的图片的高度
     * @return The decoded bitmap, or null if the image data could not be
     * decoded
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 从资源文件中解码图片, 会根据需要拉伸或压缩图片
     *
     * @param filePath  文件全路径
     * @param reqWidth  需要显示的图片的宽度
     * @param reqHeight 需要显示的图片的高度
     * @return The decoded bitmap, or null if the image data could not be
     * decoded
     */
    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inInputShareable = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        try {
            return BitmapFactory.decodeFile(filePath, options);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据要求的图片大小，和图片原来的小计算图片采样大小
     *
     * @param options   option
     * @param reqWidth  要求输出的图片宽度
     * @param reqHeight 要求输出的图片高度
     * @return 采样值
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if ((reqWidth > 0 && reqHeight > 0) && (height > reqHeight || width > reqWidth)) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return 0 == inSampleSize ? 1 : inSampleSize;
    }

    /**
     * 获取bitmap占用空间大小
     *
     * @param bitmap 位图实例
     * @return 位图占用空间大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (bitmap == null) {
            throw new IllegalArgumentException("bitmap must be not null!");
        }

        return SDKVersionUtils.hasHoneycombMR1() ? bitmap.getByteCount() : (bitmap.getRowBytes() * bitmap.getHeight());
    }

    /**
     * 保存bitMap图片
     *
     * @param bitmap   Bitmap对象
     * @param savePath 图片路径
     */

    public static void saveBitmap(final Bitmap bitmap, final String savePath) {
        FileUtils.delete(savePath);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(savePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, DEAFULT_QUALITY, fileOutputStream);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保持等比例缩放图片
     * 当view宽度较大时，垂直方向顶部不剪裁，只剪裁下面，当view高度较大时，水平方向居中保留，裁剪左右。
     *
     * @param imageView image view
     */
    public static void amendMatrixForCenterCrop(ImageView imageView) {
        if (imageView == null) {
            return;
        }

        Drawable drawable = imageView.getDrawable();
        int drawableHeight = drawable != null ? drawable.getIntrinsicHeight() : 0;
        int drawableWidth = drawable != null ? drawable.getIntrinsicWidth() : 0;
        int viewWidth = imageView.getWidth();
        int viewHeight = imageView.getHeight();
        if (drawableHeight <= 0 || drawableWidth <= 0 || viewWidth <= 0 || viewHeight <= 0) {
            return;
        }

        float horizontalScaleRatio = 1.0f * viewWidth / drawableWidth;
        float verticalScaleRatio = 1.0f * viewHeight / drawableHeight;
        if (verticalScaleRatio >= horizontalScaleRatio) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            float scaleRatio = Math.max(horizontalScaleRatio, verticalScaleRatio);
            Matrix matrix = new Matrix();
            matrix.postScale(scaleRatio, scaleRatio);
            imageView.setImageMatrix(matrix);
        }
    }

    /**
     * 以FitCenter方式生成图片
     *
     * @param srcBitmap 原始图片
     * @param dstWidth  目标图片宽度
     * @param dstHeight 目标图片高度
     * @param tryRecycleSource 是否尽量回收原图
     * @return 如果创建不成功返回原图
     */
    public static Bitmap createFitXYBitmap(Bitmap srcBitmap, int dstWidth, int dstHeight, boolean tryRecycleSource) {
        if (srcBitmap == null || dstWidth <= 0 || dstHeight <= 0) {
            return srcBitmap;
        }

        Bitmap dstBitmap = srcBitmap;
        try {
            dstBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, true);
            if (dstBitmap != srcBitmap && tryRecycleSource) {
                srcBitmap.recycle();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return dstBitmap;
    }

    /**
     * 以FitCenter方式生成图片
     *
     * @param srcBitmap        原始图片
     * @param dstWidth         目标图片宽度
     * @param dstHeight        目标图片高度
     * @param tryRecycleSource 是否尽量回收原图
     * @return 如果创建不成功返回原图
     */
    public static Bitmap createFitCenterBitmap(Bitmap srcBitmap, int dstWidth, int dstHeight, boolean tryRecycleSource) {
        if (srcBitmap == null || dstWidth <= 0 || dstHeight <= 0) {
            return srcBitmap;
        }

        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int newWidth = srcWidth;
        int newHeight = srcHeight;

        if (newWidth > dstWidth) {
            newHeight = dstWidth * newHeight / newWidth;
            newWidth = dstWidth;
        }

        if (newHeight > dstHeight) {
            newWidth = dstHeight * newWidth / newHeight;
            newHeight = dstHeight;
        }

        return createFitXYBitmap(srcBitmap, newWidth, newHeight, tryRecycleSource);
    }

    /**
     * 以CenterCrop方式生成图片
     *
     * @param srcBitmap        原始图片
     * @param dstWidth         目标图片宽度
     * @param dstHeight        目标图片高度
     * @param tryRecycleSource 是否尽量回收原图
     * @return 如果创建不成功返回原图
     */
    public static Bitmap createCenterCropBitmap(Bitmap srcBitmap, int dstWidth, int dstHeight, boolean tryRecycleSource) {
        if (srcBitmap == null || dstWidth == 0 || dstHeight == 0) {
            return srcBitmap;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        Bitmap dstBitmap = srcBitmap;
        try {
            if ((dstHeight / dstWidth) - (srcHeight / srcWidth) > 0) {
                int newWidth = (srcHeight * dstWidth) / dstHeight;
                dstBitmap = Bitmap.createBitmap(srcBitmap, (srcWidth - newWidth) / 2, 0, newWidth, srcHeight);
            } else {
                int newHeight = (dstHeight * srcWidth) / dstWidth;
                dstBitmap = Bitmap.createBitmap(srcBitmap, 0, (srcHeight - newHeight) / 2, srcWidth, newHeight);
            }
            if (dstBitmap != srcBitmap && tryRecycleSource) {
                srcBitmap.recycle();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return dstBitmap;
    }
}
