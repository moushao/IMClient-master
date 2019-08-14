package component.baselib.utils;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * 类名: {@link TabBarUtils}
 * <br/> 功能描述: 主界面底部导航栏工具类，用于状态切换
 * <br/> 作者: MouShao
 * <br/> 时间: 2018/4/11
 */
public class TabBarUtils {
    private TextView[] tvList;
    private ImageView[] imgList;
    //上一个为止
    private int prePosition = 0;
    private int[] selDrawable;
    private int[] norDrawable;
    private String selectColor = "#2399E4";
    private String unSelectColor = "#7b8191";

    public TabBarUtils() {

    }

    public TabBarUtils setSelDrawable(int[] selDrawable) {
        this.selDrawable = selDrawable;
        return this;
    }

    public TabBarUtils setNorDrawable(int[] norDrawable) {
        this.norDrawable = norDrawable;
        return this;
    }

    public TabBarUtils setTextArray(TextView[] tvArray) {
        this.tvList = tvArray;
        return this;
    }

    public TabBarUtils setImageArray(ImageView[] imageArray) {
        this.imgList = imageArray;
        return this;
    }

    public void ChangeTabSelected(int prePosition, int currentPosition) {
        if (prePosition == currentPosition)
            return;
        //将选中的tab设置选中状态
        if (imgList != null && selDrawable != null && currentPosition < imgList.length && currentPosition < selDrawable
                .length)
            imgList[currentPosition].setImageResource(selDrawable[currentPosition]);
        if (tvList != null && currentPosition < tvList.length)
            tvList[currentPosition].setTextColor(Color.parseColor(selectColor));
        //把上一个选中置为位选中
        if (imgList != null && norDrawable != null && prePosition < imgList.length && prePosition < norDrawable.length)
            imgList[prePosition].setImageResource(norDrawable[prePosition]);
        if (tvList != null && prePosition < tvList.length)
            tvList[prePosition].setTextColor(Color.parseColor(unSelectColor));
        this.prePosition = currentPosition;
    }

    public void ChangeTabSelected(int currentPosition) {
        ChangeTabSelected(prePosition, currentPosition);
        prePosition = currentPosition;
    }

    public TabBarUtils setSelectColor(String selectColor) {
        this.selectColor = selectColor;
        return this;
    }

    public TabBarUtils setUnSelectColor(String unSelectColor) {
        this.unSelectColor = unSelectColor;
        return this;
    }
}

