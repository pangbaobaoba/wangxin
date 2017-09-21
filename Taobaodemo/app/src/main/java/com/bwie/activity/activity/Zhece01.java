package com.bwie.activity.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bwie.activity.R;
import com.bwie.activity.bean.Loginbean;
import com.bwie.activity.utils.OkHttp3Utils;
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
 * 3.@date 2017/9/13 13:50
 */
public class Zhece01 extends AppCompatActivity implements View.OnClickListener {

    private EditText zhece_yhm;
    private EditText zhece_mm;
    private EditText zhece_yx;
    private EditText zhece_yx1;
    private Button zhece1_login;
    private EditText zhece_cxmm;
    private String path="http://169.254.229.178/mobile/index.php?act=login&op=register";
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
                        Log.i("asdf","注册成功");

                    }else  if(code==400){
                        Log.i("asdfs","注册失败");
                    }
                    break;
            }


        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhece01);
        //获取控件
        zhece_yhm = (EditText) findViewById(R.id.zhece_yhm);
        zhece_mm = (EditText) findViewById(R.id.zhece_mm);

        zhece_yx1 = (EditText) findViewById(R.id.zhece_yx);
        zhece_cxmm = (EditText) findViewById(R.id.zhece_cxmm);
        zhece1_login = (Button) findViewById(R.id.zhece1_login);
        zhece1_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String string = zhece_yhm.getText().toString();
        String string1 = zhece_mm.getText().toString();
        String string2 = zhece_yx1.getText().toString();
        String string3 = zhece_cxmm.getText().toString();
        getserver(string,string1,string2,string3);


    }

    private void getserver(String string, String string1, String string2, String string3) {
        Map<String,String> map=new HashMap<>();
        map.put("username",string);
        map.put("password",string1);
        map.put("password_confirm",string3);
        map.put("email",string2);
        map.put("client","android");
        OkHttp3Utils.doPost(path, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                  Message message=new Message();
                message.what=0;
                message.obj=response.body().string();
                handler.sendMessage(message);
            }
        });
    }
}
