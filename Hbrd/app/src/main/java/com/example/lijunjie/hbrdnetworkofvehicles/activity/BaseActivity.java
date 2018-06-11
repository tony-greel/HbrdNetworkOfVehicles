package com.example.lijunjie.hbrdnetworkofvehicles.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lijunjie.hbrdnetworkofvehicles.R;


/**
 * Created by lijunjie on 2018/5/11.
 */

public class BaseActivity extends AppCompatActivity {

    private Dialog progressDialog;

    public static final int PERMISSION_DENIEG = 1;//权限不足，权限被拒绝的时候
    private static final String PACKAGE_URL_SCHEME = "package:";//权限方案

    public Activity mActivity;

    /**
     * 加载
     */
    public void showProgressDialog(){
        if (progressDialog==null){
            progressDialog = new Dialog(this,R.style.progress_dialog);
            progressDialog.setContentView(R.layout.dialog_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
            msg.setText("加载中");
            progressDialog.show();
        }
        progressDialog.show();
    }

    public void dismiss(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }


    /**
     * 定位权限
     */
    public boolean getLocationPre() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                             Manifest.permission.READ_EXTERNAL_STORAGE,
                             Manifest.permission.WRITE_EXTERNAL_STORAGE,
                             Manifest.permission.RECEIVE_SMS,
                             Manifest.permission.READ_SMS}, 1);
            return false;
        } else {
            return true;
        }
    }


    /**
     * 外部存储权限
     */
    public boolean getReadExternalMemory() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ,Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS}, 1);
            return false;
        } else {
            return true;
        }
    }


    /**
     * 内部存储权限
     */
    public boolean getReadInternalMemory() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 短信权限
     */
    public boolean getShortMessageMemory() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS}, 1);
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        showMissingPermissionDialog(requestCode, grantResults);
    }


    /**
     * 权限缺少时弹出对话框
     * @param requestCode
     * @param grantResults
     * @param
     */
    private void showMissingPermissionDialog(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            boolean isGrant = true;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {

                } else {
                    isGrant = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("帮助");//提示帮助
                    builder.setMessage(R.string.string_help_text);

                    //如果是拒绝授权，返回
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(PERMISSION_DENIEG);//权限不足
                            Toast.makeText(mActivity, "取消", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                    builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳转系统应用权限
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
                            startActivity(intent);
                            return;
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
            }
            if(isGrant){
                onGrantPermission();
            }

        }
    }

    public void onGrantPermission() {

    }
}
