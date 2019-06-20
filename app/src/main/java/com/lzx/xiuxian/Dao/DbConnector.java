package com.lzx.xiuxian.Dao;

/**
 * Created by Administrator on 2019/2/22 0022.
 */


import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnector {
    private String TAG= "DbConnector";
    private Connection con;
    private String dbDriver = "com.mysql.jdbc.Driver";
    private String dbUser = "root";
    private String dbPsw = "13535552522";
    private String dbUrl = "jdbc:mysql://10.252.4.167:3306/XiuXian";

    public Connection getConnection() {
        try {
            Class.forName(dbDriver);
            Log.d(TAG, "数据库加载成功");
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            Log.d(TAG, e1.toString());
        }

        try {
            con = DriverManager.getConnection(dbUrl,dbUser,dbPsw);
            Log.d(TAG, "数据库连接成功");

        } catch (Exception e) {

            // TODO Auto-generated catch block
            Log.d(TAG, e.toString());
        }

        return con;

    }
}
