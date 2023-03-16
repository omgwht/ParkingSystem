package com.abc.parkingsystem;

public class ParkingInfoDoubleArray {
    private String[] parkinglot_name = null;
    private String[] parkinglot_position = null;
    ParkingInfoDoubleArray(String[] name_arr,String[] position_arr){
        parkinglot_name = name_arr;
        parkinglot_position = position_arr;
    }
    public String[] getNameArray(){
        return parkinglot_name;
    }
    public String[] getPositionArray(){
        return parkinglot_position;
    }
}
