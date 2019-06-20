package com.lzx.xiuxian.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.lzx.xiuxian.R;


import java.util.Date;


public class MainActivity extends Activity implements View.OnClickListener {
    NumberPicker numberPicker=null;


    private SharedPreferences sharedPreferences;
    private String name;
    private Long score;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView menu;
    private Button activity_xiuxian;
    private ImageView me;
    private TextView tvName;

    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"!!!");
        try{
            drawerLayout = (DrawerLayout) findViewById(R.id.activity_na);
            navigationView = (NavigationView) findViewById(R.id.nav);
            tvName = (TextView)findViewById(R.id.textView2);
            menu= (ImageView) findViewById(R.id.menu);
            View headerView = navigationView.getHeaderView(0);
            me = (ImageView) findViewById(R.id.me);
            me.setOnClickListener(this);
            menu.setOnClickListener(this);
            sharedPreferences = getSharedPreferences("data",0);
            name = sharedPreferences.getString("name","无名");
            score = sharedPreferences.getLong("score",0);
            tvName.setText(name);
        }catch (Exception e){
            Log.d(TAG,e.toString());

        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(false                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               );

                switch (item.getItemId()){
                    case R.id.records:
                        try{
                            Intent intent=new Intent(MainActivity.this, ListActivity.class);
                            startActivity(intent);
                        }catch (Exception e)
                        {
                            Log.d(TAG,e.toString());
                        }
                        break;
                    case R.id.friends:

                        try{
                            Intent intent = new Intent(MainActivity.this,FriendsActivity.class);
                            startActivity(intent);
                            break;
                        }catch (Exception e){
                            Log.d(TAG,e.toString());
                        }

                }

                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });



        numberPicker = findViewById(R.id.number_picker);
        String [] values = new String[] {"30","40","50","60",
                "70","80","90","100","110","120","130","140","150","160",
                "170","180"};
        numberPicker.setMaxValue(values.length-1);
        numberPicker.setMinValue(0);
        numberPicker.setDisplayedValues(values);
        numberPicker.setFocusable(true);
        numberPicker.setFocusableInTouchMode(true);
        this.setNumberPickerValue(30);

        activity_xiuxian = (Button) this.findViewById(R.id.activity_xiuxian);
        activity_xiuxian.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu://点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }

                break;
            case R.id.activity_xiuxian://开始计时

                Date  date = new Date();
                Log.d(TAG,date.toString());
                long startTime = date.getTime();

                Intent intent = new Intent(MainActivity.this, XiuXingActivity.class);
                // 在Intent中传递数据
                intent.putExtra("time", getNumberPickerValue());
                intent.putExtra("startTime",startTime);
                // 启动Intent
                startActivity(intent);

                break;
//            case R.id.records:
//                try{
//                    startActivity(new Intent(MainActivity.this,ListActivity.class));
//
//                }catch (Exception e)
//                {
//                    Log.d(TAG,e.toString());
//
//                }
//                break;
            case R.id.me:
                final AlertDialog.Builder Dialog =new AlertDialog.Builder(MainActivity .this);
                Dialog.setTitle("修行");
                Dialog.setMessage("你已经专注了"+score+"分钟");
                Dialog.show();
        }
    }



    public int getNumberPickerValue(){
        if(numberPicker != null){
            return (numberPicker.getValue()+3)*1;
        }else{
            return -1;
        }
    }

    public void setNumberPickerValue(int val) {
        if (numberPicker != null) {
            numberPicker.setValue(val / 10 - 3);
        }

    }




}
