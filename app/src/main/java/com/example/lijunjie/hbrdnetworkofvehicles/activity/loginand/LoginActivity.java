package com.example.lijunjie.hbrdnetworkofvehicles.activity.loginand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.RegisterActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.RetrieveThePasswordActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.User;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;


/**
 * Created by lijunjie on 2018/5/13.
 * 登录界面
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    private RelativeLayout login_rl;

    private ImageView login_img_button , login_img_disappear , login_img_disappear_two , longin_password_lnvisible;

    private EditText login_et_account , login_et_password;

    private TextView login_tx_forget_the_password , login_tx_register;

    private Button login_button;

    boolean eyeOpen = false ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
        binding();
        initView();
    }


    private void initialization() {
        login_rl = findViewById(R.id.login_rl);

        login_img_button = findViewById(R.id.login_img_button);

        login_img_disappear = findViewById(R.id.login_img_disappear);
        login_img_disappear_two = findViewById(R.id.login_img_disappear_two);

        longin_password_lnvisible = findViewById(R.id.longin_password_lnvisible);

        login_et_account = findViewById(R.id.login_et_account);
        login_et_password = findViewById(R.id.login_et_password);

        login_tx_forget_the_password = findViewById(R.id.login_tx_forget_the_password);
        login_tx_register = findViewById(R.id.login_tx_register);

        login_button = findViewById(R.id.login_button);

    }

    private void binding() {
        login_rl.setOnClickListener(this);

        login_img_button.setOnClickListener(this);

        login_img_disappear.setOnClickListener(this);
        login_img_disappear_two.setOnClickListener(this);
        longin_password_lnvisible.setOnClickListener(this);

        login_et_account.setOnClickListener(this);
        login_et_password.setOnClickListener(this);

        login_tx_forget_the_password.setOnClickListener(this);
        login_tx_register.setOnClickListener(this);

        login_button.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_rl:
                break;

            case R.id.login_img_button:
                Intent login_img_button_intent = new Intent(this, MainActivity.class);
                startActivity(login_img_button_intent);
                break;

            case R.id.login_et_account:
                break;

            case R.id.login_et_password:

                break;

            case R.id.login_img_disappear:
                login_et_account.setText("");

                break;

            case R.id.login_img_disappear_two:
                login_et_password.setText("");

                break;

            case R.id.longin_password_lnvisible:
                imgPasswordDisappearMonitor();

                break;

            case R.id.login_tx_forget_the_password:
                Intent login_tx_forget_the_password_intent = new Intent(this, RetrieveThePasswordActivity.class);
                startActivity(login_tx_forget_the_password_intent);
                break;

            case R.id.login_tx_register:
                Intent login_tx_register_intent = new Intent(this, RegisterActivity.class);
                startActivity(login_tx_register_intent);

                break;

            case R.id.login_button:
                logonJudgementMonitor();
                break;
        }
    }


    /**
     * 登录按钮判断监听
     */
    private void logonJudgementMonitor() {
        String url = "http://1517a91z44.iask.in:35052/landing";
        String account = login_et_account.getText().toString();
        String password = login_et_password.getText().toString();


        if (account.equals("") && password.equals("")){
            Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            showProgressDialog();
            FormBody formBody = new FormBody
                    .Builder()
                    .add("name", account)
                    .add("pass", password)
                    .add("Consumer","Oneself")
                    .build();
            NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
                @Override
                public void onError(Request request, IOException e) {
                    Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onSuccess(Request request, String result) {
                    Log.d("TAG", "lss" + result);
                    jsonAnalytic(result);
                    dismiss();
                }
            });
        }
    }

    /**
     * 解析后台传过来的Json数据
     * @param result
     */
    private void jsonAnalytic(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            String Status = jsonObject.getString("Status");
            if(Status.equals("ok")){
                String jsonObject2 = jsonObject.getString("user");
                if (jsonObject2.equals("false")) {
                    Toast.makeText(this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                }else {
                    jsonObject = jsonObject.getJSONObject("user");
                    User user = new User();
                    user.setOneselIdCard(jsonObject.getString("oneselIdCard"));
                    user.setOneselIdName(jsonObject.getString("oneselIdName"));
                    user.setOneselfPhone(jsonObject.getString("oneselfPhone"));
                    user.setOneselfSerial(jsonObject.getString("oneselfSerial"));
                    user.setOneselfName(jsonObject.getString("oneselfName"));

                    //缓存用户ID
                    SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name",user.getOneselfName());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }else if (Status.equals("error")){
                Toast.makeText(this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示密码图片监听
     */
    private void imgPasswordDisappearMonitor() {
        longin_password_lnvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( eyeOpen ){
                    login_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    longin_password_lnvisible.setImageResource(R.drawable.img_longin_password_lnvisible);
                    eyeOpen = false ;
                }else {
                    login_et_password.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    longin_password_lnvisible.setImageResource( R.drawable.img_longin_password_so );
                    eyeOpen = true ;
                }
            }
        });
    }


    /**
     * 输入框判断监听
     */
    private void initView() {
        login_et_account.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});//设置限制长度，多了输入不了

        login_et_account.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0){
                    login_img_disappear.setVisibility(View.VISIBLE);
                }else {
                    login_img_disappear.setVisibility(View.GONE);
                }
            }
        });


        login_et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0){
                    login_img_disappear_two.setVisibility(View.VISIBLE);
                }else {
                    login_img_disappear_two.setVisibility(View.GONE);
                }
            }
        });

    }

}
