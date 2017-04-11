package com.base.apple.demo.search.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apple.common.BaseListAdapter;
import com.apple.http.common.BaseHttpClient;
import com.apple.http.listener.HttpCallback;
import com.base.apple.demo.R;
import com.base.apple.demo.api.LoginApiStores;
import com.base.apple.demo.common.AppBaseParams;
import com.base.apple.demo.search.bean.UserBean;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class UserListAdapter extends BaseListAdapter {


    Context mContext;
    public UserListAdapter(Context context) {
        super(context);
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item2, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserBean.ItemsBean itemsBean=(UserBean.ItemsBean) getItem(position);
        viewHolder.txtName.setText(itemsBean.getLogin());
        Picasso.with(viewHolder.icon_avatar.getContext())
                .load(itemsBean.getAvatar_url())
                .into(viewHolder.icon_avatar);
        viewHolder.txtLanguage.setText(itemsBean.getLanguage()+"");


        return  convertView;
    }

    static class ViewHolder {
        @Bind(R.id.icon_avatar)
        ImageView icon_avatar;
        @Bind(R.id.txt_name)
        TextView txtName;
        @Bind(R.id.txt_language)
        TextView txtLanguage;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
