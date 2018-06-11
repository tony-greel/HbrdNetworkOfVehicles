package com.example.lijunjie.hbrdnetworkofvehicles.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

/**
 * Created by lijunjie on 2018/5/31.
 * 车控界面
 */

public class CarControlFragment extends Fragment {
    private ZwCanvas zwCanvas;
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

        zwCanvas = view.findViewById(R.id.zw);
    }
    private void setView(){
        two_layout.setBackgroundDrawable(btnDrawable);

        zwCanvas.setRelativeLayout(two_layout);

        ViewOutlineProvider viewOutlineProvider=new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                //x，y轴表示位置，后两个参数表示长，宽
                outline.setOval (0,100,mainlayout.getWidth(),mainlayout.getHeight());
                outline.setAlpha(0.9f);
            }
        };
        mainlayout.setOutlineProvider(viewOutlineProvider);
        zwCanvas.setZwCanvasListener(new ZwCanvasListener() {
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

            @Override
            public void butdown() {
                rotateyAnimRun(mainlayout);
            }

            @Override
            public void butup() {
                rotateyAnimRun1(mainlayout);
            }
        });
    }
    public void rotateyAnimRun(View view) {
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.90f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotationX", 0.0F, 38.0F);
        ObjectAnimator translate = ObjectAnimator.ofFloat(view, "translationY", 0F, mainlayout.getHeight()/2-100);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rotation,scaleY,scaleX,translate);
        animatorSet.start();
    }
    public void rotateyAnimRun1(View view) {

        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.90f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotationX", 38.0F, 0F);
        ObjectAnimator translate = ObjectAnimator.ofFloat(view, "translationY", mainlayout.getHeight()/2-100, 0F);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rotation,scaleY,scaleX,translate);
        animatorSet.start();
    }
}
