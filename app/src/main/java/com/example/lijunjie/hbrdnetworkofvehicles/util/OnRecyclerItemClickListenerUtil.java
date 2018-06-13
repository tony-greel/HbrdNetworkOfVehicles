package com.example.lijunjie.hbrdnetworkofvehicles.util;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lijunjie on 2018/6/12.
 */

public abstract class OnRecyclerItemClickListenerUtil implements RecyclerView.OnItemTouchListener {

    private final RecyclerView recyclerView;
    private final GestureDetectorCompat mGestureDetector;


    public OnRecyclerItemClickListenerUtil(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }


    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);

    public abstract void onItemLOngClick(RecyclerView.ViewHolder viewHolder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent event) {
            View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemClick(viewHolder);
            }
            return true;
        }

        public void onLongPress(MotionEvent event) {
            View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
            if (child != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(child);
                onItemLOngClick(viewHolder);
            }
        }

    }
}
