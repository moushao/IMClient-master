package com.mous.common.adapter;

import android.view.View;
import android.widget.TextView;

import com.mous.common.R2;
import com.mous.common.bean.IMUser;

import butterknife.BindView;
import component.baselib.adapter.VBaseHolder;

/**
 * Created by MouShao on 2019/8/19.
 */

public class ContactsHolder extends VBaseHolder<IMUser> {
    @BindView(R2.id.item_contacts_name) TextView mName;
    @BindView(R2.id.item_contacts_view) View mSegmentingLine;

    public ContactsHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(int position, IMUser mData) {
        super.setData(position, mData);
        mName.setText(mData.getNickName());
        if (position == mCount - 1)
            mSegmentingLine.setVisibility(View.INVISIBLE);
    }
}
