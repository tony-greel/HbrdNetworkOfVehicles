package com.example.lijunjie.hbrdnetworkofvehicles.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull.AccountSecurityActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull.FeedbackActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull.MyCarAddActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.CommomDialog;

/**
 * Created by lijunjie on 2018/5/10.
 * 侧拉框
 */

public class SidePullFrameFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout side_pull_frame_rl_my_car , side_pull_frame_rl_account_security,
                           side_pull_frame_rl_feedback , side_pull_frame_rl_sign_out;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_side_pull_frame, container, false);

        side_pull_frame_rl_my_car = view.findViewById(R.id.side_pull_frame_rl_my_car);
        side_pull_frame_rl_account_security = view.findViewById(R.id.side_pull_frame_rl_account_security);
        side_pull_frame_rl_feedback = view.findViewById(R.id.side_pull_frame_rl_feedback);
        side_pull_frame_rl_sign_out = view.findViewById(R.id.side_pull_frame_rl_sign_out);

        binding();
        return view;
    }

    private void binding() {
        side_pull_frame_rl_my_car.setOnClickListener(this);
        side_pull_frame_rl_account_security.setOnClickListener(this);
        side_pull_frame_rl_feedback.setOnClickListener(this);
        side_pull_frame_rl_sign_out.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.side_pull_frame_rl_my_car:
                Intent side_pull_frame_rl_my_car_intent = new Intent(getContext(), MyCarAddActivity.class);
                startActivity(side_pull_frame_rl_my_car_intent);
                break;
            case R.id.side_pull_frame_rl_account_security:
                Intent side_pull_frame_rl_account_security_intent = new Intent(getContext(), AccountSecurityActivity.class);
                startActivity(side_pull_frame_rl_account_security_intent);
                break;
            case R.id.side_pull_frame_rl_feedback:
                Intent side_pull_frame_rl_feedback_intent = new Intent(getContext(), FeedbackActivity.class);
                startActivity(side_pull_frame_rl_feedback_intent);
                break;

            case R.id.side_pull_frame_rl_sign_out:
                //弹出提示框
                new CommomDialog(getContext(), R.style.dialog, "您确定退出车联网？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            SharedPreferences preferences=getActivity().getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
                            preferences.edit().clear().commit();
                            dialog.dismiss();
                            getActivity().finish();
                            getActivity().overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                        }

                    }
                }).show();
                break;
        }
    }


}
