package com.base.apple.demo.system.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.apple.demo.system.viewmodel.SystemViewModel;


public class SystemFragement extends Fragment {


    SystemViewModel systemViewModel;

    public SystemFragement() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        systemViewModel=new SystemViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return systemViewModel.initView(inflater, container, savedInstanceState);
    }
}
