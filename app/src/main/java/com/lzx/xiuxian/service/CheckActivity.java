//package com.lzx.xiuxian.service;
//
//
//import android.app.Activity;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class CheckActivity extends AppCompatActivity {
//
//
//    protected static final int SUCCESS_GET_APPLICAITON = 0;
//
//    //布局中的各个控件
//    private RelativeLayout rl_loading;
//    private ListView lv_appmanage;
//    private TextView tv_title;
//    private  Button ljr;
//    //包管理器
//    private PackageManager pm;
//    //获取手机应用信息的业务类
//    private AppInfoService appInfoService;
//    //手机应用app信息集合
//    private List<AppInfo> appInfos;
//    //用户应用程序信息集合
//    private List<AppInfo> userAppInfos;
//    //是否是所有的app程序,默认为true
//    private boolean isAllApp = true;
//    //AppManagerAdapter适配器对象
//    private AppManagerAdapter mAdapter;
//
//    private PopupWindow mPopupWindow;
//    //mHandler方法
//    private Handler mHandler = new Handler(){
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SUCCESS_GET_APPLICAITON:
//                    //给listview去绑定数据，隐藏加载的控件
//                    mAdapter = new AppManagerAdapter(getApplicationContext(),userAppInfos);
//                    //设置数据
//                    lv_appmanage.setAdapter(mAdapter);
//                    //隐藏RelativeLayout
//                    rl_loading.setVisibility(View.GONE);
//                    //View.VISIBLE (控件显示)View.INVISIBLE（控件隐藏  但占据空间）  View.GONE（控件隐藏  不占据空间）
//                    break;
//
//                default:
//                    break;
//            }
//        };
//    };
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_check);
//        ActionBar actionbar = getSupportActionBar();
//        if(actionbar != null){
//            actionbar.hide();
//        }
//
//        ImageButton button1 =(ImageButton) findViewById(R.id.back);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Activity) getBaseContext()).finish();
//            }
//        });
//     /*   ljr=(Button)findViewById(R.id.ljr);
//        ljr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appInfos.
//            }
//        });*/
//
//
//        //获取布局中的控件
//        rl_loading = (RelativeLayout) findViewById(R.id.rl_loading);
//        lv_appmanage = (ListView) findViewById(R.id.lv_appmanage);
//        tv_title = (TextView) findViewById(R.id.tv_title);
//        //实例化AppInfoService对象
//        appInfoService = new AppInfoService(this);
//        //包管理器
//        pm = getPackageManager();
//
//        //在子线程中获取手机安装的应用程序信息
//        new Thread(){
//            public void run() {
//                appInfos = appInfoService.getAppInfos();
//
//                userAppInfos = new ArrayList<AppInfo>();
//                for(AppInfo appInfo:appInfos){
//                    if(appInfo.isUserApp()){
//                        userAppInfos.add(appInfo);
//                    }
//                }
//                Message msg = new Message();
//                msg.what = SUCCESS_GET_APPLICAITON;
//                mHandler.sendMessage(msg);
//            };
//        }.start();
//
//
//    }
//
//}
