package com.example.lijunjie.hbrdnetworkofvehicles.activity.registration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.activity.loginand.LoginActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.Oneself;
import com.example.lijunjie.hbrdnetworkofvehicles.util.AuthenticationIdNumberUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.CountDownTimerUtils;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lijunjie on 2018/5/23.
 * 注册
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText register_et_full_name, register_et_id, register_et_account, register_et_password, register_et_again_password,
            register_et_phone_number, register_et_verification;


    private ImageView register_img_full_name, register_img_id, register_img_disappear, register_password_lnvisible,
            register_password_lnvisible_two, register_img_disappear_four,
            register_img_back, register_img_button;


    private Button register_bt_obtain_verification, register_button;

    boolean eyeOpen = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialization();
        binding();
        initView();
    }


    /**
     * 初始化
     */
    private void initialization() {
        register_et_full_name = findViewById(R.id.register_et_full_name); // 姓名输入框
        register_et_id = findViewById(R.id.register_et_id); // 身份证输入框
        register_et_account = findViewById(R.id.register_et_account); // 账号输入框
        register_et_password = findViewById(R.id.register_et_password); // 密码输入框
        register_et_again_password = findViewById(R.id.register_et_again_password); // 确认密码输入框
        register_et_phone_number = findViewById(R.id.register_et_phone_number); // 手机号输入框
        register_et_verification = findViewById(R.id.register_et_verification); // 验证码输入框

        register_img_back = findViewById(R.id.register_img_back); // 顶部后退图片
        register_img_button = findViewById(R.id.register_img_button); // 顶部退出图片

        register_img_full_name = findViewById(R.id.register_img_full_name); // 姓名输入框右边的清除图片
        register_img_id = findViewById(R.id.register_img_id); // 身份证输入框右边的清除图片

        register_img_disappear = findViewById(R.id.register_img_disappear); // 账号输入框右边的清除图片
        register_password_lnvisible = findViewById(R.id.register_password_lnvisible); // 密码输入框右边的显示密码图片

        register_password_lnvisible_two = findViewById(R.id.register_password_lnvisible_two); // 确认密码输入框右边的显示密码图片
        register_img_disappear_four = findViewById(R.id.register_img_disappear_four); // 手机号输入框右边的清除图片

        register_bt_obtain_verification = findViewById(R.id.register_bt_obtain_verification); // 获取验证码按钮
        register_button = findViewById(R.id.register_button); // 注册按钮
    }


    /**
     * 监听初始化
     */
    private void binding() {
        //输入框
        register_et_full_name.setOnClickListener(this);
        register_et_id.setOnClickListener(this);
        register_et_account.setOnClickListener(this);
        register_et_password.setOnClickListener(this);
        register_et_again_password.setOnClickListener(this);
        register_et_phone_number.setOnClickListener(this);
        register_et_verification.setOnClickListener(this);

        //图片
        register_img_back.setOnClickListener(this);
        register_img_button.setOnClickListener(this);

        register_img_full_name.setOnClickListener(this);
        register_img_id.setOnClickListener(this);

        register_img_disappear.setOnClickListener(this);
        register_password_lnvisible.setOnClickListener(this);

        register_password_lnvisible_two.setOnClickListener(this);
        register_img_disappear_four.setOnClickListener(this);

        //按钮
        register_bt_obtain_verification.setOnClickListener(this);
        register_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.register_img_back: // 顶部后退图片
                Intent register_img_back_intent = new Intent(this, LoginActivity.class);
                startActivity(register_img_back_intent);
                break;

            case R.id.register_img_button: // 顶部退出图片
                Intent register_img_button_intent = new Intent(this, MainActivity.class);
                startActivity(register_img_button_intent);
                break;

            case R.id.register_img_full_name: // 账号输入框右边的清除图片
                register_et_full_name.setText("");
                break;

            case R.id.register_img_id: // 手机号输入框右边的清除图片
                register_et_id.setText("");
                break;

            case R.id.register_img_disappear: // 账号输入框右边的清除图片
                register_et_account.setText("");
                break;

            case R.id.register_img_disappear_four: // 手机号输入框右边的清除图片
                register_et_phone_number.setText("");
                break;

            case R.id.register_password_lnvisible: // 密码输入框右边的显示密码图片
                imgPasswordDisappearMonitor();
                break;

            case R.id.register_password_lnvisible_two:  // 确认密码输入框右边的显示密码图片
                imgConfirmPasswordDisappearMonitor();
                break;

            case R.id.register_bt_obtain_verification: // 获取验证码按钮
                getTheVerifyingCode();
                break;

            case R.id.register_button: // 注册按钮
                registerJudgementMonitor();

                break;
        }
    }


    /**
     * 注册按钮判断监听
     */
    private void registerJudgementMonitor() {
        String url = "http://1517a91z44.iask.in:35052/login";

        String account = register_et_account.getText().toString();
        String password = register_et_password.getText().toString().trim();
        String phone_number = register_et_phone_number.getText().toString();
        String id = register_et_id.getText().toString();
        String full_name = register_et_full_name.getText().toString();

        String verification = register_et_verification.getText().toString();
        String again_password = register_et_again_password.getText().toString().trim();

        //缓存用户ID
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",account);
        editor.putString("password",password);
        editor.commit();

        if (account.equals("") || password.equals("") ||
                phone_number.equals("") || id.equals("") ||
                full_name.equals("") || again_password.equals("") ||
                verification.equals("")) {
            Toast.makeText(RegisterActivity.this, "请输入完整的注册信息!！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AuthenticationIdNumberUtil.Isuser(account)) {
            Toast.makeText(this, "用户名格式不正确，需要2~10位包括下划线、英文、中文", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthenticationIdNumberUtil.Ispass(password)) {
            Toast.makeText(this, "密码格式不正确，必须大于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthenticationIdNumberUtil.Isphone(phone_number)) {
            Toast.makeText(this, "手机号码格式不正确，请输入正确格式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AuthenticationIdNumberUtil.Isidcard(id)) {
            Toast.makeText(this, "身份证号码格式不正确，请输入正确格式", Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        FormBody formBody = new FormBody
                .Builder()
                .add("OneselfName", account)
                .add("OneselfPass", password)
                .add("OneselfPhone",phone_number)
                .add("OneselIdCard", id)
                .add("OneselIdName",full_name)
                .build();
        NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
            @Override
            public void onError(Request request, IOException e) {
                Toast.makeText(RegisterActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(Request request, String result) {
                Log.d("TAG", "lss" + result);
                jsonAnalytic(result);
                analogLanding();
                dismiss();
            }
        });
    }

    /**
     *通过返回的json数据来判断用户名这些是否重复
     * @param result
     */
    private void jsonAnalytic(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);

            String Status = jsonObject.getString("Status");
            if (Status.equals("ok")) {
            }else if (Status.equals("error")){
                Toast.makeText(this, "用户名、手机号、身份证重复，请重新输入！", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * 请求服务器成功调此方法，用于模拟登陆，拿到以缓存的ID和密码进行模拟登陆
     */
    private void analogLanding() {
        SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        String password = sp.getString("password", null);

        String url = "http://1517a91z44.iask.in:35052/landing";
        FormBody formBody = new FormBody
                .Builder()
                .add("name", name)
                .add("pass", password)
                .add("Consumer","Oneself")
                .build();
        NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
            @Override
            public void onError(Request request, IOException e) {
                Toast.makeText(RegisterActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(Request request, String result) {
                Log.d("TAG", "wss" + result);
                userJsonData(result);
            }
        });
    }


    /**
     * 解析模拟登陆的Json数据
     * @param result
     */
    private void userJsonData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            jsonObject = jsonObject.getJSONObject("user");
            Oneself user = new Oneself();
            user.setOneselfIdCard(jsonObject.getString("oneselIdCard"));
            user.setOneselfIdName(jsonObject.getString("oneselIdName"));
            user.setOneselfPhone(jsonObject.getString("oneselfPhone"));
            user.setOneselfSerial(jsonObject.getString("oneselfSerial"));
            user.setOneselfName(jsonObject.getString("oneselfName"));

            //缓存用户ID
            SharedPreferences sp = getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name",user.getOneselfSerial());
            editor.putString("phone",user.getOneselfPhone());
            editor.commit();

            Intent intent = new Intent(RegisterActivity.this, RegisterDindingVehicleActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 输入框右边图片监听
     */
    private void imgPasswordDisappearMonitor() {
        register_et_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eyeOpen) {
                    //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
                    register_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    register_password_lnvisible.setImageResource(R.drawable.img_longin_password_lnvisible);
                    eyeOpen = false;
                } else {
                    //明文
                    register_et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    register_password_lnvisible.setImageResource(R.drawable.img_longin_password_so);
                    eyeOpen = true;
                }
            }
        });
    }

    private void imgConfirmPasswordDisappearMonitor() {
        register_password_lnvisible_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (eyeOpen) {
                    //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
                    register_et_again_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    register_password_lnvisible_two.setImageResource(R.drawable.img_longin_password_lnvisible);
                    eyeOpen = false;
                } else {
                    //明文
                    register_et_again_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    register_password_lnvisible_two.setImageResource(R.drawable.img_longin_password_so);
                    eyeOpen = true;
                }
            }
        });
    }


    /**
     * 输入框的监听
     */
    private void initView() {
        register_et_full_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    register_img_full_name.setVisibility(View.VISIBLE);
                } else {
                    register_img_full_name.setVisibility(View.GONE);
                }
            }
        });

        register_et_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    register_img_id.setVisibility(View.VISIBLE);
                } else {
                    register_img_id.setVisibility(View.GONE);
                }
            }
        });

        register_et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    register_img_disappear.setVisibility(View.VISIBLE);
                } else {
                    register_img_disappear.setVisibility(View.GONE);
                }
            }
        });

        register_et_phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    register_img_disappear_four.setVisibility(View.VISIBLE);
                } else {
                    register_img_disappear_four.setVisibility(View.GONE);
                }
            }
        });
    }


    /**
     * 获取验证码按钮
     */
    private void getTheVerifyingCode() {
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(register_bt_obtain_verification, 60000, 1000);
        mCountDownTimerUtils.start();
    }
}
