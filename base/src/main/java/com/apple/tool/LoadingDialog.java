package com.apple.tool;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.market.R;


/**
 * Created by zhangliang on 16/4/21. 加载Dialog
 */
public class LoadingDialog extends Dialog {



    public LoadingDialog(Context context) {
        super(context,  R.style.loading);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        this.setCanceledOnTouchOutside(false);
    }



    @Override
    protected void onStop() {
        super.onStop();
        this.dismiss();
    }
}
