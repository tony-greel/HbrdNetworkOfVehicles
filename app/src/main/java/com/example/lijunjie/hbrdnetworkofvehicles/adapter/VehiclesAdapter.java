package com.example.lijunjie.hbrdnetworkofvehicles.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.BaseActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.main.MainActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.registration.RegisterActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull.AddingVehiclesActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CarInformation;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.CommomDialog;
import com.example.lijunjie.hbrdnetworkofvehicles.util.OkHttpAsk;
import com.example.lijunjie.hbrdnetworkofvehicles.util.OkHttpAskListener;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtil;
import com.example.lijunjie.hbrdnetworkofvehicles.util.network.NetworkRequestUtilListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by lijunjie on 2018/5/19.
 * 添加车辆适配器
 */

public class VehiclesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CarInformation> carInformations;
    private Context context;

    public VehiclesAdapter(Context context,List<CarInformation> carInformations){
        this.context = context;
        this.carInformations=carInformations;
    }

    @Override
    public int getItemViewType(int position) {
        if(carInformations ==null&&position==0|| carInformations !=null&&position== carInformations.size()){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0){
            return new AddViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_adding,parent,false));
        }else{
            return new VehiclesViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_adding_vehicles,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VehiclesViewHolder){
            VehiclesViewHolder vehiclesViewHolder = (VehiclesViewHolder) holder;
            vehiclesViewHolder.load(carInformations.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return carInformations ==null ? 1: carInformations.size()+1;
    }


    /**
     * 车辆信息item
     */
    class VehiclesViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout adding_vehicles_deletion;

        private TextView adding_vehicles_license_plate , adding_vehicles_vehicle_brand , adding_vehicles_vehicle_model;


        public VehiclesViewHolder(View itemView) {
            super(itemView);
            adding_vehicles_deletion = itemView.findViewById(R.id.adding_vehicles_deletion);

            adding_vehicles_license_plate = itemView.findViewById(R.id.adding_vehicles_license_plate);
            adding_vehicles_vehicle_brand = itemView.findViewById(R.id.adding_vehicles_vehicle_brand);
            adding_vehicles_vehicle_model = itemView.findViewById(R.id.adding_vehicles_vehicle_model);
        }



        public void load(CarInformation carInformation){
            adding_vehicles_license_plate.setText(carInformation.getCarInformationBrand());
            adding_vehicles_vehicle_brand.setText(carInformation.getCarInformationMake());
            adding_vehicles_vehicle_model.setText(carInformation.getCarInformationModel());
            adding_vehicles_deletion.setOnLongClickListener(v -> {
                //弹出提示框
                new CommomDialog(context, R.style.dialog, "您确定删除此车辆信息？", (dialog, confirm) -> {
                    if (confirm) {
                        String url = "http://1517a91z44.iask.in:35052/DeleteCarInformation";

                        FormBody formBody = new FormBody
                                .Builder()
                                .add("UserSerial", carInformation.getUserSerial())
                                .add("CarInformationSerial", carInformation.getCarInformationSerial())
                                .build();
                        NetworkRequestUtil.getInstance().asyncPost(url, formBody, new NetworkRequestUtilListener() {
                            @Override
                            public void onError(Request request, IOException e) {

                            }
                            @Override
                            public void onSuccess(Request request, String result) {
                                Log.d("TAG", "lss" + result);
                                int poi = carInformations.indexOf(carInformation);
                                carInformations.remove(poi);
                                notifyItemRemoved(poi);
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
                return true;
            });
        }
    }


    /**
     * 添加车辆
     */
    class AddViewHolder extends RecyclerView.ViewHolder{

        ImageView ivAdd;

        public AddViewHolder(final View itemView) {
            super(itemView);
            ivAdd = itemView.findViewById(R.id.iv_add_vehicles);
            ivAdd.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), AddingVehiclesActivity.class);
                view.getContext().startActivity(intent);
            });
        }
    }
}
