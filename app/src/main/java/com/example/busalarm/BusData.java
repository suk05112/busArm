package com.example.busalarm;

public class BusData {
    int StationId;
    String StationName;
    int mobileNo;

    BusData(int StationId, String StationName, int mobileNo){
        this.StationId = StationId;
        this.StationName = StationName;
        this.mobileNo = mobileNo;
    }

    public int getStationId(){
        return this.StationId;
    }

    public String getStationName(){
        return this.StationName;
    }

    public int getMobileNo(){
        return mobileNo;
    }
}
