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
}
