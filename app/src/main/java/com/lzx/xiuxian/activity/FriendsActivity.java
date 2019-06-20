package com.lzx.xiuxian.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.lzx.xiuxian.Adapter.FriendsAdapter;
import com.lzx.xiuxian.Adapter.ItemAdapter;
import com.lzx.xiuxian.Dao.UserDao;
import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.Item;
import com.lzx.xiuxian.Vo.User;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class FriendsActivity extends AppCompatActivity {
    private String TAG = "FriendsActivity";
    private List<User> friends;
    private FriendsAdapter adapter;
    private RecyclerView recyclerView;
    private Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case 0:
                    adapter.notifyDataSetChanged(); //发送消息通知ListView更新

                    break;
                default:
                    //do something
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            Log.d(TAG,"!!!!");
            setContentView(R.layout.list2);
            friends = new ArrayList<>();
            recyclerView = (RecyclerView)findViewById(R.id.list2);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            requestAllPower();//申请权限＋初始化数据
            adapter = new FriendsAdapter(friends);
            recyclerView.setAdapter(adapter); // 重新设置ListView的数据适配器

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }

    private void initItems(){

        new Thread() {
            public void run() {
                Cursor cursor = null;
                try{
                    cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            null,null,null);
                    Log.d(TAG,"cursor?:"+(cursor==null));
                    if(cursor !=null){
                        while(cursor.moveToNext()){
                            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Log.d(TAG,"number:"+number);
                            User user = new UserDao().getUser(number);
                            Log.d(TAG,"user?:"+(user==null));
                            if(user!=null){
                                friends.add(user);
                                Log.d(TAG,"userName:"+user.getName());
                            }
                        }
                    }
                }catch (Exception e){
                    Log.d(TAG,e.toString());

                }finally {
                    if(cursor!=null){
                        cursor.close();
                    }
                }

                hander.sendEmptyMessage(0); // 下载完成后发送处理消息
            }                    ;
        }.start();













    }

    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS}, 1);

        }else{
            initItems();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    initItems();
                }else{
                    Toast.makeText(this,"你拒绝了权限",Toast.LENGTH_SHORT).show();

                }
                break;
        }

    }

}
