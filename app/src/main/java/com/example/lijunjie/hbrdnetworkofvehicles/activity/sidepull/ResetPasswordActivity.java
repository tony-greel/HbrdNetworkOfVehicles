package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.loginand.LoginActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.RegisterActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.RegisterDindingVehicleActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.util.AuthenticationIdNumberUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.OkHttpAsk;
import com.example.lijunjie.hbrdnetworkofvehicles.util.OkHttpAskListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunjie on 2018/5/30.
 * 重置密码
 */

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener{

    private Button reset_password_vehicle_button;
    private EditText reset_password_et_verification;
    private ImageView reset_password_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initialization();
        binding();
    }

    private void initialization() {
        reset_password_img_back = findViewById(R.id.reset_password_img_back);

        reset_password_et_verification = findViewById(R.id.reset_password_et_verification);

        reset_password_vehicle_button = findViewById(R.id.reset_password_vehicle_button);
    }

    private void binding() {
        reset_password_img_back.setOnClickListener(this);

        reset_password_et_verification.setOnClickListener(this);

        reset_password_vehicle_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset_password_img_back:
                finish();
                break;

            case R.id.reset_password_vehicle_button:
                requestModifyPassword();
                break;
        }
    }

    private void requestModifyPassword() {
        String url = "http://1517a91z44.iask.in:35052/UpdateUserPass";
        String password = reset_password_et_verification.getText().toString();

        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        Log.d("EE",name);
        
        if(password.equals("")){
            Toast.makeText(this, "输入框为空，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!AuthenticationIdNumberUtil.Ispass(password)){
            Toast.makeText(this, "重置密码必须大于6位", Toast.LENGTH_SHORT).show();
            return;
        }
            showProgressDialog();
            Map<String,String> map= new HashMap();
            map.put("Consumer","Oneself");
            map.put("UserSerial",name);
            map.put("UserPass",password);
            OkHttpAsk.requestNet(url, map, new OkHttpAskListener() {
                @Override
                public void onSuccess(String str) {
                    dismiss();
                    jsonAnalytic(str);
                }

                @Override
                public void onError() {
                    Toast.makeText(ResetPasswordActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
            
    }

    private void jsonAnalytic(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);

            String Status = jsonObject.getString("Status");
            if (Status.equals("ok")) {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ResetPasswordActivity.this, AccountSecurityActivity.class);
                startActivity(intent);
            }else if (Status.equals("error")){
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
