package com.bwie.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bwie.activity.R;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/12 21:02
 */
public class Zhuce extends AppCompatActivity implements View.OnClickListener {

    private Button zhece_button;
    private Button button_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.zhece);
        zhece_button = (Button) findViewById(R.id.zhece_button);
        button_login = (Button) findViewById(R.id.button_login);
        zhece_button.setOnClickListener(this);
        button_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_login:
                Intent intent=new Intent(Zhuce.this,Login.class);
                startActivity(intent);
                break;
            case R.id.zhece_button:
                Intent intent1=new Intent(Zhuce.this,Zhece01.class);
                startActivity(intent1);

                break;
        }

    }
}
