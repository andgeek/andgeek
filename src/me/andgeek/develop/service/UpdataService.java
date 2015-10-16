package me.andgeek.develop.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 检测安装更新文件的助手类
 */
@SuppressLint("NewApi")
public class UpdataService extends Service {
    
    /** 安卓系统下载类 **/
    DownloadManager mDownloadManager;
    
    /** 接收下载完的广播 **/
    DownloadCompletedReceiver mCompleteReceiver;
    
    private SharedPreferences prefs;
    
    private static final String DL_ID = "downloadId";
    
    public static final String DL_NAME = "downFileName";
    
    public static final String DL_URL = "downUrl";
    
    /** 初始化下载器 **/
    private void initDownManager(Intent intent) {
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mCompleteReceiver = new DownloadCompletedReceiver();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String downName = intent.getStringExtra(DL_NAME);
        if (downName == null || downName.trim().equals("")) {
            throw new NullPointerException("downName---------下载名称不能为空");
        }
        String downUrl = intent.getStringExtra(DL_URL);
        if (downUrl == null || downUrl.trim().equals("")) {
            throw new NullPointerException("downUrl---------下载地址不能为空");
        }
        
        if (!prefs.getString(DL_NAME, "").equals(downName)) {
            // 设置下载地址
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downUrl));
            // 设置允许使用的网络类型，这里是移动网络和wifi都可以
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            // 下载时，通知栏显示途中
            request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
            request.setVisibleInDownloadsUi(true);
            // 设置下载后文件存放的位置
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "baidumusic.apk");
            // 将下载请求放入队列
            long id = mDownloadManager.enqueue(request);
            // 保存id
            prefs.edit().putLong(DL_ID, id).apply();
            prefs.edit().putString(DL_NAME, downName).commit();
        }
        else {
            // 下载已经开始，检查状态
            queryDownloadStatus();
        }
        // 注册下载广播
        registerReceiver(mCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 调用下载
        initDownManager(intent);
        return START_NOT_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        
        return null;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销下载广播
        if (mCompleteReceiver != null) {
            unregisterReceiver(mCompleteReceiver);
        }
    }
    
    // 接受下载完成后的intent
    class DownloadCompletedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // 这里可以取得下载的id，这样就可以知道哪个文件下载完成了。适用与多个下载任务的监听
                Log.v("down", "" + intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0));
                queryDownloadStatus();
            }
        }
    }
    
    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong(DL_ID, 0));
        Cursor c = mDownloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("down", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("down", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    // 正在下载，不做任何事情
                    Log.v("down", "STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    // 完成
                    Log.v("down", "下载完成");
                    // 自动安装apk
                    installAPK(mDownloadManager.getUriForDownloadedFile(prefs.getLong(DL_ID, 0)));
                    
                    break;
                case DownloadManager.STATUS_FAILED:
                    // 清除已下载的内容，重新下载
                    Log.v("down", "STATUS_FAILED");
                    mDownloadManager.remove(prefs.getLong(DL_ID, 0));
                    prefs.edit().clear().commit();
                    break;
            }
        }
    }
    
    /**
     * 安装apk文件
     */
    private void installAPK(Uri apk) {
        Intent intents = new Intent();
        intents.setAction("android.intent.action.VIEW");
        intents.addCategory("android.intent.category.DEFAULT");
        intents.setDataAndType(apk, "application/vnd.android.package-archive");
        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intents);
    }
    
}
