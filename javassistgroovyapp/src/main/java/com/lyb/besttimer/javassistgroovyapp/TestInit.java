package com.lyb.besttimer.javassistgroovyapp;

import android.content.Context;
import android.widget.Toast;

import com.lyb.besttimer.annotation_bean.BindInit;
import com.lyb.besttimer.annotation_bean.IAppInit;

@BindInit
public class TestInit implements IAppInit<Context> {
    @Override
    public void init(Context applicationContext) {
        Toast.makeText(applicationContext, "appinit 666", Toast.LENGTH_LONG).show();
    }
}