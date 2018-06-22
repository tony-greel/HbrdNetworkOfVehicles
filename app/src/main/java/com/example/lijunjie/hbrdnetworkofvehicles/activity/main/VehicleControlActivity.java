package com.example.lijunjie.hbrdnetworkofvehicles.activity.main;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.adapter.FragmentAdapter;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.MyViewPager;
import com.example.lijunjie.hbrdnetworkofvehicles.fragment.AirConditionerFragment;
import com.example.lijunjie.hbrdnetworkofvehicles.fragment.CarControlFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 实时监控
 */
public class VehicleControlActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vehicle_control_pager;
    private ImageView vehicle_control_img_back;
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
        vehicle_control_pager = findViewById(R.id.vehicle_control_pager);
        vehicle_control_img_back = findViewById(R.id.vehicle_control_img_back);
    }

    /**
     * 控件监听初始化
     */
    private void binding() {
        vehicle_control_pager.setOnClickListener(this);
        vehicle_control_img_back.setOnClickListener(this);
    }

    /**
     * Viewpager滑动操作
     */
    private void SlidingOperation() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new AirConditionerFragment());
        fragments.add(new CarControlFragment());

        adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        vehicle_control_pager.setCurrentItem(0);
        vehicle_control_pager.setAdapter(adapter);
//        vehicle_control_pager.setAllowedSwipeDirection(MyViewPager.SwipeDirection.right);
//        vehicle_control_pager.setAllowedSwipeDirection(MyViewPager.SwipeDirection.left);


        vehicle_control_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vehicle_control_img_back:
                finish();
                break;
        }
    }
}
