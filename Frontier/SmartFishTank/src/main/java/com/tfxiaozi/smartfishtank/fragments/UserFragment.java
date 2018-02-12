package com.tfxiaozi.smartfishtank.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tfxiaozi.smartfishtank.R;
import com.tfxiaozi.smartfishtank.widget.LabelLayout;

/**
 * Created by dongqiang on 2016/9/11.
 */
public class UserFragment extends Fragment implements View.OnClickListener{

    private LabelLayout llBuy;
    private LabelLayout llCloud;
    private LabelLayout llFaq;
    private LabelLayout llAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.user_fragment, container, false);
        initViews(view);
        initEvents();
        return view;
    }

    private void initViews(View view) {
    }

    private void initEvents() {
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {



        }
    }
}
