package com.lzx.xiuxian.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.lzx.xiuxian.R;
import com.lzx.xiuxian.activity.XiuXingActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private String TAG = "MyService";
    private Context context = this;
    private TimerTask timerTask;
    private Timer timer;
    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        try{
//            Notification.Builder builder = new Notification.Builder(this);
//            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                    new Intent(this, XiuXingActivity.class), 0);
//            builder.setContentIntent(contentIntent);
//            builder.setSmallIcon(R.mipmap.ic_launcher);
//            builder.setTicker("Foreground Service Start");
//            builder.setContentTitle("Foreground Service");
//            builder.setContentText("Make this service run in the foreground.");
//            Notification notification = builder.build();
//            startForeground(1, notification);

//
//            Intent notificationIntent = new Intent(this, XiuXingActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
//            Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
//            builder.setContentTitle("***服务");
//            builder.setContentText("请勿关闭，***");
//            builder.setContentIntent(pendingIntent);
//            Notification notification = builder.getNotification();
//            startForeground(1,notification);//启动前台服务

//             在API11之后构建Notification的方式
            Notification.Builder builder = new Notification.Builder
            (this.getApplicationContext()); //获取一个Notification构造器
            Intent nfIntent = new Intent(this, XiuXingActivity.class);

            builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                     R.mipmap.touxiang2)) // 设置下拉列表中的图标(大图标)
            .setContentTitle("修行中") // 设置下拉列表里的标题
            .setSmallIcon(R.mipmap.touxiang2) // 设置状态栏内的小图标
            .setContentText("别玩手机啦，快去做事") // 设置上下文内容
            .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

            Notification notification = builder.build(); // 获取构建好的Notification
            notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

            // 参数一：唯一的通知标识；参数二：通知消息。
            startForeground(110, notification);// 开始前台服务
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }

        timerTask= new TimerTask() {

            @Override
            public void run() {
                String topName = getTopApp(getApplicationContext());
                Log.d(TAG,"名"+topName);
                if(!topName.equals("com.lzx.xiuxian")){
                    try{
                        Intent intent = new Intent(context, XiuXingActivity.class);
                        startActivity(intent);
                    }catch (Exception e){
                        Log.d(TAG,e.toString());
                    }

                    Log.d(TAG,"到这里");
                }

            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,100);//从现在开始，每隔100毫秒运行一次run方法


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");

    }

    @Override
    public void onDestroy() {

        stopForeground(true);// 停止前台服务--参数：表示是否移除之前的通知
        timer.cancel();
    }

    private String getForegroundApp() {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext()
                .getSystemService(Context.USAGE_STATS_SERVICE);
        long ts = System.currentTimeMillis();
        List<UsageStats> queryUsageStats
                = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;
        }

        UsageStats recentStats = null;
        for (UsageStats usageStats : queryUsageStats) {
            if(recentStats == null ||
                    recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                recentStats = usageStats;
            }
        }

        return recentStats.getPackageName();
    }

    boolean isUserApp(String packageName){
        PackageInfo packageInfo=null;
        boolean isUserApp = false;
        //首先获取packageManager
        PackageManager packageManager = getApplicationContext().getPackageManager();
        //获取packageInfo信息
        try{
            packageInfo = packageManager.getPackageInfo(packageName, 0);
            isUserApp = (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;
        }catch (Exception e){
            Log.d(TAG,e.toString());
        }
        return isUserApp;
    }


    private static String getTopApp(Context context) {

        String topActivity = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                }
            }
        }

        return topActivity;
    }

}
