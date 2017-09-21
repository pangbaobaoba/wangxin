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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.bean.GoodsBean;
import com.bwie.activity.bean.Souhuo;
import com.bwie.activity.utils.OkHttp3Utils;
import com.bwie.activity.utils.PayApder;
import com.bwie.activity.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/16 13:42
 */
public class Payactivity extends AppCompatActivity implements View.OnClickListener {

    private ListView pay_listview;
    private Button pay_tijiao;
    private String path="http://169.254.229.178/mobile/index.php?act=member_buy&op=buy_step1";
    private ImageView pay_image;
    private String s="http://169.254.229.178/mobile/index.php?act=member_address&op=address_list";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String obj = (String) msg.obj;
                    Log.i("sdf",obj);
                    Gson gson=new Gson();
                    Souhuo souhuo = gson.fromJson(obj, Souhuo.class);
                    Souhuo.DatasBean datas = souhuo.getDatas();
                    List<Souhuo.DatasBean.AddressListBean> address_list = datas.getAddress_list();
                    Souhuo.DatasBean.AddressListBean addressListBean = address_list.get(0);
                    String true_name = addressListBean.getTrue_name();
                    String area_info = addressListBean.getArea_info();
                    String mob_phone = addressListBean.getMob_phone();
                    pay_shzhe.setText(true_name);
                    pay_address.setText(area_info);
                    pay_phonenum.setText(mob_phone);
                    break;
                case 1:
                    String obj1 = (String) msg.obj;
                    Log.i("dingdan",obj1);
                    break;

            }
        }
    };
    private TextView pay_shzhe;
    private TextView pay_address;
    private TextView pay_phonenum;
    private String card_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payactivity);
        //获取id
        pay_listview = (ListView) findViewById(R.id.pay_listview);
        pay_tijiao = (Button) findViewById(R.id.pay_tijiao);
        pay_image = (ImageView) findViewById(R.id.pay_image);
        pay_shzhe = (TextView) findViewById(R.id.pay_shzhe);
        pay_address = (TextView) findViewById(R.id.pay_address);
        pay_phonenum = (TextView) findViewById(R.id.pay_phonenum);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        List<GoodsBean> goodsBean = (List<GoodsBean>) bundle.getSerializable("goodsBean");
        Log.i("list",goodsBean.toString());
        card_id = null;
        for(int i=0;i<goodsBean.size();i++){
            if(i==0){
              card_id =goodsBean.get(i).getCard_id()+"|"+goodsBean.get(i).getCount();
            }else{
                card_id +=","+goodsBean.get(i).getCard_id()+"|"+goodsBean.get(i).getCount();
            }


        }
        PayApder apder=new PayApder(goodsBean,Payactivity.this);
        pay_listview.setAdapter(apder);
        pay_tijiao.setOnClickListener(this);
        pay_image.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getaddress(s);
    }

    private void getaddress(String s) {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Payactivity.this, "loginkey", "sdfg");
        Map<String,String> map=new HashMap<>();
        map.put("key",key);
        OkHttp3Utils.doPost(s, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  od=response.body().string();
                Message message=new Message();
                message.what=0;
                message.obj=od;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pay_tijiao:
                getTijiao(path);
                Intent intent1=new Intent(Payactivity.this,Zhifu.class);
                startActivity(intent1);
            break;
            case R.id.pay_image:
            Intent intent=new Intent(Payactivity.this,Shouhuodizhi.class);
                startActivity(intent);

                break;
        }


    }

    private void getTijiao(String path) {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Payactivity.this, "loginkey", "sdfg");
        Map<String,String> map=new HashMap<>();
        map.put("key",key);
        map.put("cart_id",card_id);
        map.put("ifcart","1");

        OkHttp3Utils.doPost(path, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String  od=response.body().string();
                Message message=new Message();
                message.what=1;
                message.obj=od;
                handler.sendMessage(message);
            }
        });
    }
}
