package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;

/**
 * Created by lijunjie on 2018/5/25.
 * 账号安全
 */

public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener{

    private ImageView account_security_img_back , account_security_img , account_security_img_modify_password;

    private TextView account_security_tx_modify_mobile_phone , account_security_tx_modify_password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        initialization();
        binding();
    }

    private void initialization() {
        account_security_img_back = findViewById(R.id.account_security_img_back);
        account_security_img = findViewById(R.id.account_security_img);
        account_security_img_modify_password = findViewById(R.id.account_security_img_modify_password);

        account_security_tx_modify_mobile_phone = findViewById(R.id.account_security_tx_modify_mobile_phone);
        account_security_tx_modify_password = findViewById(R.id.account_security_tx_modify_password);

    }

    private void binding() {
        account_security_img_back.setOnClickListener(this);
        account_security_img.setOnClickListener(this);
        account_security_img_modify_password.setOnClickListener(this);

        account_security_tx_modify_mobile_phone.setOnClickListener(this);
        account_security_tx_modify_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_security_tx_modify_password:
                Intent login_img_button_intent = new Intent(this, SecurityCertificationActivity.class);
                startActivity(login_img_button_intent);
                break;
        }
    }
}
