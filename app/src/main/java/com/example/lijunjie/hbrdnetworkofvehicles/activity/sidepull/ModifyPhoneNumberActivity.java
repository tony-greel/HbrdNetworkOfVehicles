package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;

/**
 * Created by lijunjie on 2018/6/6.
 * 修改电话号码
 */

public class ModifyPhoneNumberActivity extends BaseActivity implements View.OnClickListener{

    private ImageView modify_phone_number_img_back;

    private TextView modify_phone_number_tv_phone_number;

    private Button modify_phone_number_vehicle_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_phone_number);
        initialization();
        binding();
        dataDocking();

    }

    private void dataDocking() {
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String phone = sp.getString("phone", null);
        modify_phone_number_tv_phone_number.setText(phone);
    }

    private void initialization() {
        modify_phone_number_img_back = findViewById(R.id.modify_phone_number_img_back);

        modify_phone_number_tv_phone_number = findViewById(R.id.modify_phone_number_tv_phone_number);

        modify_phone_number_vehicle_button = findViewById(R.id.modify_phone_number_vehicle_button);
    }

    private void binding() {
        modify_phone_number_img_back.setOnClickListener(this);
        modify_phone_number_tv_phone_number.setOnClickListener(this);
        modify_phone_number_vehicle_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modify_phone_number_img_back:
                finish();
                break;

            case R.id.modify_phone_number_tv_phone_number:
                break;

            case R.id.modify_phone_number_vehicle_button:
                break;

        }
    }
}
