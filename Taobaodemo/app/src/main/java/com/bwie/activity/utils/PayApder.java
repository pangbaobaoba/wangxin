package com.bwie.activity.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.activity.R;
import com.bwie.activity.bean.GoodsBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/16 15:05
 */
public class PayApder extends BaseAdapter{
    private List<GoodsBean> list;
    private Context context;

    public PayApder(List<GoodsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
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
            view= View.inflate(context, R.layout.payitem, null);
            vh.imageView= (ImageView) view.findViewById(R.id.pay_imag);
            vh.miaosu= (TextView) view.findViewById(R.id.pay_miaosu);
            vh.price= (TextView) view.findViewById(R.id.pay_price);
            vh.num= (TextView) view.findViewById(R.id.pay_num);
            vh.stroe= (TextView) view.findViewById(R.id.pay_store);
            view.setTag(vh);
        }else {
            vh= (Viewholder) view.getTag();
        }
        Picasso.with(context).load(list.get(i).getImageLogo()).into(vh.imageView);
        vh.stroe.setText(list.get(i).getDesc());
        vh.miaosu.setText(list.get(i).getName());
        vh.price.setText(list.get(i).getPrice()+"");
        vh.num.setText(list.get(i).getCount()+"");
        return view;
    }

    class Viewholder{
        ImageView imageView;
        TextView miaosu;
        TextView price;
        TextView num;
        TextView stroe;
    }

}
