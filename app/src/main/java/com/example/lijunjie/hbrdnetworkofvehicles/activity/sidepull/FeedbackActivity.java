package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;

/**
 * Created by lijunjie on 2018/5/25.
 * 反馈
 */

public class FeedbackActivity extends BaseActivity implements View.OnClickListener{

    private ImageView feedback_img_back;

    private EditText feedback_information , feedback_user_contact_mode;

    private Button feedback_submission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initialization();
        binding();
    }

    private void initialization() {
        feedback_img_back = findViewById(R.id.feedback_img_back);

        feedback_information = findViewById(R.id.feedback_information);
        feedback_user_contact_mode = findViewById(R.id.feedback_user_contact_mode);

        feedback_submission = findViewById(R.id.feedback_submission);
    }

    private void binding() {
        feedback_img_back.setOnClickListener(this);

        feedback_information.setOnClickListener(this);
        feedback_user_contact_mode.setOnClickListener(this);

        feedback_submission.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedback_img_back:
                finish();
                break;
        }
    }
}
