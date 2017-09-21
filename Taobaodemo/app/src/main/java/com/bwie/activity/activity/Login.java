package com.bwie.activity.activity;

import android.content.Intent;
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
 * 3.@date 2017/9/12 19:28
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText login_yhm;
    private EditText login_pass;
    private String path="http://169.254.229.178/mobile/index.php?act=login";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String json = (String) msg.obj;
                    Gson gson=new Gson();
                    Loginbean loginbean = gson.fromJson(json, Loginbean.class);

                    int code = loginbean.getCode();
                    Loginbean.DatasBean datas = loginbean.getDatas();

                    if(code==200){
                        key = datas.getKey();
                        Log.i("nnnn",key);
                        Log.i("asdf","登录成功");
                        SharedUtil instances = SharedUtil.getInstances();
                        instances.saveDatad(Login.this,"boolean",true);
                        instances.saveDatad(Login.this,"login",yhm);
                        instances.saveDatad(Login.this,"loginkey", key);

                        Intent intent=new Intent(Login.this,Myactivity.class);
                        intent.putExtra("yhm",yhm);
                        startActivity(intent);
                    }

                    break;
            }
        }
    };
    private String yhm;
    private String key;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.login);
        //获取id
        button = (Button) findViewById(R.id.login_bt);
        login_pass = (EditText) findViewById(R.id.login_pass);
        login_yhm = (EditText) findViewById(R.id.login_yhm);


        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String pass = login_pass.getText().toString();
        yhm = login_yhm.getText().toString();
        Log.i("asdff",pass+ yhm);
        denglu(pass, yhm);
    }

    private void denglu(String pass, String yhm) {
        final Map<String,String> map=new HashMap<>();
        map.put(" username",yhm);
        map.put(" password",pass);
        map.put("client","android");
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
