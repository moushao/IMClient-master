package component.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;


import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import component.baselib.common.AppManager;
import component.baselib.R;
import component.baselib.mvp.BasePresenter;
import component.baselib.mvp.BaseView;
import component.baselib.utils.CleanLeakUtils;
import component.baselib.utils.ToastUtils;
import component.baselib.widget.dialog.AlertDialog;
import component.baselib.widget.dialog.ProgressDialog;


public abstract class BaseActivity<T extends BasePresenter<BaseView>> extends AppCompatActivity implements IBase,
        BaseView {
    public BasePresenter mPresenter;

    protected InputMethodManager inputManager;

    private ProgressDialog progressDialog;
    private Unbinder unbinder;
    protected boolean isRegisterEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        AppManager.getAppManager().addActivity(this);
        setContentView(getLayoutId());
        setStatusBarColor();
        unbinder = ButterKnife.bind(this);
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.attach((BaseView) this);
        }
        onSaveState(savedInstanceState);
        initData();
        initWidgetAndEvent();
        registerEventBus();
    }


    public void onSaveState(Bundle savedInstanceState) {
    }

    /**
     * 获取布局
     */
    protected abstract int getLayoutId();


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置控件并设置监听
     */
    protected abstract void initWidgetAndEvent();


    protected void setStatusBarColor() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.action_sheet_blue), 0);
    }

    public void showBaseLoading(String string) {
        progressDialog = ProgressDialog.getInstance(this);
        progressDialog.setMessage(TextUtils.isEmpty(string) ? "加载中..." : string);
        progressDialog.show();
    }


    public void disBaseLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void destroyProgressDialog() {
        if (progressDialog != null) {
            progressDialog.destroy();
        }
    }

    public void showToast(String message) {
        ToastUtils.showToast(BaseActivity.this, message);
    }


    public void showToast(int ResID) {
        ToastUtils.showToast(BaseActivity.this, ResID);
    }

    public void showBaseMessageDialog(final String message) {
        if (TextUtils.isEmpty(message))
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog(BaseActivity.this).setWidthRatio(0.7f).builder()
                        .hideTitleLayout().setMsg(message)
                        .setPositiveButton(("确 定"), null)
                        .setMessageGravity(Gravity.CENTER).show();
            }
        });
    }

    private void registerEventBus() {
        if (isRegisterEvent && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unRegisterEventBus() {
        if (isRegisterEvent) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.detachView();
            mPresenter = null;
        }
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
        destroyProgressDialog();
        ToastUtils.release();
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        unRegisterEventBus();
        super.onDestroy();
    }


    @Override
    public void showLoading(String str) {
        showBaseLoading(str);
    }

    @Override
    public void disLoading() {
        disBaseLoading();
    }

    @Override
    public void onFailed(String message) {
        showBaseMessageDialog(message);
    }

}
