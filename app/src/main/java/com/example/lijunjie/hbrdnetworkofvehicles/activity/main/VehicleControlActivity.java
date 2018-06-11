package com.example.lijunjie.hbrdnetworkofvehicles.activity.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.adapter.FragmentAdapter;
import com.example.lijunjie.hbrdnetworkofvehicles.fragment.AirConditionerFragment;
import com.example.lijunjie.hbrdnetworkofvehicles.fragment.CarControlFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.sms.BmobSMS;

public class VehicleControlActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager main_viewPager;

    private ImageView img_1 , img_2 , img_3 , img_4;

    private FragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        setContentView(R.layout.activity_vehiclecontrol);
        initialization();
        binding();
        SlidingOperation();

    }


    /**
     * 隐藏状态栏
     */
    private void statusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 26) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 控件绑定
     */
    private void initialization() {
        main_viewPager = findViewById(R.id.main_view_pager);

        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
    }

    /**
     * 控件监听初始化
     */
    private void binding() {
        main_viewPager.setOnClickListener(this);

        img_1.setOnClickListener(this);
        img_2.setOnClickListener(this);
        img_3.setOnClickListener(this);
        img_4.setOnClickListener(this);


    }

    /**
     * Viewpager滑动操作
     */
    private void SlidingOperation() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AirConditionerFragment());
        fragments.add(new CarControlFragment());

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        main_viewPager.setCurrentItem(0);
        main_viewPager.setAdapter(adapter);

        main_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        img_1.setVisibility(View.VISIBLE);
                        img_2.setVisibility(View.VISIBLE);
                        img_3.setVisibility(View.GONE);
                        img_4.setVisibility(View.GONE);
                        break;
                    case 1:
                        img_1.setVisibility(View.GONE);
                        img_2.setVisibility(View.GONE);
                        img_3.setVisibility(View.VISIBLE);
                        img_4.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
