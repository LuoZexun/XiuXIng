//package com.lzx.xiuxian.service;
//
//import android.app.usage.UsageStats;
//import android.app.usage.UsageStatsManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.graphics.drawable.Drawable;
//import android.provider.Settings;
//import android.util.Log;
//
//import java.lang.reflect.Field;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
///**
// *
// *获取手机上的应用
// *
// **/
//
//public class AppInfoService {
//    private String TAG1 = "ERROR";
//    private Context context;
//    private PackageManager pm;
//    private UsageStatsManager usm;//获取时间的类
//    public AppInfoService(Context context) {
//        // TODO Auto-generated constructor stub
//        this.context = context;
//        pm = context.getPackageManager();
//        usm = (UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
//    }
//    /**
//     * 得到手机中所有的应用程序信息
//     * @return
//     */
//   /* public List<AppInfo> getAppInfos(){
//        //创建要返回的集合对象
//        List<AppInfo> appInfos = new ArrayList<AppInfo>();
//        //获取手机中所有安装的应用集合
//        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//        //遍历所有的应用集合
//        for(ApplicationInfo info : applicationInfos){
//
//            AppInfo appInfo = new AppInfo();
//
//            //获取应用程序的图标
//            Drawable app_icon = info.loadIcon(pm);
//            appInfo.setApp_icon(app_icon);
//
//            //获取应用的名称
//            String app_name = info.loadLabel(pm).toString();
//            appInfo.setApp_name(app_name);
//
//            //获取应用的包名
//            String packageName = info.packageName;
//            appInfo.setPackagename(packageName);
//            //判断应用程序是否是用户程序
//            boolean isUserApp = true; *//*filterApp(info);*/
//       /*     appInfo.setUserApp(isUserApp);
//            appInfos.add(appInfo);
//        }
//        return appInfos;
//    }*/
//
//    public List<AppInfo> getAppInfos(){
//        List<AppInfo> appInfos = new ArrayList<AppInfo>();
//        String packagename;
//        long total,lastTimeUsed;
//        int launchcount=0;
//        Calendar calendar = Calendar.getInstance();
//        long endtime = calendar.getTimeInMillis();
//        calendar.add(Calendar.DATE, -1);
//        long starttime = calendar.getTimeInMillis();
//        SimpleDateFormat sdf =new SimpleDateFormat("MM月dd日hh时mm分");
//        //下面这个方法得到UsageStats  第一个参数可以有很多种 第二第三个参数是开始计算和结束计算的时间
//        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, starttime, endtime);
//        if( usageStatsList==null || usageStatsList.isEmpty() )
//        {
//            try {
//                context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//            }catch (Exception e)
//            {
//                Log.d(TAG1, e.getMessage());
//            }
//        }
//        else {
//            int size = usageStatsList.size(),
//                    count = 0;
//            //遍历 得到数据
//            for (UsageStats usagestats : usageStatsList) {
//                ApplicationInfo applicationInfo = null;
//                packagename = usagestats.getPackageName();
//                try {
//                    applicationInfo = pm.getApplicationInfo(packagename, PackageManager.GET_META_DATA);
//                } catch (Exception e) {
//                    Log.d(TAG1, e.getMessage());
//                }
//                if (applicationInfo != null) {
//                    AppInfo appInfo = new AppInfo();
//                    total = usagestats.getTotalTimeInForeground();
//                    lastTimeUsed = usagestats.getLastTimeUsed();
//
//                    try {
//                        Field field = usagestats.getClass().getDeclaredField("mLaunchCount");
//                        if (field != null) {
//                            launchcount = (int) field.get(usagestats);
//                        } else launchcount = -1;
//                    } catch (Exception e) {
//                        Log.d(TAG1, e.getMessage());
//                    }
//                    appInfo.setUsageStats(usagestats);
//                    appInfo.setTotalTimeForeground();
//                    appInfo.setLaunchCount();
//
//                    appInfo.setLastTimeUsed();
//                    appInfo.setPackagename(packagename);
//                    Drawable app_icon = applicationInfo.loadIcon(pm);
//                    appInfo.setApp_icon(app_icon);
//                    String app_name = applicationInfo.loadLabel(pm).toString();
//                    appInfo.setApp_name(app_name + "\n" + "运行约" + (total/60000) + "分钟" + "\n"
//                            + "\n" + "最后启动时间：" + formatData(lastTimeUsed, sdf));
//                    boolean isUserApp = filterApp(applicationInfo);
//                    appInfo.setUserApp(isUserApp);
//                    appInfos.add(appInfo);
//                }
//            }
//        }
////            List<ConfigurationStats> configurationStats = usm.queryConfigurations(UsageStatsManager.INTERVAL_BEST, starttime, endtime);
////            if (configurationStats == null || configurationStats.isEmpty()) {
////                try {
////                    context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
////                } catch (Exception e) {
////                    Log.d(TAG1, e.getMessage());
////                }
////            }
////            else{
////                for(ConfigurationStats configurationStats1: configurationStats){
////                   int a= configurationStats1.getActivationCount();
////                   long t = configurationStats1.getTotalTimeActive();
////                    Log.d("confi", " "+a+"  "+formatData(t,sdf));
////                }
////            }
//
//        return appInfos;
//    }
//
//    public static  String formatData(long time,SimpleDateFormat sdf)
//    {
//        Date date = new Date();
//        date.setTime(time);
//        String string = sdf.format(date);
//        return string;
//
//    }
//
//
//
//
//    //判断应用程序是否是用户程序
//    public boolean filterApp(ApplicationInfo info) {
//        //原来是系统应用，用户手动升级
//        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
//            return true;
//            //用户自己安装的应用程序
//        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//            return true;
//        }
//        return false;
//    }
//
//}
