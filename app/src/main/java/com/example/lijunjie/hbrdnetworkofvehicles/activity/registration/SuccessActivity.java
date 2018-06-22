package com.example.lijunjie.hbrdnetworkofvehicles.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;

import static android.os.Build.VERSION_CODES.O;


/**
 * Created by lijunjie on 2018/5/24.
 * 注册成功
 */

public class SuccessActivity extends BaseActivity implements View.OnClickListener {

    //    private ImageView success_img_back;
    private Button success_button;

    private TextView success_tv_skip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        initialization();
        binding();
    }

    private void initialization() {
//        success_img_back = findViewById(R.id.success_img_back); // 顶部后退图片
        success_button = findViewById(R.id.success_button); // 绑定车辆按钮
        success_tv_skip = findViewById(R.id.success_tv_skip); // 跳过此步骤
    }

    private void binding() {
//        success_img_back.setOnClickListener(this);
        success_button.setOnClickListener(this);
        success_tv_skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.success_img_back: // 顶部后退图片
//                break;

            case R.id.success_button: // 绑定车辆按钮
                Intent register_img_back_intent = new Intent(this, RegisterDindingVehicleActivity.class);
                startActivity(register_img_back_intent);

                break;

            case R.id.success_tv_skip: // 跳过此步骤
                Intent success_tv_skip_intent = new Intent(this, MainActivity.class);
                startActivity(success_tv_skip_intent);

                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent success_tv_skip_intent = new Intent(this, MainActivity.class);
            startActivity(success_tv_skip_intent);
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
