package com.bwie.activity.bean;

import java.util.List;

/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/17 15:32
 */
public class Souhuo {

    /**
     * code : 200
     * datas : {"address_list":[{"address":"全文二天","address_id":"27","area_id":"37","area_info":"北京 北京市 东城区","city_id":"36","dlyp_id":"0","is_default":"1","member_id":"31","mob_phone":"123456","true_name":"胖宝宝吧"}]}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private List<AddressListBean> address_list;

        public List<AddressListBean> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<AddressListBean> address_list) {
            this.address_list = address_list;
        }

        public static class AddressListBean {
            /**
             * address : 全文二天
             * address_id : 27
             * area_id : 37
             * area_info : 北京 北京市 东城区
             * city_id : 36
             * dlyp_id : 0
             * is_default : 1
             * member_id : 31
             * mob_phone : 123456
             * true_name : 胖宝宝吧
             */

            private String address;
            private String address_id;
            private String area_id;
            private String area_info;
            private String city_id;
            private String dlyp_id;
            private String is_default;
            private String member_id;
            private String mob_phone;
            private String true_name;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getArea_info() {
                return area_info;
            }

            public void setArea_info(String area_info) {
                this.area_info = area_info;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getDlyp_id() {
                return dlyp_id;
            }

            public void setDlyp_id(String dlyp_id) {
                this.dlyp_id = dlyp_id;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMob_phone() {
                return mob_phone;
            }

            public void setMob_phone(String mob_phone) {
                this.mob_phone = mob_phone;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }
        }
    }
}
