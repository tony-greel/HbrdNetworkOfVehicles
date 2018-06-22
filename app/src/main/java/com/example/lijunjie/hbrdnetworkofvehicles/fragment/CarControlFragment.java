package com.example.lijunjie.hbrdnetworkofvehicles.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.ZwCanvas;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.ZwCanvasListener;
import com.example.lijunjie.hbrdnetworkofvehicles.customcontrol.ZwTwoCanvas;

/**
 * Created by lijunjie on 2018/5/31.
 * 车控界面
 */

public class CarControlFragment extends Fragment {
    private ZwTwoCanvas zwTwoCanvas;
    private RelativeLayout mainlayout,two_layout;

    private Resources resources ;
    private Drawable btnDrawable;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carcontrol, container, false);
        init(view);
        setView();
        return view;
    }
    private void init(View view){
        two_layout = view.findViewById(R.id.two_layout);
        mainlayout = view.findViewById(R.id.mainlayout);

        resources = getActivity().getResources();
        btnDrawable = resources.getDrawable(R.mipmap.canvansback);

        zwTwoCanvas = view.findViewById(R.id.zw);
    }
    private void setView(){
        two_layout.setBackgroundDrawable(btnDrawable);

        zwTwoCanvas.setRelativeLayout(mainlayout);
        zwTwoCanvas.setZwCanvasListener(new ZwCanvasListener() {
            @Override
            public void up() {
                Toast.makeText(getContext(), "上滑动", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void left() {
                Toast.makeText(getContext(), "左滑动", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void right() {
                Toast.makeText(getContext(), "右滑动", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
