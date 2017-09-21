package com.bwie.activity.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.bean.Laolishi;
import com.bwie.activity.bean.Xiangqing;
import com.bwie.activity.utils.Laolishiapder;
import com.bwie.activity.utils.OkHttp3Utils;
import com.bwie.activity.utils.SharedUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/13 21:09
 */
public class Tiaozhuangwc extends AppCompatActivity implements View.OnClickListener {
    private String ss="http://169.254.229.178/mobile/index.php?act=goods&op=goods_list&page=100";
    private String path="http://169.254.229.178/mobile/index.php?act=goods&op=goods_detail";
    private String addgwc="http://169.254.229.178/mobile/index.php?act=member_cart&op=cart_add";
    private List<String> arr_images;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:

                    String json = (String) msg.obj;

                    Gson gson=new Gson();
                    Xiangqing xiangqing = gson.fromJson(json, Xiangqing.class);
                    Xiangqing.DatasBean datas = xiangqing.getDatas();
                    List<Xiangqing.DatasBean.GoodsCommendListBean> goods_commend_list = datas.getGoods_commend_list();
                   for(int i=0;i<goods_commend_list.size();i++){
                       goodsCommendListBean = goods_commend_list.get(i);
                       String goods_image_url = goodsCommendListBean.getGoods_image_url();
                       arr_images.add(goods_image_url);

                   }
                    String goods_name = goodsCommendListBean.getGoods_name();
                    String goods_promotion_price = goodsCommendListBean.getGoods_promotion_price();
                    tiaozhaun_price.setText("￥"+goods_promotion_price);
                    tiaozhuan_text.setText(goods_name);
                    pw_text.setText("￥"+goods_promotion_price);

                    Log.i("asdfg",arr_images.toString());
                    xingqing_vp.setAdapter(new PagerAdapter() {
                        @Override
                        public int getCount() {
                            return arr_images.size();
                        }

                        @Override
                        public boolean isViewFromObject(View view, Object object) {
                            return view==object;
                        }

                        @Override
                        public Object instantiateItem(ViewGroup container, int position) {
                            ImageView imageView=new ImageView(Tiaozhuangwc.this);
                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            Picasso.with(Tiaozhuangwc.this).load(  arr_images.get(position)).into(imageView);
                            container.addView(imageView);
                            return imageView;
                        }

                        @Override
                        public void destroyItem(ViewGroup container, int position, Object object) {
                            container.removeView((View)object);
                        }
                    });
              break;
                case 1:
                    String obj1 = (String) msg.obj;
                    Gson gson1=new Gson();
                    Laolishi laolishi = gson1.fromJson(obj1, Laolishi.class);
                    Laolishi.DatasBean data = laolishi.getDatas();
                    final List<Laolishi.DatasBean.GoodsListBean> goods_list = data.getGoods_list();
                    Laolishiapder laolishi1=new Laolishiapder(goods_list,Tiaozhuangwc.this);
                    tiaozhuan_vp.setAdapter(laolishi1);


                    break;
                case 2:
                    String obj = (String) msg.obj;

                    Log.i("asdf",obj);
                    break;
            }
        }
    };
    private ViewPager xingqing_vp;
    private TextView tiaozhuan_text;
    private Xiangqing.DatasBean.GoodsCommendListBean goodsCommendListBean;
    private TextView tiaozhaun_price;
    private ListView tiaozhuan_vp;
    private Button addgouwuche;
    private View view;
    private PopupWindow popupWindow;
    private ImageView pw_image;
    private TextView pw_text;
    private Button addcart_pop_add;
    private Button addcart_pop_jian;
    private TextView addcart_pop_goodnum;
    private Button gwc_add;
    private String stringExtra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.taiozhuangwc);
        xingqing_vp = (ViewPager) findViewById(R.id.xingqing_vp);
        tiaozhuan_text = (TextView) findViewById(R.id.tiaozhuan_text);
        tiaozhaun_price = (TextView) findViewById(R.id.tiaozhaun_price);
        tiaozhuan_vp = (ListView) findViewById(R.id.tiaozhuan_vp);
        addgouwuche = (Button) findViewById(R.id.addgouwuche);
        view = View.inflate(Tiaozhuangwc.this, R.layout.pwview,null);
        pw_image = (ImageView) view.findViewById(R.id.pw_image);
        pw_text = (TextView) view.findViewById(R.id.pw_text);
        addcart_pop_add = (Button) view.findViewById(R.id.addcart_pop_add);
        addcart_pop_jian = (Button) view.findViewById(R.id.addcart_pop_jian);
        addcart_pop_goodnum = (TextView) view.findViewById(R.id.addcart_pop_goodnum);
        gwc_add = (Button) view.findViewById(R.id.gwc_add);
        arr_images=new ArrayList<>();
        Intent intent = getIntent();
        stringExtra = intent.getStringExtra("goods_id");
        String imag = intent.getStringExtra("imag");
        String url=path+"&goods_id="+ stringExtra;
        getdata(url);
        Log.i("asdf",url);
        getserverdata(ss);
        addgouwuche.setOnClickListener(this);
        addcart_pop_add.setOnClickListener(this);
        addcart_pop_jian.setOnClickListener(this);
        addcart_pop_goodnum.setOnClickListener(this);
        gwc_add.setOnClickListener(this);
        Picasso.with(Tiaozhuangwc.this).load(imag).into(pw_image);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        //设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置焦点
        popupWindow.setFocusable(true);
        //可触摸
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new poponDismissListener());
    }

    private void getserverdata(String ss) {
        OkHttp3Utils.doGet(ss, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message=new Message();
                message.what=1;
                message.obj=string;
                handler.sendMessage(message);
            }
        });
    }

    private void getdata(String url) {
        OkHttp3Utils.doGet(url, new Callback() {
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addcart_pop_jian:
                int num1=Integer.parseInt(addcart_pop_goodnum.getText().toString());
                num1--;
                if(num1<2)
                {

                    return;
                }
                addcart_pop_goodnum.setText(num1+"");
                break;
            case R.id.addcart_pop_add:
                int num = Integer.parseInt(addcart_pop_goodnum.getText().toString());
                num++;
                addcart_pop_goodnum.setText(num+"");

                break;
            case R.id.addgouwuche:
                //判断popupWindow是否是显示状态
                if(popupWindow.isShowing()){
                    //消失
                    popupWindow.dismiss();
                }else{
                    backgroundAlpha(0.5f);
                    popupWindow.showAsDropDown(addgouwuche, 0, -550);
                }
                break;
            case R.id.gwc_add:



                 getaddgwc(addgwc);
                popupWindow.dismiss();
                break;
        }

    }

    private void getaddgwc(String s) {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Tiaozhuangwc.this, "loginkey", "sdfg");
        Log.i("kkkk",key);
        String string = addcart_pop_goodnum.getText().toString();

        Map<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("goods_id",stringExtra);
        map.put("quantity",string);
        Log.i("ddff",stringExtra);
        Log.i("fff",string);
        OkHttp3Utils.doPost(addgwc, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message=new Message();
                message.what=2;
                message.obj=string;
                handler.sendMessage(message);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }
}
