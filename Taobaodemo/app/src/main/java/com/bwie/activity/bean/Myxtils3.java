package com.bwie.activity.bean;

import android.app.Application;

import com.bwie.activity.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/8/31 15:29
 */

public class Myxtils3 extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
       //初始化
        x.Ext.init(this);
    }
}
