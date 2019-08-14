package com.mous.common.fragment;

import com.mous.common.R;

import component.baselib.base.BaseFragment;
import component.baselib.mvp.BasePresenter;

/**
 * Created by MouShao on 2019/8/14.
 */

public class ContactsFragment extends BaseFragment {
    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_contacts_fragment;

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initWidgetAndEvent() {

    }

    @Override
    protected void lazyLoadData() {

    }
}
