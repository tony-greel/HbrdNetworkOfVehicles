package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.RegisterDindingVehicleActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.SuccessActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.LimitEditText;
import com.example.lijunjie.hbrdnetworkofvehicles.util.AuthenticationIdNumberUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lijunjie on 2018/5/30.
 * 添加车辆
 */

public class AddingVehiclesActivity extends BaseActivity implements View.OnClickListener{

    private EditText add_vehicle_et_serial_number , add_vehicle_license_plate_number ,
                     add_vehicle_et_car_model , add_vehicle_et_frame_number;

    private LimitEditText add_vehicle_et_brand;


    private ImageView add_vehicle_img_back  , add_vehicle_img_disappear ,
                      add_vehicle_img_disappear_two , add_vehicle_img_disappear_three ,
                      add_vehicle_img_disappear_four , add_vehicle_img_serial_number;

    private Button add_vehicle_button ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_information);
        initialization();
        binding();
        initView();
    }

    private void initialization() {
        add_vehicle_et_serial_number = findViewById(R.id.add_vehicle_et_serial_number); // 序列号
        add_vehicle_license_plate_number = findViewById(R.id.add_vehicle_license_plate_number); // 车牌号
        add_vehicle_et_brand = findViewById(R.id.add_vehicle_et_brand); // 车辆品牌
        add_vehicle_et_car_model = findViewById(R.id.add_vehicle_et_car_model); // 车辆型号
        add_vehicle_et_frame_number = findViewById(R.id.add_vehicle_et_frame_number); // 车架号

        add_vehicle_img_back = findViewById(R.id.add_vehicle_img_back); // 顶部后退图片
        add_vehicle_img_disappear = findViewById(R.id.add_vehicle_img_disappear); // 车牌号输入框右边的清除图片
        add_vehicle_img_disappear_two = findViewById(R.id.add_vehicle_img_disappear_two); // 汽车品牌输入框右边的清除图片
        add_vehicle_img_disappear_three = findViewById(R.id.add_vehicle_img_disappear_three); // 汽车型号输入框右边的的清除图片
        add_vehicle_img_disappear_four = findViewById(R.id.add_vehicle_img_disappear_four); // 汽车颜色输入框右边的的清除图片
        add_vehicle_img_serial_number = findViewById(R.id.add_vehicle_img_serial_number); // 汽车颜色输入框右边的的清除图片

        add_vehicle_button = findViewById(R.id.add_vehicle_button); // 立即绑定按钮
    }

    private void binding() {
        //输入框
        add_vehicle_et_serial_number.setOnClickListener(this);
        add_vehicle_license_plate_number.setOnClickListener(this);
        add_vehicle_et_brand.setOnClickListener(this);
        add_vehicle_et_car_model.setOnClickListener(this);
        add_vehicle_et_frame_number.setOnClickListener(this);

        //图片
        add_vehicle_img_back.setOnClickListener(this);
        add_vehicle_img_disappear.setOnClickListener(this);
        add_vehicle_img_disappear_two.setOnClickListener(this);
        add_vehicle_img_disappear_three.setOnClickListener(this);
        add_vehicle_img_disappear_four.setOnClickListener(this);
        add_vehicle_img_serial_number.setOnClickListener(this);

        add_vehicle_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_vehicle_img_back: // 顶部后退图片
                finish();
                break;

            case R.id.add_vehicle_img_disappear: // 序列号输入框右边的清除图片
                add_vehicle_et_serial_number.setText("");
                break;

            case R.id.add_vehicle_img_disappear_two: // 车牌号输入框右边的清除图片
                add_vehicle_license_plate_number.setText("");

                break;

            case R.id.add_vehicle_img_disappear_three: // 车辆品牌输入框右边的的清除图片
                add_vehicle_et_brand.setText("");

                break;

            case R.id.add_vehicle_img_disappear_four: // 车辆型号输入框右边的的清除图片
                add_vehicle_et_car_model.setText("");
                break;

            case R.id.add_vehicle_img_serial_number: // 车辆型号输入框右边的的清除图片
                add_vehicle_et_frame_number.setText("");
                break;

            case R.id.add_vehicle_button: // 立即绑定按钮
                boundVehicles();
                break;
        }
    }

    private void boundVehicles() {
        String url = "http://1517a91z44.iask.in:35052/InsertAddCar";

        String serial_number = add_vehicle_et_serial_number.getText().toString();
        String vehicle_license_plate_number = add_vehicle_license_plate_number.getText().toString().trim();
        String brand = add_vehicle_et_brand.getText().toString();
        String car_model = add_vehicle_et_car_model.getText().toString();
        String frame_number = add_vehicle_et_frame_number.getText().toString();

        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);

        if (serial_number.equals("") || vehicle_license_plate_number.equals("") ||
                brand.equals("") || car_model.equals("") ||
                frame_number.equals("")) {
            Toast.makeText(AddingVehiclesActivity.this, "请输入完整要绑定的车辆信息！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (serial_number.length() > 10){
            Toast.makeText(this, "序列号必须大于10位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthenticationIdNumberUtil.iscarbrand(vehicle_license_plate_number)) {
            Toast.makeText(this, "车牌号码格式不正确，请输入正确格式", Toast.LENGTH_SHORT).show();
            return;
        }
        showProgressDialog();
        FormBody formBody = new FormBody
                .Builder()
                .add("Consumer", "Oneself")
                .add("CarInformationSerial", serial_number)
                .add("UserSerial",name)
                .add("CarInformationBrand", vehicle_license_plate_number)
                .add("CarInformationMake",brand)
                .add("CarInformationModel", car_model)
                .add("CarInformationStand",frame_number)
                .add("GroupingId","1")
                .build();
        NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
            @Override
            public void onError(Request request, IOException e) {
                Toast.makeText(AddingVehiclesActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(Request request, String result) {
                Log.d("TAG", "lss" + result);
                jsonAnalytic(result);
                dismiss();
            }
        });
    }

    /**
     * 解析服务器传过来的数据进行判断
     * @param result
     */
    private void jsonAnalytic(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            String Status = jsonObject.getString("Status");
            if(Status.equals("error")) {
                Toast.makeText(this, "车牌号或序列号重复请重新输入", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(AddingVehiclesActivity.this, MyCarAddActivity.class);
                startActivity(intent);
                finish();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    /**
     * 输入框的监听
     */
    private void initView() {
        /**
         * 序列号输入框右边的清除图片监听
         */
        add_vehicle_et_serial_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    add_vehicle_img_disappear.setVisibility(View.VISIBLE);
                } else {
                    add_vehicle_img_disappear.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 车牌号输入框右边的清除图片监听
         */
        add_vehicle_license_plate_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    add_vehicle_img_disappear_two.setVisibility(View.VISIBLE);
                } else {
                    add_vehicle_img_disappear_two.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 车辆品牌输入框右边的的清除图片监听
         */
        add_vehicle_et_brand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    add_vehicle_img_disappear_three.setVisibility(View.VISIBLE);
                } else {
                    add_vehicle_img_disappear_three.setVisibility(View.GONE);
                }
            }
        });


        /**
         * 车辆型号输入框右边的的清除图片监听
         */
        add_vehicle_et_car_model.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    add_vehicle_img_disappear_four.setVisibility(View.VISIBLE);
                } else {
                    add_vehicle_img_disappear_four.setVisibility(View.GONE);
                }
            }
        });


        /**
         * 车架号输入框右边的的清除图片监听
         */
        add_vehicle_et_frame_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    add_vehicle_img_serial_number.setVisibility(View.VISIBLE);
                } else {
                    add_vehicle_img_serial_number.setVisibility(View.GONE);
                }
            }
        });
    }
}
