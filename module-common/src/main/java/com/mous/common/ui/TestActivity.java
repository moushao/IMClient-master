package com.mous.common.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.mous.common.R;

import org.androidannotations.annotations.EActivity;

import component.baselib.base.BaseActivity;
import component.baselib.mvp.BasePresenter;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initWidgetAndEvent() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
