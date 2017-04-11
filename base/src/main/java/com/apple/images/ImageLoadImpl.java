package com.apple.images;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.io.IOException;


/**
 * AsyncHttpClient async网络申请实现类
 * 如果有新网络tcp请求，就要重新实现一个网络交互类
 *
 * @author 胡少平
 *
 */
public class ImageLoadImpl implements BaseImages {

	private ImageLoader mImageLoader=null;
	Context mContext;
	//单例模式实现
	private static ImageLoadImpl instance;

	public static ImageLoadImpl getInstance(Context context) {
		if (instance == null)
			instance = new ImageLoadImpl(context);
		return instance;
	}

	private ImageLoadImpl(Context context) {
		mContext=context;
		mImageLoader = ImageLoader.getInstance();
	}

	@Override
	public void displayImage(String uri, ImageView imageView) {
		displayImage(uri,imageView,null);
	}

	@Override
	public void displayImage(String uri, ImageView imageView, AppImageOptions options) {
		displayImage(uri,imageView,options,null);
	}


	@Override
	public void displayImage(String uri, ImageView imageView,AppImageOptions options, final BaseImageLoadingListener listener) {
		if(uri==null||uri.trim().equals("")||imageView==null){
			return;
		}
//		Bitmap bmp =mImageLoader.getMemoryCache().get(uri);
//		if (bmp != null && !bmp.isRecycled()) {
//			imageView.setImageBitmap(bmp);
//			return;
//		}
//		File bmp1 =mImageLoader.getDiskCache().get(uri);
//		if (bmp1 != null&&bmp1.exists()) {
//			try{
//				BitmapFactory.Options bitOptions = new BitmapFactory.Options();
//				bitOptions.inJustDecodeBounds = false;
//				bitOptions.inPreferredConfig = Bitmap.Config.RGB_565;
//				bitOptions.inDither = true;
//				Bitmap bitmap=BitmapFactory.decodeFile(bmp1.getAbsolutePath(),bitOptions);
//				imageView.setImageBitmap(bitmap);
//				return;
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//		}
		DisplayImageOptions displayImageOptions=null;
		if(options!=null&&options.imageResOnLoading!=0){
			displayImageOptions= new DisplayImageOptions.Builder()
					.showImageOnLoading(options.imageResOnLoading)
					.showImageForEmptyUri(options.imageResForEmptyUri==0?options.imageResOnLoading:options.imageResOnFail)
					.showImageOnFail(options.imageResOnFail==0?options.imageResOnLoading:options.imageResOnLoading)
					.cacheInMemory(true)
					.cacheOnDisk(true).build();
		}else{
			displayImageOptions= new DisplayImageOptions.Builder()
					.cacheInMemory(true)
					.cacheOnDisk(true).build();
		}
		if(listener==null){
			mImageLoader.displayImage(uri, imageView, displayImageOptions,null);
		}else{
			ImageLoadingListener listener1=new ImageLoadingListener(){
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					listener.onLoadingStarted(imageUri, view);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					listener.onLoadingFailed(imageUri, view,0);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					listener.onLoadingComplete(imageUri, view, loadedImage);
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					listener.onLoadingCancelled(imageUri,view);
				}
			};
			mImageLoader.displayImage(uri, imageView, displayImageOptions,listener1);
		}
	}

	@Override
	public void loadImage(String uri, AppImageOptions options, final  BaseImageLoadingListener listener) {
		DisplayImageOptions displayImageOptions=null;
		if(options!=null&&options.imageResOnLoading!=0){
			displayImageOptions= new DisplayImageOptions.Builder()
					.showImageOnLoading(options.imageResOnLoading)
					.showImageForEmptyUri(options.imageResForEmptyUri==0?options.imageResOnLoading:options.imageResOnFail)
					.showImageOnFail(options.imageResOnFail==0?options.imageResOnLoading:options.imageResOnLoading)
					.cacheInMemory(true)
					.cacheOnDisk(true).build();
		}else{
			displayImageOptions= new DisplayImageOptions.Builder()
					.cacheInMemory(true)
					.cacheOnDisk(true).build();
		}
		ImageLoadingListener listener1=new ImageLoadingListener(){
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				if(listener!=null)
					listener.onLoadingStarted(imageUri, view);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				if(listener!=null)
					listener.onLoadingFailed(imageUri, view,0);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if(listener!=null)
					listener.onLoadingComplete(imageUri, view, loadedImage);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				if(listener!=null)
					listener.onLoadingCancelled(imageUri,view);
			}
		};
		if(listener==null){
			mImageLoader.loadImage(uri, displayImageOptions,null);
		}else{
			mImageLoader.loadImage(uri, displayImageOptions,listener1);
		}
	}

	@Override
	public void loadImage(String uri, BaseImageLoadingListener listener) {
		loadImage(uri,null,listener);
	}

	@Override
	public Bitmap loadImage(String uri) {
		return mImageLoader.loadImageSync(uri);
	}


	private BitmapFactory.Options getBitmapOption(int inSampleSize){
		System.gc();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inSampleSize = inSampleSize;
		return options;
	}

	@Override
	public void initImagesHelper() {
		File cacheDir =StorageUtils.getOwnCacheDirectory(mContext, "jucaicat/cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(mContext)
				.memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(3)//线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY -2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100) //缓存的文件数量
				.discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
				.defaultDisplayImageOptions(new DisplayImageOptions.Builder()
						.cacheInMemory(true)
						.cacheOnDisk(true).build())
				.imageDownloader(new BaseImageDownloader(mContext,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
				.writeDebugLogs() // Remove for releaseapp
				.build();//开始构建
		ImageLoader.getInstance().init(config);
	}

	@Override
	public long getImageSize() {
		return   new FileUtils().getFileSize(new File(
				StorageUtils.getCacheDirectory(mContext).getPath()));
	}

	@Override
	public void clearImages() {
		new Thread() {
			@Override
			public void run() {
				FileUtils fileUtil = new FileUtils();
				try {
					fileUtil.deleteFolderFile(
							new File(StorageUtils.getCacheDirectory(mContext).getPath())
									.getAbsolutePath(), false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
