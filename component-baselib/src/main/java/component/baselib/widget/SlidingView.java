package component.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 类名: {@link SlidingView}
 * <br/> 功能描述:侧滑控件
 * <br/> 作者: MouShao
 * <br/> 时间: 2018/5/14
 * <br/> 备注:此控件需要在代码中重新设置linearLayoutLeft中子布局的宽度,因为最开始linearLayoutLeft的宽度为wrap_content,子布局的
 * <br/> 最大宽度也就为linearLayoutLeft的宽度,当onlayout执行后,linearLayoutLeft的宽度为屏幕宽度,
 * <br/> 但是linearLayoutLeft子布局的宽度依然是之前linearLayoutLeft的wrap_content,所以需要重新设置
 */
public class SlidingView extends HorizontalScrollView {

    private boolean isLeft = true;//在左边
    private boolean isShow = false;//是否打开侧滑栏


    public SlidingView(Context context) {
        super(context);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int buttonWidth;
    private DisplayMetrics scale;

    private void init() {
        scale = getContext().getResources().getDisplayMetrics();
        buttonWidth = dp2px(100);// 布局的宽度
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.i(getClass().getSimpleName(), l + "-" + t + "-" + r + "-" + b);

        //调整布局
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);
        LinearLayout linearLayoutLeft = (LinearLayout) linearLayout.getChildAt(0);
        LinearLayout linearLayoutRight = (LinearLayout) linearLayout.getChildAt(1);
        linearLayout.layout(linearLayout.getLeft(), linearLayout.getTop(), linearLayout.getLeft()
                + getMeasuredWidth() + buttonWidth, linearLayout.getBottom());
        linearLayoutLeft.layout(linearLayoutLeft.getLeft(), linearLayoutLeft.getTop(), linearLayoutLeft.getLeft() +
                getMeasuredWidth(), linearLayoutLeft.getBottom());
        linearLayoutRight.layout(linearLayoutLeft.getLeft() + getMeasuredWidth(), linearLayoutLeft.getTop(),
                linearLayoutLeft.getLeft() + getMeasuredWidth() + buttonWidth, linearLayoutLeft.getBottom());

    }


    //恢复状态
    public void reset() {
        isLeft = true;
        isShow = false;
        smoothScrollTo(0, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            Log.i(getClass().getSimpleName(), "down");
            return true;
        }
        if (ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_UP) {
            Log.i(getClass().getSimpleName(), "up");
            int range = 70;
            if (isLeft) {
                if (getScrollX() > range) {
                    isLeft = false;
                    isShow = true;
                    smoothScrollTo(buttonWidth, 0);
                } else {
                    isShow = false;
                    smoothScrollTo(0, 0);

                }
            } else {
                if (getScrollX() < (buttonWidth - range)) {
                    isLeft = true;
                    isShow = false;
                    smoothScrollTo(0, 0);
                } else {
                    isShow = true;
                    smoothScrollTo(buttonWidth, 0);
                }
            }
            return true;
        }
        Log.i(getClass().getSimpleName(), "end");
        return super.onTouchEvent(ev);
    }


    private int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, scale);
    }

    public boolean isShow() {
        return isShow;
    }
}