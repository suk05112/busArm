package com.example.busalarm;

public class SearchStop {
    String busStop;
    String stopNum;
    String direction;

    public SearchStop(String busStop, String stopNum, String direction){
        this.busStop = busStop;
        this.stopNum = stopNum;
        this.direction = direction;
    }

    public String getbusStop(){
        return this.busStop;
    }

    public String getstopNum(){ return this.stopNum;}

    public String getdirection(){
        return this.direction;
    }

}
