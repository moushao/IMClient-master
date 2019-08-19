package com.mous.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mous.common.R;
import com.mous.common.fragment.ChatFragment;

import component.baselib.base.BaseActivity;
import component.baselib.mvp.BasePresenter;
import component.data.common.Constants;


public class ChatActivity extends BaseActivity {
    public final static String TAG = "CHAT_ACTIVITY";
    public final static String CONTACT_ID = "CONTACT_ID";
    public final static String CONTACT_NAME = "CONTACT_NAME";
    public final static String CONTACT_TYPE = "CONTACT_TYPE";
    private Context mContext;
    ChatFragment mChatFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData() {
        mContext = this;
    }

    @Override
    protected void initWidgetAndEvent() {
        mChatFragment = new ChatFragment();
        mChatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.chat_frame_container, mChatFragment).commit();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public static void startAction(Context mContext, String from, String id, String name, Boolean isGroup) {
        Intent itt = new Intent(mContext, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FROM, from);
        bundle.putString(CONTACT_ID, id);
        bundle.putString(CONTACT_NAME, name);
        bundle.putBoolean(CONTACT_TYPE, isGroup);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }
}
