package me.andgeek.develop.view;

import me.andgeek.develop.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingDialog extends Dialog {
    
    private ImageView mImageView;
    
    private Animation mAnimation;
    
    private TextView mTvMsg;
    
    public LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    
    public LoadingDialog(Context context, int theme) {
        super(context, R.style.Translucent_NoTitle);
        initView();
    }
    
    public LoadingDialog(Context context) {
        this(context, -1);
    }
    
    protected void initView() {
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        
        mImageView = (ImageView) findViewById(R.id.image_view);
        mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        mAnimation.setInterpolator(lin);
//        mTvMsg = (TextView) findViewById(R.id.tv_msg);
//        //mTvMsg.setText(msg);
//        mImageView.post(new Runnable() {
//            @Override
//            public void run() {
//                mImageView.startAnimation(mAnimation);
//            }
//        });
    }
    
    @Override
    public void dismiss() {
        super.dismiss();
//        mImageView.clearAnimation();
    }
    
    
}
