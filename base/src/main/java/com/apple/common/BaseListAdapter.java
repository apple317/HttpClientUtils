package com.apple.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class BaseListAdapter<T> extends BaseAdapter {

	public List<T> mListData;

	public LayoutInflater mInflater;

	public BaseListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setData(List<T> data) {
		mListData = data;
		notifyDataSetChanged();
	}

	public List<T> getData() {
		return mListData;
	}

	@Override
	public T getItem(int position) {
		if (mListData == null)
			return null;
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getCount() {
		if (mListData == null)
			return 0;
		return mListData.size();
	}


}
