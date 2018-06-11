package com.example.lijunjie.hbrdnetworkofvehicles.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lijunjie on 2018/5/15.
 * 注册登录正则表达式验证
 */

public class AuthenticationIdNumberUtil {
    /**
     * 手机号验证
     * @param value
     * @return
     */
    public static boolean Isphone(String value){
        String regExp = "^(0|86|17951)?(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 用户名 大小写字母数字中文下划线  2-10位
     * @param value
     * @return
     */
    public static boolean Isuser(String value){
        String regExp = "^[A-Za-z0-9_\\u4e00-\\u9fa5]{2,10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 密码大于6位
     * @param value
     * @return
     */
    public static boolean Ispass(String value){
        if(value.length()>6){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 身份证号验证
     * @param value
     * @return
     */
    public static boolean Isidcard(String value){
        String regExp = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }

    /**
     * 车牌验证
     * @param value
     * @return
     */
    public static boolean iscarbrand(String value){
        String regExp = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(value);
        return m.find();
    }

}

