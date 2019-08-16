package com.mous.common.adapter;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mous.common.R;
import com.mous.common.R2;
import com.mous.common.event.SessionItemListener;
import com.mous.common.message.LastChatMessage;

import butterknife.BindView;
import butterknife.OnClick;
import component.baselib.adapter.VBaseHolder;
import component.baselib.utils.DateUtils;
import component.baselib.utils.ScreenUtil;
import component.baselib.widget.SlidingView;

/**
 * Created by MouShao on 2018/8/3.
 */

public class SessionHolder extends VBaseHolder<LastChatMessage> {
//    @BindView(R2.id.item_session_badge) BGABadgeFrameLayout mBadgeView;
    @BindView(R2.id.session_sliding_view) SlidingView mSlidingView;
    @BindView(R2.id.item_session_head) ImageView mHead;
    @BindView(R2.id.item_session_name) TextView mName;
    @BindView(R2.id.item_session_content) TextView mContent;
    @BindView(R2.id.item_session_time) TextView mTime;
    @BindView(R2.id.item_session_layout) LinearLayout mSessionLayout;

    public SessionHolder(View itemView) {
        super(itemView);
        ViewGroup.LayoutParams params = mSessionLayout.getLayoutParams();
        params.width = ScreenUtil.getScreenWidth(mContext);
        mSessionLayout.setLayoutParams(params);
    }

    @Override
    public void setData(int position, LastChatMessage mData) {
        super.setData(position, mData);
        mName.setText(mData.title);
        mContent.setText(mData.lastContent);
        String time = DateUtils.DateFormatTime(mData.time.replaceAll("/", "-"));
        mTime.setText(DateUtils.DateFormatTime(time));
        if (mData.UnReadCount != 0) {
            //mBadgeView.showTextBadge(mData.UnReadCount + "");
        } else {
            //mBadgeView.hiddenBadge();
        }
       /* mBadgeView.setDragDismissDelegate(new BGADragDismissDelegate() {
            @Override
            public void onDismiss(BGABadgeable bgaBadgeable) {
                dragBadgeViewListener();
            }
        });*/
        if (mData.isGroup) {
            mHead.setImageResource(R.drawable.icon_chat_group_head);
        } else {
            mHead.setImageResource(R.drawable.default_head_boy);
        }

        if (!mData.isGroup && mData.talkingStatus == 1) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);//饱和度 0灰色 100过度彩色，50正常
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            mHead.setColorFilter(filter);
        } else {
            mHead.setColorFilter(null);
        }
    }

    //R.layout.item_contact_sliding
    @OnClick({R2.id.item_session_delete, R2.id.content_layout})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.item_session_delete) {
            mSlidingView.reset();
            dragBadgeViewListener();
            ((SessionItemListener) mListener).deleteSession(position, mData);
        } else if (i == R.id.content_layout) {
            if (mSlidingView.isShow()) {
                mSlidingView.reset();
            } else {
               /* if (mBadgeView.isShowBadge()) {
                    mBadgeView.hiddenBadge();
                    dragBadgeViewListener();
                }*/

                mListener.onItemClick(mView, position, mData);
            }
        }
    }

    private void dragBadgeViewListener() {
        ((SessionItemListener) mListener).dragBadgeView(position, mData);
    }
}
