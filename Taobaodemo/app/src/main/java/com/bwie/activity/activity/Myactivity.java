package com.bwie.activity.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bwie.activity.R;
import com.bwie.activity.fragments.Fragment1;
import com.bwie.activity.fragments.Fragment3;
import com.bwie.activity.fragments.Fragment4;
import com.bwie.activity.help.FragmentTwo;

/**
 * 1.主页面
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/8/31 15:42
 */
public class Myactivity extends AppCompatActivity{

    private EditText myhead_ed;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private RadioGroup my_froup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        my_froup = (RadioGroup) findViewById(R.id.my_group);
        fragmentManager = getSupportFragmentManager();
        addToStack("fragment01",new Fragment1());
        my_froup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.button1:
                        addToStack("fragment01",new Fragment1());
                        break;
                    case R.id.button2:
                        addToStack("fragment02",new FragmentTwo());
                        break;
                    case R.id.button3:
                        addToStack("fragment03",new Fragment3());
                        break;
                    case R.id.button4:

                        addToStack("fragment04",new Fragment4());

                        break;
                }
            }
        });
    }

    private void addToStack(String tag, Fragment fragment) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.frameLayout, fragment);
        //添加到回退栈
        beginTransaction.addToBackStack(tag);
        beginTransaction.commit();
    }

}
