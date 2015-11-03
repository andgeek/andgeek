package me.andgeek.develop.util;

import me.andgeek.develop.service.UpdataService;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public final class DialogUtils {
    
    /**
     * @description 应用更新对话框
     * @date 2015年8月26日
     * @param context
     * @param name
     *            下载保存的名字
     * @param currentVersionCode
     *            服务器版本号
     * @param url
     *            下载地址
     * @param desc
     *            对话框描述
     */
    public static void showUpdateDialog(final Context context,
                                        final String name,
                                        final int currentVersionCode,
                                        final String desc,
                                        final String url) {
        int verCode = PackageUtils.getVersionCode();
        if (verCode >= currentVersionCode) {
            return;
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("更新提示");
        builder.setMessage(desc);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putString(UpdataService.DL_NAME, name);
                bundle.putString(UpdataService.DL_URL, url);
                IntentUtils.startService(context, UpdataService.class, bundle);
            }
        });
        
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
