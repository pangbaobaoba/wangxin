package com.bwie.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.activity.Zhuce;
import com.bwie.activity.activity.Zhuxiao;
import com.bwie.activity.utils.SharedUtil;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/1 20:32
 */
public class Fragment4 extends Fragment implements View.OnClickListener {
    private View view;
    private TextView yonghuming;
    private ImageView frag_image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedUtil instances = SharedUtil.getInstances();
        Boolean aBoolean = (Boolean) instances.getValueByKey(getActivity(), "boolean", false);

        if(aBoolean){
            view = View.inflate(getActivity(), R.layout.fragment04, null);
            yonghuming = (TextView) view.findViewById(R.id.yonghuming);
            String  yongh = (String) instances.getValueByKey(getActivity(), "login", "dfg");
            yonghuming.setText(yongh);
            frag_image = (ImageView) view.findViewById(R.id.frag_image);
           frag_image.setOnClickListener(this);
        }else{
            Intent intent=new Intent(getActivity(), Zhuce.class);
            startActivity(intent);

        }
        return  view;
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getActivity(), Zhuxiao.class);
        startActivity(intent);
    }
}
