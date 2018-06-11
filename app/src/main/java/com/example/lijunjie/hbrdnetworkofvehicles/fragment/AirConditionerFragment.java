package com.example.lijunjie.hbrdnetworkofvehicles.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;

/**
 * Created by lijunjie on 2018/5/31.
 */

public class AirConditionerFragment extends Fragment implements View.OnClickListener{



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airconditioner, container, false);
        binding();
        return view;
    }

    private void binding() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_bt_obtain_verification:
                break;

        }
    }


}
