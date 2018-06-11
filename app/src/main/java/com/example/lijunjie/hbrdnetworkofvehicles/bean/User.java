package com.example.lijunjie.hbrdnetworkofvehicles.bean;

/**
 * Created by lijunjie on 2018/6/7.
 */

public class User {

    private String oneselIdCard; // 身份证

    private String oneselIdName; // 姓名

    private String oneselfPhone; // 电话号码

    private String oneselfSerial; // 序列号

    private String oneselfName; // 用户名


    public String getOneselIdCard() {
        return oneselIdCard;
    }

    public void setOneselIdCard(String oneselIdCard) {
        this.oneselIdCard = oneselIdCard;
    }

    public String getOneselIdName() {
        return oneselIdName;
    }

    public void setOneselIdName(String oneselIdName) {
        this.oneselIdName = oneselIdName;
    }

    public String getOneselfPhone() {
        return oneselfPhone;
    }

    public void setOneselfPhone(String oneselfPhone) {
        this.oneselfPhone = oneselfPhone;
    }

    public String getOneselfSerial() {
        return oneselfSerial;
    }

    public void setOneselfSerial(String oneselfSerial) {
        this.oneselfSerial = oneselfSerial;
    }

    public String getOneselfName() {
        return oneselfName;
    }

    public void setOneselfName(String oneselfName) {
        this.oneselfName = oneselfName;
    }

    @Override
    public String toString() {
        return "oneselIdCard:"+oneselIdCard+",oneselIdName:"+oneselfName;
    }
}
