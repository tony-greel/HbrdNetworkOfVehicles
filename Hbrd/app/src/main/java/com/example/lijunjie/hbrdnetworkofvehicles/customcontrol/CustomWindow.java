package com.example.lijunjie.hbrdnetworkofvehicles.customcontrol;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.adapter.CustomWindowAdapter;
import com.example.lijunjie.hbrdnetworkofvehicles.bean.CustomWindowMyCar;
import com.example.lijunjie.hbrdnetworkofvehicles.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunjie on 2018/5/25.
 * 自定义列表
 */


public class CustomWindow {

    private Context mContext;
    private PopupWindow popupWindow;

    private View contentView;

    private ListView mLvMenuList;
    private CustomWindowAdapter customWindowAdapter;

    private OnItemSelectListener onItemSelectListener;

    private List<CustomWindowMyCar> list ;

    public interface OnItemSelectListener{
        void onItemSelect(int position);
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener){
        this.onItemSelectListener = onItemSelectListener;
    }

    public CustomWindow(Context mContext){
        this.mContext = mContext;
        list = new ArrayList<>();
        initPopWindow();
    }

    /**
     * 初始化popwindow菜单
     */
    private void initPopWindow(){
        contentView = LayoutInflater.from(mContext).inflate(R.layout.cusomwindow, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.PopupAnimation);


        mLvMenuList = (ListView) contentView.findViewById(R.id.lv_menu_1);
        customWindowAdapter = new CustomWindowAdapter(mContext,list);
        mLvMenuList.setAdapter(customWindowAdapter);
        mLvMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemSelectListener != null){
                    if(list.get(position).getTag() > 0){
                        onItemSelectListener.onItemSelect(list.get(position).getTag());
                    }else{
                        onItemSelectListener.onItemSelect(position);
                    }
                }
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 设置菜单列表数据源
     * @param itemList
     */
    public void setItemList(List<CustomWindowMyCar> itemList){
        this.list = itemList;
        customWindowAdapter.setCarInfos(itemList);
    }

    public void showMenu(){
        if (popupWindow == null)
            return;
        // 状态栏的高度
        Rect frame = new Rect();
        AppCompatActivity activity = (AppCompatActivity)mContext;
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, mContext.getResources().getDisplayMetrics());
        if (activity.getSupportActionBar() != null){
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP| Gravity.RIGHT, 0, activity.getSupportActionBar().getHeight() + frame.top - dp);
        } else {
            popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.TOP| Gravity.RIGHT, 0, CommonUtil.dip2px(mContext, 48) + frame.top - dp);
        }
    }
}
