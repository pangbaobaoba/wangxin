package com.bwie.activity.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bwie.activity.R;
import com.bwie.activity.bean.Loginbean;
import com.bwie.activity.utils.OkHttp3Utils;
import com.bwie.activity.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/13 20:30
 */
public class Zhuxiao extends AppCompatActivity implements View.OnClickListener {

    private Button zhece_button;
    private String path="http://169.254.229.178/mobile/index.php?act=logout";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:

                    String json = (String) msg.obj;
                    Log.i("sdf",json);
                    Gson gson=new Gson();
                    Loginbean loginbean = gson.fromJson(json, Loginbean.class);
                    int code = loginbean.getCode();
                    if(code==200){
                        Log.i("asdf","注销成功");

                    }else  if(code==400){
                        Log.i("asdfs","注销失败");
                    }
                    break;
            }


        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.zhuxiao);
        zhece_button = (Button) findViewById(R.id.zhuxiao_button);
        zhece_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Zhuxiao.this, "key", "sdfg");
        Log.i("asd",key);
        String  yongh = (String) instances.getValueByKey(Zhuxiao.this, "login", "dfg");
        getdata(key,yongh);
    }

    private void getdata(String key,String username) {
        Map<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("client","android");
        map.put("username",username);
        Log.i("username",username);
        OkHttp3Utils.doPost(path, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message=new Message();
                message.what=0;
                message.obj=string;
                handler.sendMessage(message);
            }
        });
    }
}
