package com.bwie.activity.custum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 1.自定义View类
 * 2.@author 王欣
 * 3.@date 2017/9/4 08:44
 */
public class CustumView extends View {

    //定义画笔和画布
    private  Paint paint;
    private Context context;

    public CustumView(Context context) {
        super(context);
        this.context=context;

    }



    public CustumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.paint=new Paint();//实例化画笔
       this.paint.setAntiAlias(true);//消除锯齿
        this.paint.setStyle(Paint.Style.STROKE);//设置空圆心
        init(context,attrs);

    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        int conter=getWidth()/2;//定义宽度
        int banjng=dip2px(context,85);//设置圆内半径
        int waiyuan=dip2px(context,2);//设置外圆半径
        //绘制内圆
        this.paint.setARGB(150,170,190,160);
        this.paint.setStrokeWidth(2);
        canvas.drawCircle(conter,conter,banjng,this.paint);
       //绘制外框
        this.paint.setARGB(250,210,230,220);
        this.paint.setStrokeWidth(waiyuan);
        canvas.drawCircle(conter,conter,banjng+1+waiyuan/2,this.paint);
        //绘制外圆
        this.paint.setARGB(150,160,190,200);
        this.paint.setStrokeWidth(2);
        this.paint.setColor(Color.RED);
        canvas.drawCircle(conter,conter,banjng+waiyuan,this.paint);
        super.onDraw(canvas);
    }

    private int dip2px(Context context, int i) {
        float density = context.getResources().getDisplayMetrics().density;

        return (int)(density*i+0.5f);
    }

}
