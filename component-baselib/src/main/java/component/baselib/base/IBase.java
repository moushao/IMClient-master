package component.baselib.base;


import component.baselib.mvp.BasePresenter;

public interface IBase {
    //返回解析器
    BasePresenter getPresenter();

    //    View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    //
    //    void bindView(Bundle savedInstanceState);

    //    int getContentLayout();
}
