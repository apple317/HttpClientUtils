package com.base.apple.demo.delay.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.apple.demo.R;
import com.base.apple.demo.delay.viewmodel.DelayViewModel;


public class DelayFragement extends Fragment {

    DelayViewModel delayViewModel;
    public DelayFragement() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delayViewModel=new DelayViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return delayViewModel.initView(inflater, container, savedInstanceState);
    }

}
