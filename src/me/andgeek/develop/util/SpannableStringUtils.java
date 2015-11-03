package me.andgeek.develop.util;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;

/**
 * 描述： Spannable工具
 * 
 * @creator Administrator
 * @create-time 2015-9-6 下午6:10:47
 * @revision 1.0
 */
public class SpannableStringUtils {
    /**
     * 设置String颜色
     * 
     * @param str
     *            传入的字符串
     * @param color
     *            设置的颜色值
     * @return
     */
    public static SpannableStringBuilder setTextColor(CharSequence str, int color) {
        SpannableStringBuilder spanString = new SpannableStringBuilder(str);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
    
    /**
     * 设置String颜色，大小
     * 
     * @param str
     *            传入的字符串
     * @param color
     *            设置的颜色值
     * @return
     */
    public static SpannableStringBuilder setColorAndSize(CharSequence str, int color, int size) {
        SpannableStringBuilder spanString = new SpannableStringBuilder(str);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(size);
        spanString.setSpan(span1, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
    
    /**
     * 设置个人主页菜单项
     * 
     * @param str
     *            传入的字符串
     * @param size
     *            设置大小
     * @return
     */
    public static SpannableStringBuilder setTextSize(CharSequence str, int size) {
        SpannableStringBuilder spanString = new SpannableStringBuilder(str);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(size);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
    
    /**
     * 删除线
     */
    public static SpannableStringBuilder setStrikeSpan(CharSequence str) {
        SpannableStringBuilder spanString = new SpannableStringBuilder(str);
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
    
    /**
     * 粗体
     */
    public static SpannableStringBuilder setBold(CharSequence str) {
        SpannableStringBuilder spanString = new SpannableStringBuilder(str);
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
    
    /**
     * 斜体
     */
    public static SpannableStringBuilder setItalic(CharSequence str) {
        SpannableStringBuilder spanString = new SpannableStringBuilder("BIBI");
        StyleSpan span = new StyleSpan(Typeface.ITALIC);
        spanString.setSpan(span, 0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
    
}
