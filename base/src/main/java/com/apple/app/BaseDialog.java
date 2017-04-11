/**
 * 公共父类弹出框
 */
package com.apple.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.apple.butterknife.ViewBinder;
import com.apple.http.listener.HttpCallback;
import com.apple.http.common.BaseHttpClient;


/**
 * @author hushaoping
 *
 */
public abstract class BaseDialog extends Dialog  {

	public DialogClick click;
	Activity  activity;
	public HttpClick httpClick;

	public BaseDialog(Activity mActivity, int style) {
		super(mActivity, style);
		activity = mActivity;
		click = new DialogClick();
		httpClick=new HttpClick();
	}

	/** 设置控件点击监听器 */
	public void setOnClickListener(int id) {
		if (click == null)
			click = new DialogClick();
		this.getWindow().findViewById(id).setOnClickListener(click);
	}

	private class DialogClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			treatClickEvent(id);
			treatClickEvent(v);
		}
	}

	/**
	 * @deprecated 成功返回
	 *
	 */
	protected abstract void onSuccess(String content, BaseHttpClient client, Object parse) ;

	/**
	 * @deprecated 识别返回
	 */
	protected abstract void  onError(Throwable error, BaseHttpClient client);

	public class HttpClick extends HttpCallback {
		@Override
		public void success(String content, BaseHttpClient client, Object parse) {
			onSuccess(content,client,parse);
		}

		@Override
		public void error(Throwable error, BaseHttpClient client) {
			onError(error,client);
		}
	}
	/** 处理点击事件 */
	public void treatClickEvent(int id) {

	}

	/** 处理点击事件 */
	public void treatClickEvent(View v) {

	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	/**
	 * @deprecated  初始化布局和控件
	 *
	 * @param bundle
	 */
	public  View initView(Bundle bundle){
		View view=new ViewBinder().initView(this);
		return  view;
	}


	/**
	 * 初始化监听
	 */
	protected abstract void initLisitener();

	/**
	 * 初始化数据
	 */
	protected abstract void initData(Activity activity,Bundle savedInstanceState);
	/**
	 * @deprecated 初始化样式
	 */
	protected abstract void initStyle();

	/**
	 * 显示对话框
	 * */
	public void goShow() {
		this.setCanceledOnTouchOutside(true);
		getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		show();
	}






}
