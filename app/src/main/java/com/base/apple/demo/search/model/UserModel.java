package com.base.apple.demo.search.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;

import com.base.apple.demo.BR;
import com.squareup.picasso.Picasso;


/**
 * Created by kelin on 16-3-15.
 */
public class UserModel extends BaseObservable {


    public UserModel() {

    }

    public String id;
    public String name;
    public String language;

    public String avatarlogo;

    public void setTitle(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    public void setLanguage(String language) {
        this.language = language;
        notifyPropertyChanged(BR.language);
    }


    public void setAvatarlogo(String avatarlogo) {
        this.avatarlogo = avatarlogo;
        notifyPropertyChanged(BR.avatarlogo);

    }

    @BindingAdapter({"avatarlogo"})
    public static void loadImage(ImageView iv, String url) {
        Log.e("HU", "loadImage=======" + url);
        Picasso.with(iv.getContext())
                .load(url)
                .into(iv);
    }

    @Bindable
    public String getAvatarlogo() {
        return avatarlogo;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getLanguage() {
        return language;
    }
}


