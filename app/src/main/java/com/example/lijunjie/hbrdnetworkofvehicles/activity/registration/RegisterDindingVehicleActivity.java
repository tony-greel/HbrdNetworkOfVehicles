package com.example.lijunjie.hbrdnetworkofvehicles.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;

/**
 * Created by lijunjie on 2018/5/24.
 * 绑定车辆
 */

public class RegisterDindingVehicleActivity extends BaseActivity implements View.OnClickListener{

    private EditText registered_bound_vehicle_et_account , registered_bound_vehicle_et_license_plate ,
            registered_bound_vehicle_et_frame , registered_bound_vehicle_et_car_color;

    private ImageView register_bound_img_back  , registered_bound_vehicle_img_disappear ,
            registered_bound_vehicle_img_disappear_two , registered_bound_vehicle_img_disappear_three ,
            registered_bound_vehicle_img_disappear_four;

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
        registered_bound_vehicle_et_account = findViewById(R.id.registered_bound_vehicle_et_account); // 车牌号输入框
        registered_bound_vehicle_et_license_plate = findViewById(R.id.registered_bound_vehicle_et_license_plate); // 汽车品牌输入框
        registered_bound_vehicle_et_frame = findViewById(R.id.registered_bound_vehicle_et_frame); // 汽车型号输入框
        registered_bound_vehicle_et_car_color = findViewById(R.id.registered_bound_vehicle_et_car_color); // 汽车颜色输入框

        register_bound_img_back = findViewById(R.id.register_bound_img_back); // 顶部后退图片
        registered_bound_vehicle_img_disappear = findViewById(R.id.registered_bound_vehicle_img_disappear); // 车牌号输入框右边的清除图片
        registered_bound_vehicle_img_disappear_two = findViewById(R.id.registered_bound_vehicle_img_disappear_two); // 汽车品牌输入框右边的清除图片
        registered_bound_vehicle_img_disappear_three = findViewById(R.id.registered_bound_vehicle_img_disappear_three); // 汽车型号输入框右边的的清除图片
        registered_bound_vehicle_img_disappear_four = findViewById(R.id.registered_bound_vehicle_img_disappear_four); // 汽车颜色输入框右边的的清除图片

        registered_bound_vehicle_button = findViewById(R.id.registered_bound_vehicle_button); // 立即绑定按钮
    }


    /**
     * 监听初始化
     */
    private void binding() {
        //输入框
        registered_bound_vehicle_et_account.setOnClickListener(this);
        registered_bound_vehicle_et_license_plate.setOnClickListener(this);
        registered_bound_vehicle_et_frame.setOnClickListener(this);
        registered_bound_vehicle_et_car_color.setOnClickListener(this);

        //图片
        register_bound_img_back.setOnClickListener(this);
        registered_bound_vehicle_img_disappear.setOnClickListener(this);
        registered_bound_vehicle_img_disappear_two.setOnClickListener(this);
        registered_bound_vehicle_img_disappear_three.setOnClickListener(this);
        registered_bound_vehicle_img_disappear_four.setOnClickListener(this);

        registered_bound_vehicle_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered_bound_vehicle_et_account: // 车牌号输入框
                break;

            case R.id.registered_bound_vehicle_et_license_plate: // 汽车品牌输入框
                break;

            case R.id.registered_bound_vehicle_et_frame: // 汽车型号输入框
                break;

            case R.id.registered_bound_vehicle_et_car_color: // 汽车颜色输入框
                break;

            case R.id.register_bound_img_back: // 顶部后退图片
                Intent registered_bound_vehicle_img_back_intent = new Intent(this,SuccessActivity.class);
                startActivity(registered_bound_vehicle_img_back_intent);
                break;

            case R.id.registered_bound_vehicle_img_disappear: // 车牌号输入框右边的清除图片
                registered_bound_vehicle_et_account.setText("");

                break;

            case R.id.registered_bound_vehicle_img_disappear_two: // 汽车品牌输入框右边的清除图片
                registered_bound_vehicle_et_license_plate.setText("");

                break;

            case R.id.registered_bound_vehicle_img_disappear_three: // 汽车型号输入框右边的的清除图片
                registered_bound_vehicle_et_frame.setText("");

                break;

            case R.id.registered_bound_vehicle_img_disappear_four: // 汽车颜色输入框右边的的清除图片
                registered_bound_vehicle_et_car_color.setText("");

                break;

            case R.id.registered_bound_vehicle_button: // 立即绑定按钮
                Intent registered_bound_vehicle_button_intent = new Intent(this,MainActivity.class);
                startActivity(registered_bound_vehicle_button_intent);
                finish();
                break;

        }
    }


    /**
     * 输入框的监听
     */
    private void initView() {
        /**
         * 车牌号输入框右边的清除图片监听
         */
        registered_bound_vehicle_et_account.addTextChangedListener(new TextWatcher() {
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
        registered_bound_vehicle_et_license_plate.addTextChangedListener(new TextWatcher() {
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
        registered_bound_vehicle_et_frame.addTextChangedListener(new TextWatcher() {
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
        registered_bound_vehicle_et_car_color.addTextChangedListener(new TextWatcher() {
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
