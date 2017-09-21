package com.bwie.activity.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.bean.Laolishi;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/13 19:20
 */
public class Laolishiapder extends BaseAdapter{

    private List<Laolishi.DatasBean.GoodsListBean> datas;
    private Context context;

    public Laolishiapder(List<Laolishi.DatasBean.GoodsListBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder vh;
        if(view==null){
            vh=new Viewholder();
           view = View.inflate(context, R.layout.laolishiitem, null);
           vh.imag = (ImageView) view.findViewById(R.id.lls_image);
           vh.price= (TextView) view.findViewById(R.id.lls_price);
            vh.title= (TextView) view.findViewById(R.id.lls_title);
            view.setTag(vh);
        }else{
            vh= (Viewholder) view.getTag();
        }
        String goods_image_url = datas.get(i).getGoods_image_url();
        Picasso.with(context).load(goods_image_url).into(vh.imag);
        String goods_price = datas.get(i).getGoods_price();
        vh.price.setText(goods_price);
        String goods_name = datas.get(i).getGoods_name();
        vh.title.setText("￥"+goods_name);
        return view;
    }
    class Viewholder{
        TextView title;
        TextView price;
        ImageView imag;
    }
}
