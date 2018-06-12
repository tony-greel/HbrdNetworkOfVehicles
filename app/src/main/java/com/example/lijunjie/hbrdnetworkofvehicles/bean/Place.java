package com.example.lijunjie.hbrdnetworkofvehicles.bean;

/**
 * 车辆实时信息表，位置信息
 */
public class   Place {
    private String carInformationSerial;
    private String date;
    private String dateTime;
    private String positionX;
    private String positionY;
    private double speed;
    private String acc;   //acc状态
    private String airconFrom;  //空调状态
    private int airconHeat;//空调温度
    private String airconModel;  //空调模式
    private String mileage;  //里程
    private int oil;  //剩余油量
    private int waters;  //水温
    private int rev;  //转速
    private int tax;  //机油压力
    private String carGateway;  //车门
    private String carlight;   //车灯
    private int isOnline;  //是否上下线

    public String getCarInformationSerial() {
        return carInformationSerial;
    }

    public void setCarInformationSerial(String carInformationSerial) {
        this.carInformationSerial = carInformationSerial;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getAirconFrom() {
        return airconFrom;
    }

    public void setAirconFrom(String airconFrom) {
        this.airconFrom = airconFrom;
    }

    public int getAirconHeat() {
        return airconHeat;
    }

    public void setAirconHeat(int airconHeat) {
        this.airconHeat = airconHeat;
    }

    public String getAirconModel() {
        return airconModel;
    }

    public void setAirconModel(String airconModel) {
        this.airconModel = airconModel;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public int getOil() {
        return oil;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public int getWaters() {
        return waters;
    }

    public void setWaters(int waters) {
        this.waters = waters;
    }

    public int getRev() {
        return rev;
    }

    public void setRev(int rev) {
        this.rev = rev;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getCarGateway() {
        return carGateway;
    }

    public void setCarGateway(String carGateway) {
        this.carGateway = carGateway;
    }

    public String getCarlight() {
        return carlight;
    }

    public void setCarlight(String carlight) {
        this.carlight = carlight;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }
}
