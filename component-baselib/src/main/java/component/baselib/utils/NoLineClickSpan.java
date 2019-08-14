package component.baselib.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Class_name：NoLineClickSpan 
 * Class_description： Override the ClickableSpan
 * class to remove the underline style (the default system uses ClickableSpan to
 * Create_time： November 26  2012 - 11:39:51 a.m. Change records： 
 * Modifier： LuoZhiPeng
 * Modifier_time：November 26, 2012 - 11:39:51 a.m. 
 * Version： 
 * Description：
 */
public class NoLineClickSpan extends ClickableSpan {

    public NoLineClickSpan() {
        super();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        /**set textColor**/
        ds.setColor(ds.linkColor);
        /**Remove the underline**/
        ds.setUnderlineText(false);     
    }

    @Override
    public void onClick(View widget) {
    }
}