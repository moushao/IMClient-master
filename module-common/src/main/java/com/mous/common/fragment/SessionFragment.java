package com.mous.common.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mous.common.R;
import com.mous.common.R2;
import com.mous.common.adapter.SessionHolder;
import com.mous.common.event.SessionItemListener;
import com.mous.common.message.LastChatMessage;
import com.mous.common.widget.ConnectStatusWidget;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import component.baselib.adapter.VBaseAdapter;
import component.baselib.base.BaseFragment;
import component.baselib.mvp.BasePresenter;
import mous.component.im.netty.IMEngine;

/**
 * Created by MouShao on 2019/8/14.
 */

public class SessionFragment extends BaseFragment {
    @BindView(R2.id.session_connect_widget) ConnectStatusWidget mConnectWidget;
    @BindView(R2.id.session_recycler) RecyclerView mRecycler;
    @BindView(R2.id.session_refresh) SmartRefreshLayout mRefresh;

    private Context mContext;
    private VBaseAdapter adapter;
    private List<LastChatMessage> list = new ArrayList<>();

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_session_fragment;
    }

    @Override
    protected void initData() {
        mContext = getActivity();
    }

    @Override
    protected void initWidgetAndEvent() {
        initRecyclerView();
        IMEngine.getInstance().init();
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();
    }

    private void initRecyclerView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new VBaseAdapter(mContext)
                .setData(list)
                .setHolder(SessionHolder.class)
                .setLayout(R.layout.item_contact_sliding)
                .setListener(new SessionItemListener() {
                    @Override
                    public void dragBadgeView(int position, LastChatMessage messageItem) {
                    }

                    @Override
                    public void deleteSession(int position, LastChatMessage messageItem) {
                        adapter.removeItem(position);
                    }

                    @Override
                    public void onItemClick(View view, int position, Object mData) {
                        LastChatMessage lastChatMsg = (LastChatMessage) mData;
                        //ChatActivity.startAction(mContext, TAG, lastChatMsg.talkingID, lastChatMsg.title, lastChatMsg.isGroup);
                    }
                });
        mRecycler.setAdapter(adapter);
    }
}
