/**
 * 公共父类弹出框
 */
package com.apple.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;

import com.apple.http.listener.HttpCallback;
import com.apple.http.common.BaseHttpClient;



/**
 * @author hushaoping
 *
 */
public abstract class BasePopupWindow  extends PopupWindow {

	DialogClick click;
	Activity act;
	public HttpClick httpClick;

	public BasePopupWindow(Activity activity, Bundle bundle) {
		act = activity;
		click = new DialogClick();
		httpClick=new HttpClick();
		View view=initView(activity);
		setContentView(view);
		initData(activity,bundle);
		initLisitener();
		initStyle(activity);
	}



	/** 设置控件点击监听器 */
	public void setOnClickListener(int id) {
		if (click == null)
			click = new DialogClick();
		getContentView().findViewById(id).setOnClickListener(click);
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



	/**
	 * 初始化布局和控件
	 *
	 * @param
	 */
	protected abstract View initView(Activity activity);

	/**
	 * 初始化监听
	 */
	protected abstract void initLisitener();

	/**
	 * 初始化数据
	 */
	protected abstract void initData(Activity activity,Bundle bundle);

	/**
	 * @deprecated 初始化样式
	 */
	protected abstract void initStyle(Activity activity);



	/**
	 * 显示对话框
	 * */
	public void goShow() {

	}




	@Override
	public void setFocusable(boolean focusable) {
		super.setFocusable(focusable);
	}

	@Override
	public void setOutsideTouchable(boolean touchable) {
		super.setOutsideTouchable(touchable);
	}

}
