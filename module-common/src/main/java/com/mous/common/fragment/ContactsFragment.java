package com.mous.common.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.mous.common.R;
import com.mous.common.R2;
import com.mous.common.adapter.ContactsHolder;
import com.mous.common.bean.IMUser;
import com.mous.common.ui.ChatActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;
import component.baselib.adapter.VBaseAdapter;
import component.baselib.base.BaseFragment;
import component.baselib.event.ItemListener;
import component.baselib.mvp.BasePresenter;

/**
 * Created by MouShao on 2019/8/14.
 */

public class ContactsFragment extends BaseFragment {
    public final static String TAG = ContactsFragment.class.getName();
    @BindView(R2.id.contacts_rv) RecyclerView mRecycler;
    @BindView(R2.id.contacts_refresh) SmartRefreshLayout mContactsRefresh;
    Unbinder unbinder;
    private Context mContext;
    private VBaseAdapter contactsAdapter;

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
        mContext = getActivity();
    }

    @Override
    protected void initWidgetAndEvent() {
        initRecyclerView();
        setData();
    }

    private void setData() {
        ArrayList<IMUser> list = new ArrayList<>();
        list.add(new IMUser("zhaoyi", "赵一"));
        list.add(new IMUser("qianer", "钱二"));
        list.add(new IMUser("zhangsan", "张三"));
        list.add(new IMUser("lisi", "李四"));
        list.add(new IMUser("wangwu", "王五"));
        list.add(new IMUser("wuliu", "吴六"));
        list.add(new IMUser("zhengqi", "郑七"));
        list.add(new IMUser("wangba", "王八"));
        contactsAdapter.setData(list);
    }

    @Override
    protected void lazyLoadData() {

    }

    private void initRecyclerView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        mRecycler.setLayoutManager(virtualLayoutManager);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);
        contactsAdapter = new VBaseAdapter(mContext)
                .setData(new ArrayList())
                .setHolder(ContactsHolder.class)
                .setLayoutHelper(getUserAdapterLayoutHelper())
                .setLayout(R.layout.item_contacts)
                .setListener(new ItemListener<IMUser>() {
                    @Override
                    public void onItemClick(View view, int position, IMUser mData) {
                        ChatActivity.startAction(mContext, TAG, mData.getUserID(), mData.getNickName(), false);
                    }
                });

        delegateAdapter.addAdapter(contactsAdapter);
        mRecycler.setAdapter(delegateAdapter);
    }

    private LayoutHelper getUserAdapterLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        layoutHelper.setMarginTop(20);
        return layoutHelper;
    }
}
