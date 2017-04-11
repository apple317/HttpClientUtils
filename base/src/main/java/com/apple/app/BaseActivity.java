package com.apple.app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.apple.butterknife.ViewBinder;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.listener.HttpCallback;
import com.apple.tool.Utility;




public abstract class BaseActivity extends AppCompatActivity {

	public Click click;
	private Context mcontext;

	public Dialog dialog;


	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		click = new Click();
		mcontext=this.getApplicationContext();
		dialog = Utility.getDialog(this);
	}





	public void setOnClickListener(int id) {
		if (click == null)
			click = new Click();
		findViewById(id).setOnClickListener(click);
	}

	public class Click implements OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			treatClickEvent(id);
			treatClickEvent(v);
		}
	}






    public void showToast(String s) {
		Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
		toast.show();
	}

	public void treatClickEvent(int id) {

	}

	public void treatClickEvent(View v) {

	}

//	/**
//	 * @deprecated  初始化布局和控件
//	 *
//	 * @param bundle
//	 */
//	public  View initView(Bundle bundle){
//		View view=new ViewBinder().initView(this);
//		return  view;
//	}
//


	/**
	 * @deprecated  初始化界面
	 */
	protected abstract void initView(Bundle bundle);

	/**
	 * @deprecated  初始化监听
	 */
	protected abstract void initLisitener();

	/**
	 * @deprecated  初始化数据
	 */
	protected abstract void initData(Bundle bundle);

	/**
	 * @deprecated 初始化样式
	 */
	protected abstract void initStyle();









	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
