package com.example.lijunjie.hbrdnetworkofvehicles.bean;

/**
 * 车辆信息
 */
public class CarInformation  {

    private String carInformationSerial;   // 终端序列号
    private String userSerial;    //用户
    private String carInformationBrand;   //车牌子
    private String carInformationMake;
    private String carInformationModel;
    private String carInformationStand;
    private String groupingId;   //分组id

    public String getCarInformationSerial() {
        return carInformationSerial;
    }

    public void setCarInformationSerial(String carInformationSerial) {
        this.carInformationSerial = carInformationSerial;
    }

    public String getUserSerial() {
        return userSerial;
    }

    public void setUserSerial(String userSerial) {
        this.userSerial = userSerial;
    }

    public String getCarInformationBrand() {
        return carInformationBrand;
    }

    public void setCarInformationBrand(String carInformationBrand) {
        this.carInformationBrand = carInformationBrand;
    }

    public String getCarInformationMake() {
        return carInformationMake;
    }

    public void setCarInformationMake(String carInformationMake) {
        this.carInformationMake = carInformationMake;
    }

    public String getCarInformationModel() {
        return carInformationModel;
    }

    public void setCarInformationModel(String carInformationModel) {
        this.carInformationModel = carInformationModel;
    }

    public String getCarInformationStand() {
        return carInformationStand;
    }

    public void setCarInformationStand(String carInformationStand) {
        this.carInformationStand = carInformationStand;
    }

    public String getGroupingId() {
        return groupingId;
    }

    public void setGroupingId(String groupingId) {
        this.groupingId = groupingId;
    }
}
