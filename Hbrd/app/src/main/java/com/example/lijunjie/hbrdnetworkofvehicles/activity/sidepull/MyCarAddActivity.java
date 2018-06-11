package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.adapter.VehiclesAdapter;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunjie on 2018/5/19.
 * 我的车辆
 */

public class  MyCarAddActivity extends BaseActivity {

    public RecyclerView recyclerView;

    private VehiclesAdapter adapter;


    @Override
    public void  onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_vehicles);

        recyclerView = findViewById(R.id.adding_vehicles_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new VehiclesAdapter();
        recyclerView.setAdapter(adapter);

        List<CarInfo> carInfos = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            carInfos.add(new CarInfo());
        }
        adapter.setCarInfos(carInfos);

    }
}
