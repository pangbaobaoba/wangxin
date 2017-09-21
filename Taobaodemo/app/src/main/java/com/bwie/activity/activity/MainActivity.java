package com.bwie.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bwie.activity.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    //获取id
   @ViewInject(R.id.main_image)
    ImageView imageView;
    int i=2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what){
                case 1:

                  imageView.setImageResource(R.drawable.taobao_launch);
                    break;
                case 0:
                    Intent intent=new Intent(MainActivity.this,Myactivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        //进行倒计时
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(i>0){
                    i--;
                    try {

                        handler.sendEmptyMessage(i);
                        sleep(1000);
                        SharedPreferences sp=getSharedPreferences("tindOye",MODE_PRIVATE);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        handler.removeCallbacksAndMessages(null);
    }
}
