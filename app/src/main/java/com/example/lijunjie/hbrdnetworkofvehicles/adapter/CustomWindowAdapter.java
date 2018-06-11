package com.example.lijunjie.hbrdnetworkofvehicles.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CustomWindowMyCar;

import java.util.List;

/**
 * Created by lijunjie on 2018/5/22.
 */

public class CustomWindowAdapter extends BaseAdapter {

    private List<CustomWindowMyCar> itemList ;
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;


    public CustomWindowAdapter(Context mContext, List<CustomWindowMyCar> itemList) {
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.itemList = itemList;
    }

    public void setCarInfos(List<CustomWindowMyCar> carInfos) {
        this.itemList = carInfos;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.from(mContext).inflate(R.layout.cusomwindow_item, null);
            viewHolder.mIvIcon = (ImageView) convertView.findViewById(R.id.iv_icon1);
            viewHolder.mTvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.mViewDivider = convertView.findViewById(R.id.view_divider);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvTitle.setText(itemList.get(position).getTitle());
        viewHolder.mIvIcon.setImageDrawable(itemList.get(position).getImgDrawable());
//            convertView.setBackgroundColor(Color.parseColor(itemList.get(position).getColorString()));
        if (position == itemList.size() - 1){
            viewHolder.mViewDivider.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mViewDivider.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class ViewHolder{
        TextView mTvTitle;
        ImageView mIvIcon;
        View mViewDivider;
    }
}
