package com.example.lijunjie.hbrdnetworkofvehicles.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;


/**
 * Created by lijunjie on 2018/5/24.
 * 注册成功
 */

public class SuccessActivity extends BaseActivity implements View.OnClickListener{

    private ImageView success_img_back;
    private Button success_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initialization();
        binding();
    }

    private void initialization() {
        success_img_back = findViewById(R.id.success_img_back); // 顶部后退图片
        success_button = findViewById(R.id.success_button); // 绑定车辆按钮
    }

    private void binding() {
        success_img_back.setOnClickListener(this);
        success_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.success_img_back: // 顶部后退图片
                Intent success_img_back_intent = new Intent(this, RegisterActivity.class);
                startActivity(success_img_back_intent);

                break;

            case R.id.success_button: // 绑定车辆按钮
                Intent register_img_back_intent = new Intent(this, RegisterDindingVehicleActivity.class);
                startActivity(register_img_back_intent);

                break;
        }
    }
}
