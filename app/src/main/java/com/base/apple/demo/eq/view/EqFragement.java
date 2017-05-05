package com.base.apple.demo.eq.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.apple.demo.eq.viewmodel.EqViewModel;
import com.base.apple.demo.highlow.viewmodel.HighlowViewModel;


public class EqFragement extends Fragment {

    EqViewModel eqViewModel;

    public EqFragement() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eqViewModel=new EqViewModel(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return eqViewModel.initView(inflater, container, savedInstanceState);
    }
}
