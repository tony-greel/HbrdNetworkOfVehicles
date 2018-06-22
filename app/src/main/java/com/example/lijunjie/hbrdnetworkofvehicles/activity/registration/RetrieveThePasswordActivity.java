package com.example.lijunjie.hbrdnetworkofvehicles.activity.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;

/**
 * Created by lijunjie on 2018/6/5.
 * 找回密码
 */

public class RetrieveThePasswordActivity extends BaseActivity implements View.OnClickListener{

    private ImageView retrieve_the_password_img_back, retrieve_the_password_img_button, retrieve_the_password_img_disappear_four;

    private Button retrieve_the_password_bt_obtain_verification, retrieve_the_password_button;

    private EditText retrieve_the_password_et_phone_number, retrieve_the_password_et_verification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrievethepassword);
        initialization();
        binding();
        initView();
    }

    private void initialization() {
        retrieve_the_password_img_back = findViewById(R.id.retrieve_the_password_img_back);
        retrieve_the_password_img_button = findViewById(R.id.retrieve_the_password_img_button);
        retrieve_the_password_img_disappear_four = findViewById(R.id.retrieve_the_password_img_disappear_four);

        retrieve_the_password_bt_obtain_verification = findViewById(R.id.retrieve_the_password_bt_obtain_verification); // 获取验证码按钮
        retrieve_the_password_button = findViewById(R.id.retrieve_the_password_button); // 提交密码

        retrieve_the_password_et_phone_number = findViewById(R.id.retrieve_the_password_et_phone_number); // 手机号输入框
        retrieve_the_password_et_verification = findViewById(R.id.retrieve_the_password_et_verification); // 验证码输入框
    }

    private void binding() {
        retrieve_the_password_img_back.setOnClickListener(this);
        retrieve_the_password_img_button.setOnClickListener(this);
        retrieve_the_password_img_disappear_four.setOnClickListener(this);

        retrieve_the_password_bt_obtain_verification.setOnClickListener(this);
        retrieve_the_password_button.setOnClickListener(this);

        retrieve_the_password_et_phone_number.setOnClickListener(this);
        retrieve_the_password_et_verification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retrieve_the_password_bt_obtain_verification:
                break;

            case R.id.retrieve_the_password_button:
                break;

            case R.id.retrieve_the_password_img_disappear_four:
                retrieve_the_password_et_phone_number.setText("");
                break;

            case R.id.retrieve_the_password_img_back:
                finish();
                break;

            case R.id.retrieve_the_password_img_button:
                Intent register_img_button_intent = new Intent(this, MainActivity.class);
                startActivity(register_img_button_intent);
                finish();
                break;
        }
    }


    private void initView() {
        retrieve_the_password_et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    retrieve_the_password_img_disappear_four.setVisibility(View.VISIBLE);
                } else {
                    retrieve_the_password_img_disappear_four.setVisibility(View.GONE);
                }
            }
        });
    }
}
