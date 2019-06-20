package com.lzx.xiuxian.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.lzx.xiuxian.R;

public class FirstActivity extends Activity {

    SharedPreferences sharedPreferences;
    Boolean user_login;
    Boolean isFirst;
    private String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.first);
            new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                        user_login = sharedPreferences.getBoolean("ISLOGIN", false);
                        isFirst = sharedPreferences.getBoolean("ISFIRST", true);
                        if(isFirst){
                            Intent setIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            startActivity(setIntent);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("ISFIRST",false);
                            editor.apply();

                        }
                        Log.d(TAG,"!!!");


                        if (user_login) {
                            //登录了，正常加载
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            //没有登录，跳转到登录activity
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        Log.d(TAG,"!!!");



                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }
}
