package com.lzx.xiuxian.Dao;

import android.util.Log;
import com.lzx.xiuxian.Vo.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UserDao {
    private String TAG = "UserDao";
    private User user = null;
    //获取用户
    public User getUser(String phone) {
        Log.d(TAG,"getUser!");
        List<String> list = new ArrayList<>();
        list.add(phone);
        String sql = "select * from user where phone = ?";
        Vector<String> vector =null;
        try {
            vector  = BaseDao.select(sql, list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d(TAG,e.toString());
        }
        try {
            if(vector!=null) {
                user = new User();
                user.setName(vector.get(0));
                user.setPhone(vector.get(1));
                user.setPsw(vector.get(2));

            }
        }catch (Exception e){
            Log.d(TAG,vector.toString());
            Log.d(TAG,e.toString());

        }

        return user;

    }

    public User checkPsw(String phone,String userPsw){
        User user= getUser(phone);
        if(user.getPsw().equals(userPsw))
            return user;
        else
            return null;
    }

    public int addUser(User user) {
        int i = 0;

        try{
            List<String > list = new ArrayList<>();
            list.add(user.getName());
            list.add(user.getPhone());
            list.add(user.getPsw());

            String sql1 = "name,phone,psw";
            String sql = "insert into user("+sql1+") values(?,?,?)";
            i = BaseDao.insert(sql, list);

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
        Log.d(TAG,"影响行数"+i);
        return i;

    }

    public boolean isExist(String phone){
        boolean b = false;
        List<String> list = new ArrayList<>();
        list.add(phone);
        String sql = "select * from user where phone = ?";
        Vector<String> vector =null;
        try {
            vector  = BaseDao.select(sql, list);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Log.d(TAG,e.toString());
        }
        if(!vector.isEmpty()) {
            b = true;
        }
        return b;
    }

//    public int updateUser(User user) {
//        List<String> list = new ArrayList<>();
//        list.add(user.getScore());
//
//        list.add(user.getPhone());
//        String sql1 = "score=?";
//        String sql = "update user set "+sql1+"where phone=?";
//        int updateCount = 0;
//        try {
//            updateCount=BaseDao.update(sql, list);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return updateCount;
//    }
}
