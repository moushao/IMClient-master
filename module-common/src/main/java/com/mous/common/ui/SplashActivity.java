package com.mous.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mous.common.R;

import component.baselib.arouter.RouterCenter;
import component.baselib.base.BaseActivity;
import component.baselib.common.AppManager;
import component.baselib.mvp.BasePresenter;
import component.baselib.permissions.RxPermissions;
import component.data.common.Constants;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 类名: {@link SplashActivity}
 * <br/> 功能描述:启动页
 * <br/> 作者: MouShao
 * <br/> 时间:2019-08-14
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class SplashActivity extends BaseActivity {
    public final static String TAG = "SplashActivity";
    private RxPermissions rxPermissions;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        mContext = this;
    }

    @Override
    protected void initWidgetAndEvent() {
        requestPermissions();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private void requestPermissions() {
        this.rxPermissions = new RxPermissions(this);
        this.rxPermissions.request(new String[]{
                "android.permission.CALL_PHONE",
                "android.permission.CAMERA",
                "android.permission.RECORD_AUDIO",
                "android.permission.READ_PHONE_STATE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WAKE_LOCK",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_COARSE_LOCATION"})
                .subscribe(new Observer<Boolean>() {
                    public void onSubscribe(Disposable d) {

                    }

                    public void onNext(Boolean aBoolean) {
                        if (aBoolean.booleanValue()) {
                            RouterCenter.toCommonLogin(TAG);
                            finish();
                        } else {
                            showToast("权限申请失败");
                            AppManager.getAppManager().AppExit(mContext);
                        }

                    }

                    public void onError(Throwable e) {
                    }

                    public void onComplete() {

                    }
                });
    }

    public static void startAction(Context mContext, String from) {
        Intent itt = new Intent(mContext, SplashActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FROM, from);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }
}
