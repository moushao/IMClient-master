package component.baselib.bean;

/**
 * Created by MouShao on 2018/4/17.
 */

public class MessageEvent<T> {

    public String CODE;
    public T CONTENT;

    public MessageEvent(String CODE, T CONTENT) {
        this.CODE = CODE;
        this.CONTENT = CONTENT;
    }

    public MessageEvent() {
    }


}
