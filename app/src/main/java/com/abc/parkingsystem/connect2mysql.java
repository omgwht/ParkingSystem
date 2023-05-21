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

    public pkpStateInfoArray getParkingPortInfo(String sql) throws SQLException {
        // SQL语句： SELECT * FROM parkingport_info WHERE pkl_name = '(pkl_name)';
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int count = 0;
        while (rs.next()){
            count++;
        }
        String[] pkl_name = new String[count];
        String[] pkp_num = new String[count];
        int[] pkp_state = new int[count];
        String[] pkp_car_num = new String[count];
        String[] pkp_car_phone = new String[count];
        String[] pkp_in_time = new String[count];
        rs = stmt.executeQuery(sql);
        for(int i = 0; rs.next(); i++){
            pkl_name[i] = rs.getString("pkl_name");
            pkp_num[i] = rs.getString("pkp_num");
            pkp_state[i] = rs.getInt("pkp_state");
            pkp_car_num[i] = rs.getString("pkp_car_num");
            pkp_car_phone[i] = rs.getString("pkp_car_phone");
            pkp_in_time[i] = rs.getString("pkp_in_time");
        }
        pkpStateInfoArray pkp_arr = new pkpStateInfoArray(pkl_name,pkp_num,pkp_state,pkp_car_num,pkp_car_phone,pkp_in_time);
        return pkp_arr;
    }

    public int getPkpRemainNum(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        int n = 0;
        while (rs.next()){
            n = rs.getInt(1);
        }
        return n;
    }

    /*
     改变车位状态的方法
     in_state表示进入或驶出的状态，1表进入，0表驶出
    */
    public boolean changePkpState(boolean in_state,String pkl_name, String pkp_num, String car_num, String pkp_car_phone, String pkp_in_time) throws SQLException {
        //更新语句
        //UPDATE parkingport_info SET pkp_state = 1,pkp_car_num = '京Q-42H67',pkp_car_phone = '13628390937',pkp_in_time = '2023-05-19-22:33' WHERE pkl_name = 'Mine0停车场' AND pkp_num = 'A-103'
        int state = in_state? 1 : 0;
        String sql = String.format("UPDATE parkingport_info SET pkp_state = '%d',pkp_car_num = '%s',pkp_car_phone = '%s',pkp_in_time = '%s' WHERE pkl_name = '%s' AND pkp_num = '%s'",state,car_num,pkp_car_phone,pkp_in_time,pkl_name,pkp_num);
        Statement statement = connection.createStatement();
        if (statement.executeUpdate(sql) >= 1) {
            return true;
        }else{
            return false;
        }
    }

}
