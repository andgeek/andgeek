package me.andgeek.develop.view;

import me.andgeek.develop.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context) {
        super(context);
    }
    
    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }
    
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
        spinner.start();
    }
    
    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.setVisibility(View.VISIBLE);
            txt.invalidate();
        }
    }
    
    public static ProgressDialog show(Context context,
                                      CharSequence message,
                                      boolean indeterminate,
                                      boolean cancelable,
                                      OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context, R.style.ProgressDialog);
        dialog.setTitle("");
        dialog.setContentView(R.layout.dialog_progress);
        TextView txt = (TextView) dialog.findViewById(R.id.message);
        if (message == null || message.length() == 0) {
            txt.setVisibility(View.GONE);
        }
        else {
            txt.setVisibility(View.VISIBLE);
            txt.setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(false);// 触摸不消失
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        if (isActivityAlive(context)) {
            dialog.show();
        }
        return dialog;
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static boolean isActivityAlive(Context context) {
        if (context == null) {
            return false;
        }
        Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT > 16) {
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        else {
            if (activity.isFinishing()) {
                return false;
            }
        }
        
        return true;
    }
}
