package com.mous.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mous.common.fragment.ContactsFragment;
import com.mous.common.fragment.MainFragment;
import com.mous.common.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import component.baselib.adapter.FragmentAdapter;
import component.baselib.arouter.RouterURLS;
import component.baselib.base.BaseActivity;
import component.baselib.mvp.BasePresenter;
import component.baselib.utils.TabBarUtils;
import component.baselib.widget.CustomViewPager;


/**
 * 类名: {@link MainActivity}
 * <br/> 功能描述:
 * <br/> 作者: MouShao
 * <br/> 时间:
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
@Route(path = RouterURLS.COMMON_MAIN)
public class MainActivity extends BaseActivity {
    public final static String TAG = "MainActivity";
    @BindView(R2.id.pager) CustomViewPager mPager;
    private TabBarUtils mTabBarUtils;

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mContext = this;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initWidgetAndEvent() {
        initFragment();
    }

    private void initFragment() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new ContactsFragment());
        fragmentList.add(new MineFragment());
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(3);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
