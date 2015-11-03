package me.andgeek.develop.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 描述： 字体单位转换工具
 * 
 * @creator Administrator
 * @create-time 2015-9-6 下午2:10:47
 * @revision 1.0
 */
public class ScreenUtils {
    
    private static DisplayMetrics mDisplayMetrics;
    
    private static ScreenUtils utils = null;
    
    private ScreenUtils() {
    }
    
    public static ScreenUtils getInstance(Context mContext) {
        if (utils == null)
            utils = new ScreenUtils();
        if (mContext != null) {
            mDisplayMetrics = new DisplayMetrics();
            WindowManager mWindowManager = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE));
            mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
        }
        return utils;
    }
    
    /**
     * 获取屏幕密度 // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public float getDensity() {
        return mDisplayMetrics.density;
    }
    
    /**
     * 获取屏幕宽度
     */
    public int getmScreenWidth() {
        return mDisplayMetrics.widthPixels;
    }
    
    /**
     * 获取屏幕高度
     */
    public int getmScreenHeight() {
        return mDisplayMetrics.heightPixels;
    }
    
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * 
     * @param pxValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    
    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * 
     * @param dipValue
     * @param scale
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    
}
