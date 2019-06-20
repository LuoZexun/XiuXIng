package com.lzx.xiuxian.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzx.xiuxian.R;
import com.lzx.xiuxian.Vo.Item;
import com.lzx.xiuxian.service.MyService;

import java.util.ArrayList;
import java.util.List;


public class XiuXingActivity extends Activity  implements View.OnClickListener{
    private String TAG = "XiuXingActivity";
    private TextView tv_result;
    private TextView tips;
    private ImageView notes;
    private CountDownTimer timer;
    private Button cancel;
    ImageView music;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private AssetManager assetManager;
    private int musicFlag = 0;

    private Item item = new Item();
    private SharedPreferences sharedPreferences;
    private int xiuXingFlag = 0;
    private int time;
    private long startTime;
    private long actualTime = 0;
    private String name;
    private String phone;
    private long score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xiuxian);

        Log.d(TAG,"!!!");

        try{
            Intent startIntent = new Intent(this, MyService.class);
            startService(startIntent);

        }catch (Exception e)
        {
            Log.d(TAG,e.toString());
        }

        Log.d(TAG,"!!!");


        notes = (ImageView)findViewById(R.id.notes);
        tv_result = (TextView)findViewById(R.id.time);
        tips = (TextView)findViewById(R.id.tips);
        cancel = (Button) findViewById(R.id.cancel);
        music = (ImageView) findViewById(R.id.music);
        cancel.setOnClickListener(this);
        music.setOnClickListener(this);
        notes.setOnClickListener(this);


        sharedPreferences = getSharedPreferences("data",0);
        name = sharedPreferences.getString("name","无名");
        score = sharedPreferences.getLong("score",0);
        phone = sharedPreferences.getString("phone",null);
        Intent intent = getIntent();
        time = intent.getIntExtra("time", 0);
        startTime = intent.getLongExtra("startTime",0);
        initPlayer();

        tv_result.setText(time+":00");
        xiuXingFlag = 1;
        List<String> tipss = new ArrayList<>();
        tipss.add("成大事者，必先放下手机");
        tipss.add("赶快去做事！");
        tipss.add("别看着我哦，人家会害羞的");
        tipss.add("还看！小心我打你");
        tipss.add("专注！专注！");
        tipss.add("就在眼前，坚持！");
        timer = new CountDownTimer(time*60*1000,1000) {
            int i = 0;
            int j = 0;
            @Override
            public void onTick(long millisUntilFinished) {
//                tv_result.setText("seconds remaining: " + millisUntilFinished / 1000);
                   long minute = millisUntilFinished/1000/ 60 % 60;
                   long second = millisUntilFinished/1000%60;
                   String minStr = minute < 10 ? "0"+ minute : "" + minute;
                   String secStr = second < 10 ? "0"+ second : "" + second;
                   tv_result.setText(minStr + ":" + secStr);
                   tips.setText(tipss.get(j%6));
                   i++;
                   if(i>5){
                       j++;
                       i=0;
                   };

            }

            @Override
            public void onFinish() {
                actualTime = time;
                finishTime(0);
            }
        };
        timer.start();

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                showDialog();

                break;
            case R.id.music:
                runMusic();
                break;
            case R.id.notes:
                try{
                    write();
                }catch (Exception e){
                    Log.d(TAG,e.toString());
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        timer.cancel();

        mediaPlayer.release();

        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
        finish();
    }

    private void initPlayer(){
        assetManager = getAssets();
        mediaPlayer = new MediaPlayer();

        try{
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("always_with_me.mp3");
            mediaPlayer.reset();

            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());

            mediaPlayer.prepare();
        }catch (Exception e ){
            Log.d(TAG,e.toString());
        }
    }
    private void playMusic(){
        try{
            mediaPlayer.start();
            Log.d(TAG,"music start");
        }catch (Exception e ){
            Log.d(TAG,e.toString());
        }
    }
    private void pauseMusic(){
        try{
            mediaPlayer.pause();
            Log.d(TAG,"music stop");

        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
    }
    private void runMusic(){
        musicFlag = (musicFlag + 1) % 2;
        if(musicFlag == 1){
            playMusic();
        }
        else if(musicFlag == 0){
            pauseMusic();
        }
    }



    private void showDialog(){
        final AlertDialog.Builder Dialog =new AlertDialog.Builder(XiuXingActivity.this);
        Dialog.setTitle("你确定要终止修行？");
        Dialog.setMessage("修行中途强行打断会走火入魔，年轻人要三思啊");
        Dialog.setPositiveButton("堕落吧", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishTime(1);
            }
        });
        Dialog.setNegativeButton("手滑了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        Dialog.show();
    }

    private void finishTime(int flag){
        xiuXingFlag = 0;
        tv_result.setVisibility(View.GONE);
        notes.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        music.setVisibility(View.GONE);
        if(flag == 0){
            mediaPlayer.pause();
            tips.setText("Well done!完成"+time+"分钟修行");

        }
        else if(flag == 1){
            timer.cancel();
            mediaPlayer.pause();
            tips.setText(("中断修行导致走火入魔,"+"\n"+"丢失修行时间"));
        }

        item.setName(name);
        item.setStartTime(startTime);
        item.setActualTime(actualTime);
        item.save();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("score",actualTime);
        editor.apply();

//        saveMysql(actualTime);
    }

//    private void saveMysql(long actualTime) {
//        User user = new UserDao().getUser(phone);
//        user.setScore(user.getScore()+actualTime);
//
//    }


    @Override
    //安卓重写返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            if(xiuXingFlag == 0){
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                XiuXingActivity.this.onDestroy();
            }else if(xiuXingFlag == 1){
                showDialog();
            }


        }
        return true;
    }

    private void write(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");    //设置对话框标题
        final EditText edit = new EditText(this);

        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG,"1111111");
                item.setSentence(edit.getText().toString());
            }
        });

        builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();

    }







}
