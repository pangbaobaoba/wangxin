package com.bwie.activity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.activity.R;
import com.bwie.activity.utils.SharedUtil;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Vector;


/**
 * 1.类的用途
 * 2.@author 胖宝宝:王欣
 * 3.@date 2017/9/17 14:01
 */

public class Shouhuodizhi extends AppCompatActivity{
    private String cityString;
    private String areaString;
    private String cityIdString;
    private String areaIdString;
    private String provinceString;

    private ImageView leftImageView;
    private TextView titleTextView;
    private Button rightImageView;

    private Spinner provinceSpinner;
    private Spinner citySpinner;
    private Spinner areaSpinner;

    private EditText addressEditText;
    private EditText mobilePhoneEditText;
    private EditText trueNameEditText;
    String path="http://169.254.229.178/mobile/index.php?act=member_address&op=address_add";
    private CheckBox defaultCheckBox;
    String url="http://169.254.229.178/mobile/index.php?act=area&op=index";
    private List<String> provinceIdVector;
    private List<String> provinceNameVector;
    private List<String> cityIdVector;
    private List<String> cityNameVector;
    private List<String> areaIdVector;
    private List<String> areaNameVector;

    private void createControl() {

        //mActivity = this;

        //控件实例化

        rightImageView = (Button) findViewById(R.id.rightImageView);

        provinceSpinner = (Spinner) findViewById(R.id.provinceSpinner);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        areaSpinner = (Spinner) findViewById(R.id.areaSpinner);

        addressEditText = (EditText) findViewById(R.id.addressEditText);
        mobilePhoneEditText = (EditText) findViewById(R.id.mobilePhoneEditText);
        trueNameEditText = (EditText) findViewById(R.id.trueNameEditText);

        defaultCheckBox = (CheckBox) findViewById(R.id.defaultCheckBox);




        //一些子程序
        getProvinceJson();

    }

    private void createEvent() {



        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressEditText.getText().toString().isEmpty()) {
                    Toast.makeText(Shouhuodizhi.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobilePhoneEditText.getText().toString().isEmpty()) {
                    Toast.makeText(Shouhuodizhi.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (trueNameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(Shouhuodizhi.this, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                addAddress();
            }
        });

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(14.0f);
                getCityJson(provinceIdVector.get(i));
                provinceString = provinceNameVector.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(14.0f);
                getAreaJson(cityIdVector.get(i));
                cityIdString = cityIdVector.get(i);
                cityString = cityNameVector.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(14.0f);
                areaIdString = areaIdVector.get(i);
                areaString = areaNameVector.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getProvinceJson() {


        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Shouhuodizhi.this, "loginkey", "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("key", key);
        FinalHttp finalhttp=new FinalHttp();
        finalhttp.post(url, ajaxParams, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);
                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    String datas = jsonObject.getString("datas");
                    jsonObject = new JSONObject(datas);
                    parseProvince(jsonObject.getString("area_list"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });

    }

    private void parseProvince(String json) {

        try {
            provinceIdVector = new Vector<>();
            provinceNameVector = new Vector<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                provinceIdVector.add(jsonObject.getString("area_id"));
                provinceNameVector.add(jsonObject.getString("area_name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceNameVector);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provinceSpinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getCityJson(final String area_id) {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Shouhuodizhi.this, "loginkey", "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("key",key);
        ajaxParams.put("area_id", area_id);
        FinalHttp mfinalHttp=new FinalHttp();
        mfinalHttp.post(url, ajaxParams, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    String datas = jsonObject.getString("datas");
                    jsonObject = new JSONObject(datas);
                    parseCity(jsonObject.getString("area_list"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }
    private void parseCity(String json) {

        try {
            cityIdVector = new Vector<>();
            cityNameVector = new Vector<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                cityIdVector.add(jsonObject.getString("area_id"));
                cityNameVector.add(jsonObject.getString("area_name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityNameVector);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getAreaJson(final String area_id) {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Shouhuodizhi.this, "loginkey", "");
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("key",key);
        ajaxParams.put("area_id", area_id);
        FinalHttp mFinalHttp=new FinalHttp();
        mFinalHttp.post(url, ajaxParams, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    String datas = jsonObject.getString("datas");
                    jsonObject = new JSONObject(datas);
                    parseArea(jsonObject.getString("area_list"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);

            }
        });

    }
    private void parseArea(String json) {

        try {
            areaIdVector = new Vector<>();
            areaNameVector = new Vector<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                areaIdVector.add(jsonObject.getString("area_id"));
                areaNameVector.add(jsonObject.getString("area_name"));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, areaNameVector);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            areaSpinner.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void addAddress() {
        SharedUtil instances = SharedUtil.getInstances();
        String key = (String) instances.getValueByKey(Shouhuodizhi.this, "loginkey", "");

        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("key",key);
        ajaxParams.put("true_name", trueNameEditText.getText().toString());
        ajaxParams.put("mob_phone", mobilePhoneEditText.getText().toString());
        ajaxParams.put("city_id", cityIdString);
        ajaxParams.put("area_id", areaIdString);
        ajaxParams.put("address", addressEditText.getText().toString());
        ajaxParams.put("area_info", provinceString + " " + cityString + " " + areaString);
        if (defaultCheckBox.isChecked()) {
            ajaxParams.put("is_default", "1");
        } else {
            ajaxParams.put("is_default", "0");
        }
        FinalHttp mFinalHttp=new FinalHttp();
        mFinalHttp.post(path, ajaxParams, new AjaxCallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                super.onSuccess(o);

                try {
                    JSONObject jsonObject = new JSONObject(o.toString());
                    String datas = jsonObject.getString("datas");
                    if (datas.contains("失败")) {
                        Toast.makeText(Shouhuodizhi.this, "添加失败，请重试", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("datas",datas);
                        Toast.makeText(Shouhuodizhi.this, "添加成功", Toast.LENGTH_SHORT).show();
                        Shouhuodizhi.this.setResult(RESULT_OK);
                        //finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_adress);
        createControl();
        createEvent();
    }



}
