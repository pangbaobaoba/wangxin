package com.bwie.activity.help;

import java.util.List;

/**
 * Created by XP on 2017/9/17.
 */
public interface IClassifyView {
    void firstSuccess(List<Classify_First.DatasBean.ClassListBean> class_list);

    void classify_1();//请求第一级列表

    void classify_2(String gc_id);//请求第二级列表

    void SecondSuccess();
}
