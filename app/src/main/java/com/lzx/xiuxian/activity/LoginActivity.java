package com.lzx.xiuxian.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzx.xiuxian.Dao.UserDao;
import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.User;
import com.lzx.xiuxian.utils.EditTextClearTools;

public class LoginActivity extends Activity implements View.OnClickListener {
    private String TAG = "LoginActivity";
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText userPsw;
    EditText userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);



        Button register = (Button) findViewById(R.id.btn_register);
        Button login = (Button) findViewById(R.id.btn_login);
        Button nologin = (Button) findViewById(R.id.btn_nologin);
        userPsw = (EditText) findViewById(R.id.et_password);
        userPhone = (EditText) findViewById(R.id.et_userPhone);

        CheckBox ivCheckbox = (CheckBox) findViewById(R.id.cb_checkbox);
        ivCheckbox.setClickable(false);

        init();

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        nologin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_nologin:
                Log.d(TAG,"不登录使用");
                Intent mainIntent = new Intent(this,MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.btn_register:
                Log.d(TAG,"注册");
                Intent regIntent = new Intent(this, RegisterActivity.class);
                startActivity(regIntent);
                break;
            case R.id.btn_login:
                Log.d(TAG,"登录");
                final String phone = userPhone.getText().toString();
                final String psw = userPsw.getText().toString();
                if (!validateLogin(phone, psw)) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setTitle("提示")
                            .setMessage("用户名或密码不能为空")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    dialog.show();
                    return;
                }
                new Thread() {
                    public void run() {
                        Log.d(TAG,"!!!");
                        User user=null;
                        try{
                            user = new UserDao().checkPsw(phone,psw);
                        }catch (Exception e){
                            Log.d(TAG,e.toString());
                        }
                            if(user!=null)
                            {

                                sharedPreferences = getSharedPreferences("data",0);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                editor = sharedPreferences.edit();
                                editor.putBoolean("ISLOGIN", true);
                                editor.putString("name", user.getName());
                                editor.putBoolean("remember_password",true);
                                editor.putString("userPsw",user.getPsw());
                                editor.putString("phone",user.getPhone());
                                editor.putLong("score",user.getScore());
                                editor.apply();
                                startActivity(intent);
                                finish();
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                                        dialog.setTitle("提示")
                                                .setMessage("用户名或密码错误")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        dialog.show();
                                    }
                                });

                            }





                    }

                }.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    private void init(){
        EditText userName = (EditText) findViewById(R.id.et_userPhone);
        EditText password = (EditText) findViewById(R.id.et_password);
        ImageView unameClear = (ImageView) findViewById(R.id.iv_unameClear);
        ImageView pwdClear = (ImageView) findViewById(R.id.iv_pwdClear);

        EditTextClearTools.addClearListener(userName,unameClear);
        EditTextClearTools.addClearListener(password,pwdClear);
    }
    public boolean validateLogin(String username, String password) {
        if ((!TextUtils.isEmpty(username)) && (!TextUtils.isEmpty(password)))
            return true;
        return false;
    }
    public class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"网络发生改变",Toast.LENGTH_LONG).show();
        }
    }
}
