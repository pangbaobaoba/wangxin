package com.bwie.activity.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bwie.activity.R;
import com.bwie.activity.custum.CustumView;

public class MainActivity extends AppCompatActivity {

    private Button bt_color;
    private CustumView custunview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_color = (Button) findViewById(R.id.button);
        custunview = (CustumView) findViewById(R.id.custunview);

    }
}
