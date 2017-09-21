package com.bwie.activity.bean;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/15 18:48
 */
public class Ground {
    private Boolean aBoolean;
    private String g_store;

    public Ground(Boolean aBoolean, String g_store) {
        this.aBoolean = aBoolean;
        this.g_store = g_store;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public String getG_store() {
        return g_store;
    }

    public void setG_store(String g_store) {
        this.g_store = g_store;
    }

    @Override
    public String toString() {
        return "Ground{" +
                "aBoolean=" + aBoolean +
                ", g_store='" + g_store + '\'' +
                '}';
    }
}
