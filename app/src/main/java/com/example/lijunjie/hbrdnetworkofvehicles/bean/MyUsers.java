package com.example.lijunjie.hbrdnetworkofvehicles.bean;

import cn.bmob.sms.BmobSMS;

/**
 * Created by lijunjie on 2018/6/2.
 */

public class MyUsers  {

    private String mobile_phone;
    private String verification;

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
