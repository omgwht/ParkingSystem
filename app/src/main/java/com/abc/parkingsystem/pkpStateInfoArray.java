package com.abc.parkingsystem;

public class pkpStateInfoArray {
    private String[] pkl_name = null;
    private String[] pkp_num = null;
    private int[] pkp_state = null;
    private String[] pkp_car_num = null;
    private String[] pkp_car_phone = null;
    private String[] pkp_in_time = null;
    pkpStateInfoArray(String[] pkl_name,String[] pkp_num,int[] pkp_state,String[] pkp_car_num,String[] pkp_car_phone,String[] pkp_in_time){
        this.pkl_name = pkl_name;
        this.pkp_num = pkp_num;
        this.pkp_state = pkp_state;
        this.pkp_car_num = pkp_car_num;
        this.pkp_car_phone = pkp_car_phone;
        this.pkp_in_time = pkp_in_time;
    }
    public String[] getPkl_name(){
        return pkl_name;
    }
    public String[] getPkp_num(){
        return pkp_num;
    }
    public int[] getPkp_state(){
        return pkp_state;
    }
    public String[] getPkp_car_num(){
        return pkp_car_num;
    }
    public String[] getPkp_car_phone(){
        return pkp_car_phone;
    }
    public String[] getPkp_in_time(){
        return pkp_in_time;
    }
}
