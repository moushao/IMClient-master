package component.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import component.baselib.mvp.BasePresenter;
import component.baselib.mvp.BaseView;
import component.baselib.utils.ToastUtils;
import component.baselib.widget.dialog.AlertDialog;
import component.baselib.widget.dialog.ProgressDialog;


/**
 * Created by zwl on 16/9/30.
 */

public abstract class BaseFragment<T extends BasePresenter<BaseView>> extends Fragment implements IBase, BaseView {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Context mActivity;
    //是否可见状态
    protected boolean isVisible;
    //View已经初始化完成
    private boolean isPrepared;
    //是否第一次加载完
    private boolean isFirstLoad = true;

    protected BasePresenter mPresenter;
    private View mView;
    //    private LoadProgressDialog progressDialog;
    private ProgressDialog progressDialog;
    private Unbinder unbinder;
    //是否注册EventBus事件
    protected boolean isRegisterEvent;

    @Override
    public void onAttach(Context context) {
        this.mActivity = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            unbinder = ButterKnife.bind(this, mView);
            return mView;
        }
        isFirstLoad = true;
        //绑定View
        View view = null;
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;

        initData();
        if (savedInstanceState != null) {
            initStateData(savedInstanceState);
        }
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.attach((BaseView) this);
        }
        registerEventBus();
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initWidgetAndEvent();
        //在此方法中获取数据为懒加载模式,如不需要懒加载,请在initEventAndData获取数据,GankFragment有使用实例
        lazyLoad();
        mView = view;

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void initStateData(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    private void registerEventBus() {
        if (isRegisterEvent && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void unRegisterEventBus() {
        if (isRegisterEvent)
            EventBus.getDefault().unregister(this);
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad)
            return;
        isFirstLoad = false;
        lazyLoadData();
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initWidgetAndEvent();

    protected abstract void lazyLoadData();

    public void showBaseLoading(String string) {
        progressDialog = ProgressDialog.getInstance(getActivity());
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
        ToastUtils.showToast(getActivity(), message);
    }

    public void showToast(int ResID) {
        ToastUtils.showToast(getActivity(), ResID);
    }

    public void showBaseMessageDialog(final String message) {
        if (TextUtils.isEmpty(message))
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog(getActivity()).setWidthRatio(0.7f).builder()
                        .hideTitleLayout().setMsg(message).setNegativeButton(("确 定"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).setMessageGravity(Gravity.CENTER).show();
            }
        });
    }

    @Override
    public void showLoading(String str) {
        if (isVisible)
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.detachView();
            mPresenter = null;
        }
        destroyProgressDialog();
        unbinder.unbind();
        ToastUtils.release();
        unRegisterEventBus();
    }

}
