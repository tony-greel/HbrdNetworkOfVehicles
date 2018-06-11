package com.example.lijunjie.hbrdnetworkofvehicles.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.activity.sidepull.AddingVehiclesActivity;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CarInfo;

import java.util.List;

/**
 * Created by lijunjie on 2018/5/19.
 * 添加车辆适配器
 */

public class VehiclesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CarInfo> carInfos;

    public void setCarInfos(List<CarInfo> carInfos) {
        this.carInfos = carInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(carInfos==null&&position==0||carInfos!=null&&position==carInfos.size()){
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
            vehiclesViewHolder.load(carInfos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return carInfos==null ? 1:carInfos.size()+1;
    }


    class VehiclesViewHolder extends RecyclerView.ViewHolder{


        public VehiclesViewHolder(View itemView) {
            super(itemView);

        }
        public void load(CarInfo carInfo){
//            tvId.setText(carInfo.getId());
        }
    }


    class AddViewHolder extends RecyclerView.ViewHolder{

        ImageView ivAdd;

        public AddViewHolder(final View itemView) {
            super(itemView);
            ivAdd = itemView.findViewById(R.id.iv_add_vehicles);
            ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AddingVehiclesActivity.class);
                    view.getContext().startActivity(intent);
                }
            });
        }

    }


}
