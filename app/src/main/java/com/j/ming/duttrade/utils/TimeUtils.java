package com.j.ming.duttrade.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/4/2 0002.
 */

public class TimeUtils {
    public static final Long ONE_DAY = (long) (60 * 60 * 24);

    //时间操作
    private static SimpleDateFormat sdr_hm = new SimpleDateFormat("HH:mm", Locale.CHINA);
    private static SimpleDateFormat sdr_year = new SimpleDateFormat("yy", Locale.CHINA);
    private static SimpleDateFormat sdr_month = new SimpleDateFormat("MM", Locale.CHINA);
    private static SimpleDateFormat sdr_day = new SimpleDateFormat("dd", Locale.CHINA);
    private static SimpleDateFormat sdr_yM = new SimpleDateFormat("yy-MM", Locale.CHINA);
    private static SimpleDateFormat sdr_Md = new SimpleDateFormat("MM-dd", Locale.CHINA);
    private static SimpleDateFormat sdr_ymd = new SimpleDateFormat("yy/MM/dd", Locale.CANADA);
    private static SimpleDateFormat sdr_y4md = new SimpleDateFormat("yyyy/MM/dd", Locale.CANADA);
    private static SimpleDateFormat sdr_y4md_line_divide = new SimpleDateFormat("yyyy-MM-dd"
            , Locale.CANADA);
    private static SimpleDateFormat sdr_y4md_span_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
    private static SimpleDateFormat sdr_y4md_line_time = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CANADA);

    static {
        sdr_hm.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_year.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_month.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_day.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_yM.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_Md.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_ymd.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 中国北京时间，东八区
        sdr_y4md_line_divide.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        sdr_y4md_line_time.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    //时间操作
    public static String returnTime(long times) {
        Date date = new Date(times);
        Date cur_date = new Date(System.currentTimeMillis());
        String s_date = "";
        if (sdr_year.format(date).equals(sdr_year.format(cur_date))) {
            if (sdr_month.format(date).equals(sdr_month.format(cur_date)) && sdr_day.format(date).equals(sdr_day.format(cur_date))) {
                s_date = sdr_hm.format(date);
            } else {
                s_date = sdr_Md.format(date);
            }
        } else {
            s_date = sdr_yM.format(date);
        }
        return s_date;
    }

    public static String returnTime_ymd(long times) {
        Date date = new Date(times);
        return sdr_ymd.format(date);
    }

    /**
     * yyyy/MM/dd
     *
     * @return
     */
    public static String returnTime_y4md(long times) {
        Date date = new Date(times);
        return sdr_y4md.format(date);
    }

    /**
     * yyyy/MM/dd
     *
     * @param date_str
     * @return
     */
    public static long returnTimeStamps_y4md(String date_str) {
        try {
            return sdr_y4md.parse(date_str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回 2017-02-03 的格式
     *
     * @param timestamps
     * @return
     */
    public static String returnTime_y4md_line_divide(long timestamps) {
        Date date = new Date(timestamps);
        return sdr_y4md_line_divide.format(date);
    }

    /**
     * 返回 2017-02-03 19:20:36
     * @param timestamps
     * @return
     */
    public static String returnDate_Time(long timestamps){
        Date date = new Date(timestamps);
        return sdr_y4md_line_time.format(date);
    }

    public static long returnTimeStamps_DateTiem(String time){
        try {
            return sdr_y4md_span_time.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
