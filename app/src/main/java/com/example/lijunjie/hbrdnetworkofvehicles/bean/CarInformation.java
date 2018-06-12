package com.example.lijunjie.hbrdnetworkofvehicles.bean;

/**
 * 车辆信息
 */
public class CarInformation  {
    private String CarInformationSerial;   // 终端序列号
    private String UserSerial;    //用户
    private String CarInformationBrand;   //车牌子
    private String CarInformationMake;
    private String CarInformationModel;
    private String CarInformationStand;
    private int GroupingId;   //分组id

    public String getCarInformationSerial() {
        return CarInformationSerial;
    }

    public void setCarInformationSerial(String carInformationSerial) {
        CarInformationSerial = carInformationSerial;
    }

    public String getUserSerial() {
        return UserSerial;
    }

    public void setUserSerial(String userSerial) {
        UserSerial = userSerial;
    }

    public String getCarInformationBrand() {
        return CarInformationBrand;
    }

    public void setCarInformationBrand(String carInformationBrand) {
        CarInformationBrand = carInformationBrand;
    }

    public String getCarInformationMake() {
        return CarInformationMake;
    }

    public void setCarInformationMake(String carInformationMake) {
        CarInformationMake = carInformationMake;
    }

    public String getCarInformationModel() {
        return CarInformationModel;
    }

    public void setCarInformationModel(String carInformationModel) {
        CarInformationModel = carInformationModel;
    }

    public String getCarInformationStand() {
        return CarInformationStand;
    }

    public void setCarInformationStand(String carInformationStand) {
        CarInformationStand = carInformationStand;
    }

    public int getGroupingId() {
        return GroupingId;
    }

    public void setGroupingId(int groupingId) {
        GroupingId = groupingId;
    }
}
