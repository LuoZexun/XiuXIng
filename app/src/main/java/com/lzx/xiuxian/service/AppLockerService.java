//package com.lzx.xiuxian.service;
//
//
//import android.app.Service;
//import android.app.usage.UsageStats;
//import android.app.usage.UsageStatsManager;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Build;
//import android.os.IBinder;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.manager.child.Dao.LockAppDao_sqlite;
//import com.manager.child.toolclass.ManagerApplication;
//
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//
//public class AppLockerService extends Service {
//    private LockAppDao_sqlite lockAppDao ;
//    String TAG = "AppLockerService";
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d(TAG,"!!!");
//        SharedPreferences setting = getSharedPreferences("setting", 0);
//        SharedPreferences.Editor editor;
//        editor = setting.edit();
//        Context context =new ManagerApplication().getContext();
//        lockAppDao = new LockAppDao_sqlite(context);
//        /**
//         * 当service运行在低内存的环境时，将会kill掉一些存在的进程。因此进程的优先级将会很重要，
//         * 可以使用startForeground API将service放到前台状态。这样在低内存时被kill的几率更低
//         */
////        class Ob {
////            boolean flag = true;
////            void setFlag(){
////                flag = false;
////            }
////            boolean getFlag(){
////                return flag;
////            }
////        }
////        final Ob ob = new Ob();
//        //startForeground(0, new Notification());
//        TimerTask task = new TimerTask() {
//
//            @Override
//            public void run() {
//                String unLockApp = setting.getString("unLockApp","");
//                String packageName = getTopApp(context);
//                Log.d("AppLockerService","top:"+packageName);
//
//                //记得把下面放在括号里
//                //lockAppDao.isLock(packageName)
//                if (packageName.equals("com.tencent.mm")||packageName.equals("com.netease.mrzh.huawei")) {
//                    if(!unLockApp.equals(packageName)){
//
//                            Intent intent = new Intent();
//                            intent.setClass(AppLockerService.this,suopingActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra("packageName",packageName);
//                            startActivity(intent);
//
//                    }
//
//                }
////                else if(packageName.equals("com.manager.child")){
////                    editor.putString("unLockApp","");
////                    editor.apply();
////
////                }
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(task,0,100);//从现在开始，每隔100毫秒运行一次run方法
//        return START_STICKY;
//     }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//    public static String getTopApp(Context context) {
//
//        String topActivity = "";
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//
//                UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//
//
//                if (m != null) {
//                    long now = System.currentTimeMillis();
//                    //获取60秒之内的应用数据
//                    List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now);
//                    //取得最近运行的一个app，即当前运行的app
//                    if ((stats != null) && (!stats.isEmpty())) {
//                        int j = 0;
//                        for (int i = 0; i < stats.size(); i++) {
//                            if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
//                                j = i;
//                            }
//                        }
//                        topActivity = stats.get(j).getPackageName();
//                    }
//                }
//            }
//
//        return topActivity;
//    }
//
//
//
//}
