package com.base.apple.demo.highlow.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.base.apple.demo.highlow.viewmodel.HighlowViewModel;
import com.base.apple.demo.main.view.MainActivity;
import com.base.apple.demo.sound.viewmodel.SoundViewModel;


public class HighlowFragement extends Fragment {


    HighlowViewModel highlowViewModel;

    public HighlowFragement() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highlowViewModel=new HighlowViewModel((MainActivity)getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return highlowViewModel.initView(inflater, container, savedInstanceState);
    }
}
