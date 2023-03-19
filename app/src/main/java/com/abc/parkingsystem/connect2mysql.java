package com.abc.parkingsystem;

import android.util.Log;

import java.sql.*;

public class connect2mysql {

    private Connection connection;

    public boolean getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://10.0.2.2:3306/parking";
        String user = "root";
        String pwd = "dir99";
        connection = DriverManager.getConnection(url, user, pwd);
        if (connection == null) {
            Log.d("ERROR", "数据库连接失败");
            return false;
        } else {
            Log.d("SUCCESS", "数据库连接成功");
            return true;
        }
    }

    public boolean selectcountFromDB(String sql) throws SQLException, ClassNotFoundException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int n = 0;
        while (resultSet.next()) {
            n = resultSet.getInt(1);
        }
        if (n == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean insertToDB(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        if (statement.executeUpdate(sql) >= 1) {
            return true;
        }else{
            return false;
        }
    }

    public ParkingInfoDoubleArray getSearchParkingResult(String sql) throws SQLException {
        Statement pstmt = connection.createStatement();
        ResultSet rs = pstmt.executeQuery(sql);
        int parkinglot_num = 0;
        while(rs.next()){
            parkinglot_num++;
        }
        String[] parkinglot_name_arr = new String[parkinglot_num];
        String[] parkinglot_position_arr = new String[parkinglot_num];
        rs = pstmt.executeQuery(sql);
        for(int i = 0; rs.next(); i++){
            parkinglot_name_arr[i] = rs.getString("parkinglot_name");
            parkinglot_position_arr[i] = rs.getString("parkinglot_position");
        }
        ParkingInfoDoubleArray pkd_arr = new ParkingInfoDoubleArray(parkinglot_name_arr,parkinglot_position_arr);
        return pkd_arr;
    }


}
