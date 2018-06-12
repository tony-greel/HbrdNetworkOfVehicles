package com.example.lijunjie.hbrdnetworkofvehicles.util;

import android.util.Log;

import com.example.lijunjie.hbrdnetworkofvehicles.bean.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengwei on 2018/6/12.
 */

public class HbrdJsonParse {
    private static JSONObject jsonObject;
    private static JSONObject ModelObject;
    private static JSONArray ModelArray;
    private static JSONObject ModelArrayObj;
    private static Object pc;
    public static Object getJson(String str,String ModelName,boolean isOA,Class tClass) throws JSONException, IllegalAccessException, InstantiationException {
        jsonObject =new JSONObject(str);
        if(jsonObject.getString("Status").equals("ok")){
            if(isOA){
                ModelObject=jsonObject.getJSONObject(ModelName);
                pc = tClass.newInstance();// 创建一个实例
                Field[] fs = tClass.getDeclaredFields();
                for(Field s:fs){
                    Log.d("zengs",ModelObject.getString(s.getName()));
                    s.setAccessible(true);// 将目标属性设置为可以访问
                    s.set(pc,ModelObject.getString(s.getName()));
                }
                return pc;
            }else{
                List<Object> objects=new ArrayList<>();
                ModelArray=jsonObject.getJSONArray(ModelName);
                Field[] fs = tClass.getDeclaredFields();
                for(int i=0;i<ModelName.length();i++){
                    pc= tClass.newInstance();// 创建一个实例
                    for(Field s:fs){
                        Log.d("zengs",ModelObject.getString(s.getName()));
                        s.setAccessible(true);// 将目标属性设置为可以访问
                        ModelArrayObj=ModelArray.getJSONObject(i);
                        s.set(pc,ModelArrayObj.getString(s.getName()));
                    }
                    objects.add(pc);
                }
                return objects;
            }
        }else{
            return "error";
        }
    }
}
