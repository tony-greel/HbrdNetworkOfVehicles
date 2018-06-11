package com.example.lijunjie.hbrdnetworkofvehicles.activity.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.util.AuthenticationIdNumberUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lijunjie on 2018/5/24.
 * 绑定车辆
 */

public class RegisterDindingVehicleActivity extends BaseActivity implements View.OnClickListener{

    private EditText registered_bound_vehicle_et_serial_number , registered_bound_vehicle_et_vehicle_license_plate_number ,
            registered_bound_vehicle_et_brand , registered_bound_vehicle_et_car_model , registered_bound_vehicle_et_frame_number;

    private ImageView register_bound_img_back  , registered_bound_vehicle_img_disappear ,
            registered_bound_vehicle_img_disappear_two , registered_bound_vehicle_img_disappear_three ,
            registered_bound_vehicle_img_disappear_four , registered_bound_vehicle_img_serial_number;

    private Button registered_bound_vehicle_button ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_bound_vehicle);
        initialization();
        binding();
        initView();
    }


    /**
     * 初始化
     */
    private void initialization() {
        registered_bound_vehicle_et_serial_number = findViewById(R.id.registered_bound_vehicle_et_serial_number); // 序列号
        registered_bound_vehicle_et_vehicle_license_plate_number = findViewById(R.id.registered_bound_vehicle_et_vehicle_license_plate_number); // 车牌号
        registered_bound_vehicle_et_brand = findViewById(R.id.registered_bound_vehicle_et_brand); // 车辆品牌
        registered_bound_vehicle_et_car_model = findViewById(R.id.registered_bound_vehicle_et_car_model); // 车辆型号
        registered_bound_vehicle_et_frame_number = findViewById(R.id.registered_bound_vehicle_et_frame_number); // 车架号

        register_bound_img_back = findViewById(R.id.register_bound_img_back); // 顶部后退图片
        registered_bound_vehicle_img_disappear = findViewById(R.id.registered_bound_vehicle_img_disappear); // 车牌号输入框右边的清除图片
        registered_bound_vehicle_img_disappear_two = findViewById(R.id.registered_bound_vehicle_img_disappear_two); // 汽车品牌输入框右边的清除图片
        registered_bound_vehicle_img_disappear_three = findViewById(R.id.registered_bound_vehicle_img_disappear_three); // 汽车型号输入框右边的的清除图片
        registered_bound_vehicle_img_disappear_four = findViewById(R.id.registered_bound_vehicle_img_disappear_four); // 汽车颜色输入框右边的的清除图片
        registered_bound_vehicle_img_serial_number = findViewById(R.id.registered_bound_vehicle_img_serial_number); // 汽车颜色输入框右边的的清除图片

        registered_bound_vehicle_button = findViewById(R.id.registered_bound_vehicle_button); // 立即绑定按钮
    }


    /**
     * 监听初始化
     */
    private void binding() {
        //输入框
        registered_bound_vehicle_et_serial_number.setOnClickListener(this);
        registered_bound_vehicle_et_vehicle_license_plate_number.setOnClickListener(this);
        registered_bound_vehicle_et_brand.setOnClickListener(this);
        registered_bound_vehicle_et_car_model.setOnClickListener(this);
        registered_bound_vehicle_et_frame_number.setOnClickListener(this);

        //图片
        register_bound_img_back.setOnClickListener(this);
        registered_bound_vehicle_img_disappear.setOnClickListener(this);
        registered_bound_vehicle_img_disappear_two.setOnClickListener(this);
        registered_bound_vehicle_img_disappear_three.setOnClickListener(this);
        registered_bound_vehicle_img_disappear_four.setOnClickListener(this);
        registered_bound_vehicle_img_serial_number.setOnClickListener(this);

        registered_bound_vehicle_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered_bound_vehicle_et_serial_number: // 序列号
                break;

            case R.id.registered_bound_vehicle_et_vehicle_license_plate_number: // 车牌号
                break;

            case R.id.registered_bound_vehicle_et_brand: // 车辆品牌
                break;

            case R.id.registered_bound_vehicle_et_car_model: // 车辆型号
                break;

            case R.id.registered_bound_vehicle_et_frame_number: // 车架号
                break;

            case R.id.register_bound_img_back: // 顶部后退图片
                Intent registered_bound_vehicle_img_back_intent = new Intent(this,SuccessActivity.class);
                startActivity(registered_bound_vehicle_img_back_intent);
                break;

            case R.id.registered_bound_vehicle_img_disappear: // 序列号输入框右边的清除图片
                registered_bound_vehicle_et_serial_number.setText("");

                break;

            case R.id.registered_bound_vehicle_img_disappear_two: // 车牌号输入框右边的清除图片
                registered_bound_vehicle_et_vehicle_license_plate_number.setText("");

                break;

            case R.id.registered_bound_vehicle_img_disappear_three: // 车辆品牌输入框右边的的清除图片
                registered_bound_vehicle_et_brand.setText("");

                break;

            case R.id.registered_bound_vehicle_img_disappear_four: // 车辆型号输入框右边的的清除图片
                registered_bound_vehicle_et_car_model.setText("");

                break;

            case R.id.registered_bound_vehicle_button: // 立即绑定按钮
                boundVehicles();
                break;

        }
    }

    private void boundVehicles() {
        String url = "http://1517a91z44.iask.in:35052/InsertAddCar";

        String serial_number = registered_bound_vehicle_et_serial_number.getText().toString();
        String vehicle_license_plate_number = registered_bound_vehicle_et_vehicle_license_plate_number.getText().toString().trim();
        String brand = registered_bound_vehicle_et_brand.getText().toString();
        String car_model = registered_bound_vehicle_et_car_model.getText().toString();
        String frame_number = registered_bound_vehicle_et_frame_number.getText().toString();

        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        Log.d("TAH",name);

        if (serial_number.equals("") || vehicle_license_plate_number.equals("") ||
                brand.equals("") || car_model.equals("") ||
                frame_number.equals("")) {
            Toast.makeText(RegisterDindingVehicleActivity.this, "请输入完整要绑定的车辆信息！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AuthenticationIdNumberUtil.iscarbrand(vehicle_license_plate_number)) {
            Toast.makeText(this, "车牌号码格式不正确，请输入正确格式", Toast.LENGTH_SHORT).show();
            return;
        }

        if (serial_number.length() > 10){
            Toast.makeText(this, "序列号必须大于10位", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RegisterDindingVehicleActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                Log.d("LJJ",e.getMessage());
            }
            @Override
            public void onSuccess(Request request, String result) {
                Log.d("TAG", "lss" + result);
                jsonAnalytic(result);
                dismiss();
            }
        });

    }

    private void jsonAnalytic(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            String Status = jsonObject.getString("Status");
            if(Status.equals("error")) {
                Toast.makeText(this, "车牌号或序列号重复请重新输入", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(RegisterDindingVehicleActivity.this, MainActivity.class);
                startActivity(intent);
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
         * 车牌号输入框右边的清除图片监听
         */
        registered_bound_vehicle_et_serial_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    registered_bound_vehicle_img_disappear.setVisibility(View.VISIBLE);
                }else {
                    registered_bound_vehicle_img_disappear.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 汽车品牌输入框右边的清除图片监听
         */
        registered_bound_vehicle_et_vehicle_license_plate_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    registered_bound_vehicle_img_disappear_two.setVisibility(View.VISIBLE);
                }else {
                    registered_bound_vehicle_img_disappear_two.setVisibility(View.GONE);
                }
            }
        });

        /**
         * 汽车型号输入框右边的的清除图片监听
         */
        registered_bound_vehicle_et_brand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    registered_bound_vehicle_img_disappear_three.setVisibility(View.VISIBLE);
                }else {
                    registered_bound_vehicle_img_disappear_three.setVisibility(View.GONE);
                }
            }
        });


        /**
         * 汽车颜色输入框右边的的清除图片监听
         */
        registered_bound_vehicle_et_car_model.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0){
                    registered_bound_vehicle_img_disappear_four.setVisibility(View.VISIBLE);
                }else {
                    registered_bound_vehicle_img_disappear_four.setVisibility(View.GONE);
                }
            }
        });


    }
}
