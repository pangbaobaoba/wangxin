package com.bwie.activity.bean;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/11 14:06
 */
public class User {
    private Boolean bb;
    private String text;

    public Boolean getBb() {
        return bb;
    }

    public void setBb(Boolean bb) {
        this.bb = bb;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User(Boolean bb, String text) {
        this.bb = bb;
        this.text = text;
    }


}
