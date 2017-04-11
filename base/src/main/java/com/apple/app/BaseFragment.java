package com.apple.app;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apple.butterknife.ViewBinder;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.listener.HttpCallback;
import com.apple.tool.Utility;


public abstract class BaseFragment extends Fragment {
	protected boolean bQuit;

	protected BaseActivity mAct;

	public boolean bBlank;
	public FraHttpClick httpClick;

	public FragmentClick click;
	public Dialog dialog;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof BaseActivity)
			mAct = (BaseActivity) activity;
		click = new FragmentClick();
		httpClick=new FraHttpClick();
		dialog = Utility.getDialog(getActivity());
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

	public class FraHttpClick extends HttpCallback {
		@Override
		public void success(String content, BaseHttpClient client, Object parse) {
			try {
				onSuccess(content,client,parse);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		@Override
		public void error(Throwable error, BaseHttpClient client) {
			try {
				onError(error,client);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}









	/**
	 * @deprecated  初始化布局和控件
	 *
	 */
	public  View initView(LayoutInflater inflater, ViewGroup container,
						  Bundle savedInstanceState){
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
	protected abstract void initData(Bundle savedInstanceState);

	/**
	 * @deprecated 初始化样式
	 */
	protected abstract void initStyle();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (bQuit)
			// ((BaseActivity)getActivity()).quit();
			bBlank = true;
		initData(savedInstanceState);
		initLisitener();
		initStyle();
	}



	public void entry() {

	}

	/**
	 * 初始化参数
	 */
	public void initParameter() {
	}



	//设置事件监听
	public void setOnClickListener(int id) {
		if (click == null)
			click = new FragmentClick();
		this.getView().findViewById(id).setOnClickListener(click);
	}

	public class FragmentClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			treatClickEvent(id);
			treatClickEvent(v);
		}
	}

	/**
	 * 事件返回
	 * @param id
	 */
	public void treatClickEvent(int id) {

	}

	/**
	 * 事件返回
	 * @param v 视图
	 */
	public void treatClickEvent(View v) {

	}


	public void onResume() {
        super.onResume();
    }
	public void onPause() {
        super.onPause();
    }




}
