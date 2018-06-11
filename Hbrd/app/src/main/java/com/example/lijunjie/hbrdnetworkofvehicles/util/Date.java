package com.example.lijunjie.hbrdnetworkofvehicles.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hyc on 2018/6/2 11:49
 */
public class Date {
    public static final String PATTERN = "MM月dd日";
    public static final String[] weekDays = {"星期日", "星期一"
            , "星期二", "星期三", "星期四", "星期五", "星期六"};
    //日期
    private String day;
    //星期
    private String week;
    public Date(String day, String week) {
        this.day = day;
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public static List<Date> generate(){
        long dayTime = 24*60*60*1000L;
        DateFormat format = new SimpleDateFormat(PATTERN);
        List<Date> dates = new ArrayList<>();
        long curTime = System.currentTimeMillis();
        for (int i = 1; i < 8; i++) {
            long time = curTime-i*dayTime;
            String day = format.format(time);
            Date date = new Date(day,getWeek(time));
            dates.add(date);
        }
        return dates;
    }

    private static String getWeek(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date(time));
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w<0?0:w];
    }
}
