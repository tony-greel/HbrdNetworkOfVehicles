package com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.adapter.VehiclesAdapter;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CarInformation;
import com.example.lijunjie.hbrdnetworkofvehicles.util.HbrdJsonParse;
import com.example.lijunjie.hbrdnetworkofvehicles.util.OnRecyclerItemClickListenerUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lijunjie on 2018/5/19.
 * 我的车辆
 */

public class  MyCarAddActivity extends BaseActivity implements View.OnClickListener{

    public RecyclerView adding_vehicles_recycler;

    private VehiclesAdapter adapter;

    private ImageView my_car_img_back;

    @Override
    public void  onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_vehicles);
        initialization();
        requestVehicleInformation(getApplicationContext());
        binding();

    }

    /**
     * 初始化
     */
    private void initialization() {
        adding_vehicles_recycler = findViewById(R.id.adding_vehicles_recycler);
        adding_vehicles_recycler.setLayoutManager(new GridLayoutManager(this,2));

        my_car_img_back = findViewById(R.id.my_car_img_back);
    }

    /**
     * 绑定
     */
    private void binding() {
        my_car_img_back.setOnClickListener(this);
    }

    /**
     * 监听
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.adding_vehicles_recycler:
                adding_vehicles_recycler.addOnItemTouchListener(new OnRecyclerItemClickListenerUtil(adding_vehicles_recycler) {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                        Toast.makeText(MyCarAddActivity.this, "点击", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onItemLOngClick(RecyclerView.ViewHolder viewHolder) {
                        Toast.makeText(MyCarAddActivity.this, "长按删除", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });

            case R.id.my_car_img_back:
                finish();
                break;
        }
    }


    /**
     * 请求车辆信息
     * @param context
     */
    private void requestVehicleInformation(final Context context){
        String url = "http://1517a91z44.iask.in:35052/carInformation";

        SharedPreferences sp = this.getSharedPreferences("sp_demo", Context.MODE_PRIVATE);
        String name = sp.getString("name", null);
        showProgressDialog();
        FormBody formBody = new FormBody
                .Builder()
                .add("UserSerial", name)
                .build();
        NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
            @Override
            public void onError(Request request, IOException e) {
                Toast.makeText(getApplicationContext(), "服务器连接失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(Request request, String result) {
                Log.d("TAG", "lss" + result);
                adapter = new VehiclesAdapter(context,jsonAnalytic(result));
                adding_vehicles_recycler.setAdapter(adapter);
                dismiss();
            }
        });

        List<CarInformation> carInformations = new ArrayList<>();
        for (int i = 0; i < carInformations.size(); i++) {
            carInformations.add(new CarInformation());
        }
    }

    /**
     * 解析后台返回的车辆信息
     * @param result
     * @return
     */
    private List<CarInformation> jsonAnalytic(String result) {
        try {
            return (List<CarInformation>) HbrdJsonParse.getJson(result, "carInformations", false, CarInformation.class);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        List<CarInformation> list=new ArrayList<>();
        if (result == null){
            Toast.makeText(getApplicationContext(), "您还未添加车辆信息，请添加后再来", Toast.LENGTH_SHORT).show();
        }else {
            try{
                JSONObject jsonObject = new JSONObject(result);

                JSONArray children = jsonObject.getJSONArray("carInformations");
                for (int j = 0; j < children.length(); j++) {
                    jsonObject = children.getJSONObject(j);

                    CarInformation carInformation = new CarInformation();

                    carInformation.setCarInformationSerial(jsonObject.getString("carInformationSerial"));
                    carInformation.setCarInformationBrand(jsonObject.getString("carInformationBrand"));
                    carInformation.setCarInformationMake(jsonObject.getString("carInformationMake"));
                    carInformation.setCarInformationModel(jsonObject.getString("carInformationModel"));
                    list.add(carInformation);
                    Log.d("WL", carInformation.getCarInformationBrand());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
