package com.mous.im;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import component.baselib.base.BaseActivity;
import component.baselib.mvp.BasePresenter;
import component.data.common.Constants;

/**
 * 类名: {@link MainActivity}
 * <br/> 功能描述:
 * <br/> 作者: MouShao
 * <br/> 时间:
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class MainActivity extends BaseActivity {
    public final static String TAG = "MainActivity";
    private Context mContext;

    public static void startAction(Context mContext, String from) {
        Intent itt = new Intent(mContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FROM, from);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mContext = this;
    }

    @Override
    protected void initWidgetAndEvent() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
