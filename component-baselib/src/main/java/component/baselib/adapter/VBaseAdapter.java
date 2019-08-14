package component.baselib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

import component.baselib.event.ItemListener;


/**
 * 类名: {@link VBaseAdapter}
 * <br/> 功能描述:万能Adapter,集成阿里的DelegateAdapter,勿动!若有改动请联系管理员MouShao
 * <br/> 作者: MouShao
 * <br/> 时间: 2018/6/5
 */
public class VBaseAdapter<T> extends DelegateAdapter.Adapter<VBaseHolder<T>> {
    //上下文
    public Context mContext;
    //布局文件资源ID
    public int mResLayout;
    //数据源
    public List<T> mDatas;
    //布局管理器
    private LayoutHelper mLayoutHelper;
    //继承VBaseHolder的内容Holder
    public Class<? extends VBaseHolder> mMutipleHolder;
    //集成VBaseHolder的头部Holder
    private Class<? extends VBaseHolder> mHeadHolder;
    //集成VBaseHolder的尾部Holder
    private Class<? extends VBaseHolder> mRootHolder;

    //回调监听
    public ItemListener mListener;
    public int count;

    public VBaseAdapter(Context context) {
        mContext = context;
    }


    /**
     * <br/> 方法名称:VBaseAdapter
     * <br/> 方法详述:构造函数
     * <br/> 参数:<同上申明>
     */
    public VBaseAdapter(Context context, List<T> mDatas, int mResLayout, Class<? extends VBaseHolder> mClazz,
                        LayoutHelper layoutHelper, ItemListener listener) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mContext = context;
        this.mResLayout = mResLayout;
        this.mLayoutHelper = layoutHelper;
        this.mMutipleHolder = mClazz;
        this.mListener = listener;
        this.mDatas = mDatas;
        this.count = mDatas.size();
        //this.mLayoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 
        // ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * <br/> 方法名称: VBaseAdapter
     * <br/> 方法详述: 设置数据源
     * <br/> 参数: mDatas，数据源
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setData(List<T> mDatas) {
        this.mDatas = mDatas;
        this.count = this.mDatas.size();
        notifyDataSetChanged();
        return this;
    }

    public VBaseAdapter addAllData(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        this.count = this.mDatas.size();
        notifyDataSetChanged();
        return this;
    }

    /**
     * <br/> 方法名称: addItem
     * <br/> 方法详述: 设置单个数据源
     * <br/> 参数: mItem，单个数据源
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter addItem(T mItem) {
        this.mDatas.add(mItem);
        this.count = mDatas.size();
        return this;
    }

    public VBaseAdapter removeItem(int i) {
        if (count > i) {
            this.mDatas.remove(i);
            count = mDatas.size();
            notifyDataSetChanged();
        }
        return this;
    }

    /**
     * <br/> 方法名称: setLayout
     * <br/> 方法详述: 设置布局资源ID
     * <br/> 参数: mutipleLayoutRes, 布局资源ID
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setLayout(int mResLayout) {
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mResLayout = mResLayout;
        return this;
    }

    /**
     * <br/> 方法名称: setLayoutHelper
     * <br/> 方法详述: 设置布局管理器
     * <br/> 参数: layoutHelper，管理器
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setLayoutHelper(LayoutHelper layoutHelper) {
        if (layoutHelper == null) {
            this.mLayoutHelper = new LinearLayoutHelper();
        }
        this.mLayoutHelper = layoutHelper;
        return this;
    }

    /**
     * <br/> 方法名称: setHolder
     * <br/> 方法详述: 设置holder
     * <br/> 参数: mutipleHolder,集成VBaseHolder的holder
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setHolder(Class<? extends VBaseHolder> mClazz) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        this.mMutipleHolder = mClazz;
        return this;
    }

    /**
     * <br/> 方法名称: setListener
     * <br/> 方法详述: 传入监听，方便回调
     * <br/> 参数: listener
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setListener(ItemListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * <br/> 方法名称: onCreateLayoutHelper
     * <br/> 方法详述: 继承DelegateAdapter.Adapter后重写方法，告知elegateAdapter.Adapter使用何种布局管理器
     * <br/> 参数:
     * <br/> 返回值:  VBaseAdapter
     */
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper == null ? new LinearLayoutHelper() : mLayoutHelper;
    }


    public HashMap<Integer, Object> tags = new HashMap<>();

    /**
     * <br/> 方法名称: setTag
     * <br/> 方法详述: 设置mObject
     * <br/> 参数: mObject
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setTag(int tag, Object mObject) {
        if (mObject != null) {
            tags.put(tag, mObject);
        }
        return this;
    }

    /**
     * <br/> 方法名称: onCreateViewHolder
     * <br/> 方法详述: 解析布局文件，返回传入holder的构造器
     */
    @Override
    public VBaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResLayout, parent, false);
        if (tags != null && tags.size() > 0) {
            for (int tag : tags.keySet()) {
                view.setTag(tag, tags.get(tag));
            }
        }
        try {
            Constructor<? extends VBaseHolder> mClazzConstructor = mMutipleHolder.getConstructor(View.class);
            if (mClazzConstructor != null) {
                return mClazzConstructor.newInstance(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <br/> 方法名称: onBindViewHolder
     * <br/> 方法详述: 绑定数据
     * <br/> 参数:
     * <br/> 返回值:  VBaseAdapter
     */

    @Override
    public void onBindViewHolder(VBaseHolder holder, int position) {
        holder.setListener(mListener);
        holder.setContext(mContext);
        holder.setCount(count);
        holder.setData(position, mDatas.get(position));
    }

    public List<T> getDatas() {
        return mDatas;
    }


    @Override
    public int getItemCount() {
        //此处返回count是为了方便控制显示个数
        return count;
    }

    public VBaseAdapter setItemCount(int size) {
        this.count = size;
        return this;
    }

    public void clear() {
        this.mDatas.clear();
        this.count = 0;
    }
}
