package midian.baselib.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/14.
 */
public class DateUtil {
    private static SimpleDateFormat DATE_FORMAT_TILL_SECOND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final  String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    /**
     * 日期字符串转换为Date
     * @param dateStr
     * @param format
     * @return
     */
    public static Date strToDate(String dateStr, String format) {
        Date date = null;

        if (!TextUtils.isEmpty(dateStr)) {
            DateFormat df = new SimpleDateFormat(format);
            try {
                date = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 日期转换为字符串
     * @param timeStr
     * @param format
     * @return
     */
    public static String dateToString(String timeStr, String format)  {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        // 判断是否是今年
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 如果是今年的话，才去“xx月xx日”日期格式
        return DATE_FORMAT_TILL_SECOND.format(date);
    }
    
    
    public static String today(){
    	return DATE_FORMAT_TILL_SECOND.format(new Date());
    }

    static final public String old="2016-04-09 23:59:00";
    public static boolean oldDate(){
    	Date date =strToDate(old, DATE_FORMAT);
    	return System.currentTimeMillis()>date.getTime();
    }
    /**
     * 日期逻辑
     * @param dateStr 日期字符串
     * @return
     */
    public static String timeLogic(String dateStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        long now = calendar.getTimeInMillis();
        Date date = strToDate(dateStr, DATE_FORMAT);
        calendar.setTime(date);
        long past = calendar.getTimeInMillis();

        // 相差的秒数
        long time = (now - past) / 1000;

        StringBuffer sb = new StringBuffer();
        if (time > 0 && time < 60) { // 1小时内
            return sb.append(time + "秒前").toString();
        } else if (time > 60 && time < 3600) {
            return sb.append(time / 60+"分钟前").toString();
        } else if (time >= 3600 && time < 3600 * 24) {
            return sb.append(time / 3600 +"小时前").toString();
        }else if (time >= 3600 * 24 && time < 3600 * 48) {
            return sb.append("昨天").toString();
        }else if (time >= 3600 * 48 && time < 3600 * 72) {
            return sb.append("前天").toString();
        }else if (time >= 3600 * 72) {
            return dateToString(dateStr, DATE_FORMAT);
        }
        return dateToString(dateStr, DATE_FORMAT);
    }
}
