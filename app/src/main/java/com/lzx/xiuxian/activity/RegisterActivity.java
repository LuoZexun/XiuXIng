package com.lzx.xiuxian.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.lzx.xiuxian.Dao.UserDao;
import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.User;

import java.util.Date;

public class RegisterActivity extends Activity {
    private String TAG = "re001";
    EditText ivUserName1;
    EditText ivPhone;
    EditText ivPassword1;
    EditText ivPassword2;
    String username,password,passwordVa,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        ivPhone = (EditText)findViewById(R.id.iv_userName2);
        ivUserName1 = (EditText)findViewById(R.id.iv_userName1);
        ivPassword1 = (EditText)findViewById(R.id.iv_password1);
        ivPassword2 = (EditText)findViewById(R.id.iv_password2);



        try{
            Button register = (Button)findViewById(R.id.btn_register1);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"!!!");

                    phone = ivPhone.getText().toString();
                    username = ivUserName1.getText().toString();
                    password = ivPassword1.getText().toString();
                    passwordVa = ivPassword2.getText().toString();
                    final String str = validateForm(username,phone,password,passwordVa);
                    if(!str.equals("正确")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(RegisterActivity.this);
                                dialog1.setTitle("提示")
                                        .setMessage(str)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                dialog1.show();
                            }
                        });
                        return ;
                    }
                    new Thread() {
                        public void run() {
                            Log.d(TAG,"1");
                            if (new UserDao().isExist(phone)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d(TAG,"2");

                                        AlertDialog.Builder dialog2 = new AlertDialog.Builder(RegisterActivity.this);
                                        dialog2.setTitle("提示")
                                                .setMessage("账号已注册")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        dialog2.show();
                                    }
                                });

                            }else{
                                User user = new User();
                                user.setName(username);
                                user.setPhone(phone);
                                user.setPsw(password);
                                new UserDao().addUser(user);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dialog1 = new AlertDialog.Builder(RegisterActivity.this);
                                        dialog1.setTitle("提示")
                                                .setMessage("注册成功")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        dialog1.show();
                                    }
                                });

                            }
                        }

                        ;
                    }.start();
                }
            });
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }



    }
    public String validateForm(String username, String phone,String password, String passwordVa) {
        if ((!TextUtils.isEmpty(phone)) &&(!TextUtils.isEmpty(username)) && (!TextUtils.isEmpty(password)) && (!TextUtils.isEmpty(passwordVa))) {
            if (password.equals(passwordVa))
                return "正确";
            return "两次密码填写不同";
        }
        return "请完整填写表单";

    }
}
