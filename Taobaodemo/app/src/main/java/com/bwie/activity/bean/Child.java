package com.bwie.activity.bean;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/15 19:06
 */
public class Child {
    private Boolean aBoolean;
    private String imag;
    private String sp_name;
    private String price;
    private String num;

    public Child(Boolean aBoolean, String imag, String sp_name, String price, String num) {
        this.aBoolean = aBoolean;
        this.imag = imag;
        this.sp_name = sp_name;
        this.price = price;
        this.num = num;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }

    public String getSp_name() {
        return sp_name;
    }

    public void setSp_name(String sp_name) {
        this.sp_name = sp_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Child() {
    }
}
