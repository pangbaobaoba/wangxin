package com.bwie.activity.help;

/**
 * Created by XP on 2017/9/17.
 */
public class IClassifyPresenter {
    private IClassifyView iClassifyView;
    private IClassifyModle iClassifyModle;
    public IClassifyPresenter(IClassifyView iClassifyView)
    {
        this.iClassifyView=iClassifyView;
        iClassifyModle=new ClassifyModle();
    }
    public void setClassifyFirst(IClassifyView iClassifyView)
    {
        iClassifyModle.classify_1(iClassifyView);
    }
}
