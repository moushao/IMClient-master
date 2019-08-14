package com.scwang.smartrefresh.layout.api;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * 刷新布局
 * Created by SCWANG on 2017/5/26.
 */

public interface RefreshLayout extends NestedScrollingParent, NestedScrollingChild {

    RefreshLayout setFooterHeight(float dp);

    RefreshLayout setFooterHeightPx(int px);

    RefreshLayout setHeaderHeight(float dp);

    RefreshLayout setHeaderHeightPx(int px);

    /**
     * 显示拖动高度/真实拖动高度（默认0.5，阻尼效果）
     *
     * @param rate s
     * @return s
     */
    RefreshLayout setDragRate(float rate);

    /**
     * 设置下拉最大高度和Header高度的比率（将会影响可以下拉的最大高度）
     *
     * @param rate s
     * @return s
     */
    RefreshLayout setHeaderMaxDragRate(float rate);

    /**
     * 设置上啦最大高度和Footer高度的比率（将会影响可以上啦的最大高度）
     *
     * @param rate s
     * @return s
     */
    RefreshLayout setFooterMaxDragRate(float rate);

    /**
     * 设置回弹显示插值器
     */
    RefreshLayout setReboundInterpolator(Interpolator interpolator);

    /**
     * 设置回弹动画时长
     */
    RefreshLayout setReboundDuration(int duration);

    /**
     * 设置是否启用上啦加载更多（默认启用）
     */
    RefreshLayout setEnableLoadmore(boolean enable);

    /**
     * 是否启用下拉刷新（默认启用）
     */
    RefreshLayout setEnableRefresh(boolean enable);

    /**
     * 设置是否启在下拉Header的同时下拉内容
     */
    RefreshLayout setEnableHeaderTranslationContent(boolean enable);

    /**
     * 设置是否启在上拉Footer的同时上拉内容
     */
    RefreshLayout setEnableFooterTranslationContent(boolean enable);

    /**
     * 设置是否开启在刷新时候禁止操作内容视图
     */
    RefreshLayout setDisableContentWhenRefresh(boolean disable);

    /**
     * 设置是否开启在加载时候禁止操作内容视图
     */
    RefreshLayout setDisableContentWhenLoading(boolean disable);

    /**
     * 设置是否监听列表在滚动到底部时触发加载事件（默认true）
     */
    RefreshLayout setEnableAutoLoadmore(boolean enable);

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    RefreshLayout setLoadmoreFinished(boolean finished);

    /**
     * 设置指定的Header
     */
    RefreshLayout setRefreshFooter(RefreshFooter bottom);

    /**
     * 设置指定的Header
     */
    RefreshLayout setRefreshFooter(RefreshFooter footer, int width, int height);

    /**
     * 设置指定的Header
     */
    RefreshLayout setRefreshHeader(RefreshHeader header);

    /**
     * 设置指定的Header
     */
    RefreshLayout setRefreshHeader(RefreshHeader header, int width, int height);

    /**
     * 设置是否启用越界回弹
     */
    RefreshLayout setEnableOverScrollBounce(boolean enable);

    /**
     * 设置是否开启纯滚动模式
     */
    RefreshLayout setEnablePureScrollMode(boolean enable);

    /**
     * 设置是否在加载更多完成之后滚动内容显示新数据
     */
    RefreshLayout setEnableScrollContentWhenLoaded(boolean enable);

    /**
     * 单独设置刷新监听器
     */
    RefreshLayout setOnRefreshListener(OnRefreshListener listener);

    /**
     * 单独设置加载监听器
     */
    RefreshLayout setOnLoadmoreListener(OnLoadmoreListener listener);

    /**
     * 同时设置刷新和加载监听器
     */
    RefreshLayout setOnRefreshLoadmoreListener(OnRefreshLoadmoreListener listener);

    /**
     * 
     * @param listener 设置多功能监听器
     * @return s
     */
    RefreshLayout setOnMultiPurposeListener(OnMultiPurposeListener listener);

    /**
     * 
     * @param primaryColorId 设置主题颜色
     * @return s
     */
    RefreshLayout setPrimaryColorsId(@ColorRes int... primaryColorId);

    /**
     *  设置主题颜色
     * @param colors s
     * @return s
     */
    RefreshLayout setPrimaryColors(int... colors);

    /**
     * 
     * @param boundary s
     * @return s
     */
    RefreshLayout setRefreshScrollBoundary(RefreshScrollBoundary boundary);

    /**
     *
     * @return s
     */
    RefreshLayout finishRefresh();

    /**
     * 
     * @return s
     */
    RefreshLayout finishLoadmore();

    /**
     * 完成刷新
     * @param delayed
     * @return s
     */
    RefreshLayout finishRefresh(int delayed);

    /**
     * 完成加载
     *
     * @param success 数据是否成功刷新 （会影响到上次更新时间的改变）
     */
    RefreshLayout finishRefresh(boolean success);
    /**
     * 完成加载
     *
     * @param delayed s
     * @param success s
     * @return s
     */
    RefreshLayout finishRefresh(int delayed, boolean success);
    /**
     * 完成加载
     *
     * @param delayed s
     * @return s
     */
    RefreshLayout finishLoadmore(int delayed);

    /**
     * 完成加载
     *
     * @param success s
     * @return s
     */
    RefreshLayout finishLoadmore(boolean success);

    /**
     * 完成加载
     *
     * @param delayed
     * @param success 是
     * @return s
     */
    RefreshLayout finishLoadmore(int delayed, boolean success);

    /**
     * @return s
     */
    @Nullable
    RefreshHeader getRefreshHeader();

    /**
     * @return s
     */
    @Nullable
    RefreshFooter getRefreshFooter();

    /**
     * @return s
     */
    RefreshState getState();

    /**
     * @return s
     */
    ViewGroup getLayout();

    /**
     * @return s
     */
    boolean isRefreshing();

    /**
     * @return s
     */
    boolean isLoading();

    /**
     * 自动刷新
     */
    boolean autoRefresh();

    /**
     * @return s
     */
    boolean autoRefresh(int delayed);

    /**
     * 自动刷新
     */
    boolean autoRefresh(int delayed, float dragrate);

    /**
     * @return s
     */
    boolean autoLoadmore();

    /**
     * @return s
     */
    boolean autoLoadmore(int delayed);

    /**
     * @return s
     */
    boolean autoLoadmore(int delayed, float dragrate);

    /**
     * @return s
     */
    boolean isEnableRefresh();

    /**
     * @return s
     */
    boolean isEnableLoadmore();

    /**
     * @return s
     */
    boolean isLoadmoreFinished();

    /**
     * @return s
     */
    boolean isEnableAutoLoadmore();

    /**
     * @return s
     */
    boolean isEnableOverScrollBounce();

    /**
     * @return s
     */
    boolean isEnablePureScrollMode();

    /**
     * @return s
     */
    boolean isEnableScrollContentWhenLoaded();
}
