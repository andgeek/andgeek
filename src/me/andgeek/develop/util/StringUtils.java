package me.andgeek.develop.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述： 字符串工具
 * 
 * @creator Administrator
 * @create-time 2014-3-13 下午6:10:47
 * @revision 1.0
 */
public final class StringUtils {
    
    /**
     * @Description: 判断String是否为空,true代表空，false代表非空
     * @param string
     * @return boolean
     * @throws
     */
    public static boolean isNullOrEmpty(String string) {
        if (string == null) {
            return true;
        }
        else if ("".equalsIgnoreCase(string.trim())) {
            return true;
        }
        return false;
    }
    
    /**
     * @description 手机号码验证
     * @date 2015年8月17日
     * @param mobileNumber
     *            手机号码
     * @return
     */
    public static boolean isMobileNumber(String mobileNumber) {
        return Pattern.compile("^1\\d{10}$").matcher(mobileNumber).matches();
    }
    
    /**
     * @description 邮编格式验证
     * @date 2015年8月17日
     * @param zipCode
     *            邮政编码
     * @return
     */
    public static boolean isZipCode(String zipCode) {
        Pattern p = Pattern.compile("^\\d{6}$");
        Matcher m = p.matcher(zipCode);
        return m.matches();
    }
    
    /**
     * @description RMB格式化 ,保留两位小数
     * @param str
     *            RMB字符串
     * @return 如:0.01
     */
    public static String formatMoney(String str) {
        try {
            Double value = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(value);
        }
        catch (Exception e) {
            return "0.00";
        }
    }
    
    /**
     * @description RMB格式化 ,保留两位小数
     * @param d
     *            RMB
     * @return 如:0.01
     */
    public static String formatMoney(double d) {
        return formatMoney(String.valueOf(d));
    }
    
    /**
     * 检查是否是正确的email
     * 
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    /**
     * 去除字符串里的空格
     * 
     * @param str
     * @return
     */
    public static String removeAllSpace(String str) {
        String tmpstr = str.replace(" ", "");
        return tmpstr;
    }
    
}
