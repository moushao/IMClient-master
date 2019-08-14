package component.baselib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.LayoutHelper;

import java.lang.reflect.Constructor;
import java.util.List;

import component.baselib.event.ItemListener;

/**
 * Created by MouShao on 2018/4/12.
 */

public class VGirdAdapter<T> extends VBaseAdapter {
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    public String title;
    private int headLayoutID;
    private Class<? extends VBaseHolder> mHeadHolder;

    public VGirdAdapter(Context context) {
        super(context);
    }


    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public VBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int id = 0;
        Class<? extends VBaseHolder> holder = mMutipleHolder;
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            id = headLayoutID;
            holder = mHeadHolder;
        } else if (viewType == ITEM_VIEW_TYPE_ITEM) {
            id = mResLayout;
            holder = mMutipleHolder;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
        try {
            Constructor<? extends VBaseHolder> mClazzConstructor = holder.getConstructor(View.class);
            if (mClazzConstructor != null) {
                return mClazzConstructor.newInstance(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    @Override
    public void onBindViewHolder(VBaseHolder holder, int position) {
        holder.setContext(mContext);
        holder.setListener(mListener);
        if (isHeader(position)) {
            holder.setData(0, title);
        } else {
            holder.setData(position - 1, mDatas.get(position - 1));
        }


    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    public VGirdAdapter setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public VGirdAdapter setData(List mDatas) {
        super.setData(mDatas);
        return this;
    }

    public VGirdAdapter setItemCount(int size) {
        super.setItemCount(size);
        return this;
    }

    @Override
    public VGirdAdapter setLayoutHelper(LayoutHelper layoutHelper) {
        super.setLayoutHelper(layoutHelper);
        return this;
    }

    public VGirdAdapter setHeadLayout(int headLayoutID) {
        this.headLayoutID = headLayoutID;
        return this;
    }

    public VGirdAdapter setLayout(int mResLayout) {
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mResLayout = mResLayout;
        return this;
    }

    public VGirdAdapter setHeadHolder(Class<? extends VBaseHolder> mHeadHolder) {
        if (mHeadHolder == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        this.mHeadHolder = mHeadHolder;
        return this;
    }


    @Override
    public VGirdAdapter setHolder(Class mClazz) {
        super.setHolder(mClazz);
        return this;
    }

    @Override
    public VGirdAdapter setListener(ItemListener listener) {
        super.setListener(listener);
        return this;
    }

    /**
     * Created by MouShao on 2018/4/17.
     */


}
