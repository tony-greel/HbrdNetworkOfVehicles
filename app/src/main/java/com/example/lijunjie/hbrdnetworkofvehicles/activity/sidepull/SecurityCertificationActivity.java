package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.util.CountDownTimerUtils;


/**
 * Created by lijunjie on 2018/5/30.
 * 安全认证
 */

public class SecurityCertificationActivity extends BaseActivity implements View.OnClickListener{

    private ImageView security_certification_img_back;

    private EditText security_certification_et_verification;

    private Button security_certification_verification , security_certification_vehicle_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_certification);
        initialization();
        binding();
    }

    private void initialization() {
        security_certification_img_back = findViewById(R.id.security_certification_img_back);

        security_certification_et_verification = findViewById(R.id.security_certification_et_verification);

        security_certification_verification = findViewById(R.id.security_certification_verification);
        security_certification_vehicle_button = findViewById(R.id.security_certification_vehicle_button);
    }

    private void binding() {
        security_certification_img_back.setOnClickListener(this);

        security_certification_et_verification.setOnClickListener(this);
        security_certification_verification.setOnClickListener(this);
        security_certification_vehicle_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.security_certification_img_back:
                finish();
                break;

            case R.id.security_certification_verification:
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(security_certification_verification, 60000, 1000);
                mCountDownTimerUtils.start();
                break;

            case R.id.security_certification_vehicle_button:
                Intent login_img_button_intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(login_img_button_intent);
                break;
        }
    }
}
