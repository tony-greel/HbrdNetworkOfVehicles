package com.example.lijunjie.hbrdnetworkofvehicles.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by lijunjie on 2018/5/25.
 */

public class CustomWindowMyCar {
    private Drawable imgDrawable;
    private String title;
    private boolean visible;
    private int color;
    private int tag;

    public CustomWindowMyCar(Drawable imgDrawable, String title, boolean visible, int color) {
        this.imgDrawable = imgDrawable;
        this.title = title;
        this.visible = visible;
        this.color = color;
    }

    public CustomWindowMyCar(Drawable imgDrawable, String title) {
        this.imgDrawable = imgDrawable;
        this.title = title;
    }

    public CustomWindowMyCar(Drawable imgDrawable, String title, int tag) {
        this.imgDrawable = imgDrawable;
        this.title = title;
        this.tag = tag;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(Drawable imgDrawable) {
        this.imgDrawable = imgDrawable;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
