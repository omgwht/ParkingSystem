package com.abc.parkingsystem;

public class MyPostInfo {
    String post_user_name = null;
    String post_detail = null;
    MyPostInfo(String post_user_name, String post_detail){
        this.post_user_name = post_user_name;
        this.post_detail = post_detail;
    }

    public String getPost_user_name(){
        return post_user_name;
    }
    public String getPost_detail(){
        return post_detail;
    }
}
