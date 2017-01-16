package com.lyb.besttimer.androidshare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyb.besttimer.androidshare.R;
import com.lyb.besttimer.pluginwidget.fragment.BaseFragment;

/**
 * Created by Administrator on 2017/1/11.
 */

public class SimpleFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("wawawa", "heihei" + (getActivity() != null));
    }
}
