package component.baselib.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 功能描述：时间工具类
 *
 * @author Justice
 */
public class DateUtils {

    public final static String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public final static String DATE_FORMAT_YM = "yyyy-MM";
    public final static String DATE_FORMAT_HMS = "HH:mm:ss";
    public final static String DATE_FORMAT_HM = "HH:mm";


    public static Date date = null;

    public static String dateStr = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;


    /**
     * 将字符串时间格式转换为Date类型
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date str2Date(String dateStr, String format) {
        dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将字符串时间格式(yyyy-MM-dd HH:mm:ss)转换为Date类型
     *
     * @param dateStr
     * @return
     */
    public static Date str2Date(String dateStr) {
        dateFormat = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将Date类型时间转换为字符串时格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format) {
        dateFormat = new SimpleDateFormat(format);
        try {
            dateStr = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 将Date类型时间转换为字符串时格式(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        dateFormat = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        try {
            dateStr = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }


    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            //            String dt = dateStr.replaceAll("-", "/");
            String dt = dateStr;
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 返回当前时间的格式为 yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        return sdf.format(System.currentTimeMillis());
    }

    //毫秒转秒
    public static String long2String(long time) {
        //毫秒转秒
        int sec = (int) time / 1000;
        int min = sec / 60;    //分钟
        sec = sec % 60;        //秒
        if (min < 10) {    //分钟补0
            if (sec < 10) {    //秒补0
                return "0" + min + ":0" + sec;
            } else {
                return "0" + min + ":" + sec;
            }
        } else {
            if (sec < 10) {    //秒补0
                return min + ":0" + sec;
            } else {
                return min + ":" + sec;
            }
        }
    }

    /**
     * 毫秒转化时分秒毫秒
     *
     * @param ms
     * @return
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "d");
        }
        if (hour > 0) {
            sb.append(hour + "h");
        }
        if (minute > 0) {
            sb.append(minute + "′");
        }
        if (second > 0) {
            sb.append(second + "″");
        }
        return sb.toString();
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDateString() {
        String result = null;

        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);
        int second = mCalendar.get(Calendar.SECOND);

        result = String.format("%d%02d%02d%02d%02d%02d", year, month, day,
                hour, minute, second);

        return result;
    }

    /**
     * 获取当前日期(不含时分秒)
     *
     * @return
     */
    public static String getCurrentDateWithOutTimeString() {
        String result = null;

        long time = System.currentTimeMillis();
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        result = year + "-" + month + "-" + day;
        return result;
    }

    /**
     * 获取当前日期(不含时分秒)
     *
     * @return
     */
    public static String getYesterdayWithOutTimeString() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, -1);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(mCalendar.getTime());
        return yesterday;
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回指定日期的周数
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取制定年月的周数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getWeekCountOfMonth(int year, int month) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);// Java月份从0开始算
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH); // 获取当前月的周数
        return weekOfMonth;
    }

    /**
     * 获取当前时间属于指定年月的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH); // 获取当前月的第几周
        return weekOfMonth;
    }

    /**
     * 获取当前时间是当月第几周
     *
     * @return
     */
    public static int getCurrentWeekOfMonth() {
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH); // 获取当前月的第几周
        return weekOfMonth;
    }

    /**
     * 获取指定年周的第一天和最后一天
     *
     * @param year
     * @param week
     * @param flag true-返回第一天日期,false-返回最后一天日期
     * @return
     */
    public static Date getDayByWeek(int year, int week, boolean flag) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        if (!flag)
            cal.setTimeInMillis(cal.getTimeInMillis() + 6 * 24 * 60 * 60 * 1000);
        return cal.getTime();
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：返回0101型日期格式
     *
     * @param dateStr
     * @return
     */
    public static String getDateMd(String dateStr) {
        if (dateStr.length() == 1) {
            return "0101";
        } else {
            int month = getMonth(dateStr);
            int day = getDay(dateStr);
            return (month > 10 ? month : "0" + month) + "" + (day > 10 ? day : "0" + day);
        }
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }


    /**
     * 获取指定年周的第一天和最后一天
     *
     * @param year
     * @param month
     * @param flag  true-返回第一天日期,false-返回最后一天日期
     * @return
     */
    public static Date getFirstOrLastOfMonth(int year, int month, boolean flag) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //        cal.set(Calendar.MONTH, Calendar.JANUARY);
        if (!flag) {
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
        }

        return cal.getTime();
    }

    /**
     * 获取某年第一天或最后一天
     *
     * @param currentYear
     * @param flag
     * @return
     */
    public static Date getFirstOrLastOfYear(int currentYear, boolean flag) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, currentYear);
        c.set(Calendar.DAY_OF_YEAR, 1);//本年第一天
        //        c.set(Calendar.YEAR, 1);
        if (!flag) {
            int lastDay = c.getActualMaximum(Calendar.DAY_OF_YEAR);
            c.set(Calendar.DAY_OF_YEAR, lastDay);
            //            c.set(Calendar.YEAR, currentYear + 1);
            //            c.add(Calendar.DAY_OF_YEAR, -1);//本年最后一天
        }
        return c.getTime();
    }


    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param time   时间字符串
     * @param format 时间字符串对应的时间格式
     * @return
     */

    public static Date stringToDate(String time, String format) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date s_date = (Date) sdf.parse(time);
            return s_date;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * 获取某一天属于星期几
     *
     * @param dateStr
     * @return
     */
    public static int getWeekIndex(String dateStr) {
        return getWeekIndex(parseDate(dateStr, DATE_FORMAT_YMD));
    }

    //根据日期取得星期几
    public static int getWeekIndex(Date date) {
        int[] weeks = {1, 2, 3, 4, 5, 6, 7};
        Calendar cal = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 1) {
            week_index = 1;
        }
        return weeks[week_index - 1];
        //        return weeks[week_index];
    }

    //根据数字返回实际的星期几
    public static String getWeekDay(int num) {
        String day = "";
        switch (num) {
            case 1:
                day = "星期天";
                break;
            case 2:
                day = "星期一";
                break;
            case 3:
                day = "星期二";
                break;
            case 4:
                day = "星期三";
                break;
            case 5:
                day = "星期四";
                break;
            case 6:
                day = "星期五";
                break;
            case 7:
                day = "星期六";
                break;
            default:
                day = "";
                break;
        }
        return day;
    }

    /**
     * 获取星期
     *
     * @param dateStr 年月日
     * @return
     */
    public static String getWeek(String dateStr) {
        Date date = parseDate(dateStr, DATE_FORMAT_YMD);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    //    //根据日期取得星期几
    //    public static String getWeek(Date date){
    //        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    //        String week = sdf.format(date);
    //        return week;
    //    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        return Integer.parseInt(new SimpleDateFormat("yyyy-MM").format(new Date()).split("-")[1]);
    }

    /**
     * 获取指定日期的年份
     *
     * @param dateStr 年月日
     * @return
     */
    public static int getYear(String dateStr) {
        return Integer.parseInt(dateStr.split("-")[0]);
    }

    /**
     * 获取指定日期的月份
     *
     * @param dateStr 年月日
     * @return
     */
    public static int getMonth(String dateStr) {
        return Integer.parseInt(dateStr.split("-")[1]);
    }

    /**
     * 获取指定日期的日份
     *
     * @param dateStr 年月日
     * @return
     */
    public static int getDay(String dateStr) {
        return Integer.parseInt(dateStr.split("-")[2]);
    }

    /**
     * 获取当前周数
     *
     * @return
     */
    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setTime(new Date());
        return calendar.get(Calendar.WEEK_OF_YEAR);

    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentDay() {
        return Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-")[2]);
    }

    /**
     * 获取第二天日期
     *
     * @return
     */
    public static String getNextDay() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取当前年份指定月份的天数
     *
     * @param month 月份
     * @return
     */
    public static int getDateOfMonth(int month) {
        return getDateOfMonth(getCurrentYear(), month);
    }

    /**
     * 获取指定年份指定月份的天数
     *
     * @param month 月份
     * @return
     */
    public static int getDateOfMonth(int year, int month) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);// Java月份从0开始算
        int dateOfMonth = calendar.getActualMaximum(Calendar.DATE); // 获取当前月的天数
        return dateOfMonth;
    }

    /**
     * 取得指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }


    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的第一天
     *
     * @param year
     * @param month
     * @param week
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int month, int week) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.WEEK_OF_MONTH, week);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        //        c.set(Calendar.DAY_OF_WEEK, 1); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @return
     */
    public static Date getLastDayOfWeek(int year, int month, int week) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.WEEK_OF_MONTH, week);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        //        c.set(Calendar.DAY_OF_WEEK, 7); // Sunday
        return c.getTime();
    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        return isTheDay(date, new Date());
    }

    /**
     * 是否是指定日期
     *
     * @param date 需要判断的日期
     * @param day  参照日期
     * @return
     */
    public static boolean isTheDay(final Date date, final Date day) {
        return date.getTime() >= dayBegin(day).getTime()
                && date.getTime() <= dayEnd(day).getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 获取指定时间一小时之前的时间
     *
     * @param dateStr
     * @return
     */
    public static Date subHour(String dateStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(parseDate(dateStr, DATE_FORMAT_YMDHMS));
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - 1);
        return c.getTime();
    }

    public static String addHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + 1);
        return format(c.getTime(), DATE_FORMAT_YMDHMS);
    }

    public static void main(String[] args) {
        Date d = new Date();
        // System.out.println(d.toString());
        // System.out.println(formatDate(d).toString());
        // System.out.println(getMonthBegin(formatDate(d).toString()));
        // System.out.println(getMonthBegin("2008/07/19"));
        // System.out.println(getMonthEnd("2008/07/19"));
        System.out.println(addDate(d, 15).toString());

    }

    /**
     * 获取相差的小时数
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static int getHours(Date endDate, Date startDate) {

        long nh = 1000 * 60 * 60;

        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();

        // 计算差多少小时
        long hour = diff / nh;

        return (int) hour;
    }

    /**
     * 获取时间差
     *
     * @param endDate
     * @param startDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date startDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 获取当前日期(不含时分秒)
     *
     * @return
     */
    public static String getToday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.add(Calendar.DATE, 0);
        String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(mCalendar.getTime());
        return yesterday;
    }

    /**
     * 得到当前年月份
     *
     * @return
     */
    public static String getTodayWithoutDay() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        return year + "-"
                + ((month + 1) < 10 ? "0" + (month + 1) : (month + 1));
    }

    /**
     * 日期格式化
     *
     * @return
     */
    public static String DateformatTime(Date date) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换为日期
        long time = date.getTime();
        if (isThisYear(date)) {//今年
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            if (isToday(date)) { //今天
                int minute = minutesAgo(time);
                if (minute < 60) {//1小时之内
                    if (minute <= 3) {//一分钟之内，显示刚刚
                        //return "";
                        return simpleDateFormat.format(date);
                    } else {
                        //return minute + "分钟前";
                        return simpleDateFormat.format(date);
                    }
                } else {
                    return simpleDateFormat.format(date);
                }
            } else {
                if (isYestYesterday(date)) {//昨天，显示昨天
                    return "昨天 " + simpleDateFormat.format(date);
                } else if (isThisWeek(date)) {//本周,显示周几
                    String weekday = null;
                    if (date.getDay() == 1) {
                        weekday = "周一";
                    }
                    if (date.getDay() == 2) {
                        weekday = "周二";
                    }
                    if (date.getDay() == 3) {
                        weekday = "周三";
                    }
                    if (date.getDay() == 4) {
                        weekday = "周四";
                    }
                    if (date.getDay() == 5) {
                        weekday = "周五";
                    }
                    if (date.getDay() == 6) {
                        weekday = "周六";
                    }
                    if (date.getDay() == 0) {
                        weekday = "周日";
                    }
                    return weekday + " " + simpleDateFormat.format(date);
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
                    return sdf.format(date);
                }
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(date);
        }
    }

    /**
     * String型时间戳格式化
     *
     * @return
     */
    public static String LongFormatTime(String time) {
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //转换为日期
        Date date = new Date();
        date.setTime(Long.parseLong(time));
        return DateformatTime(date);
    }


    private static int minutesAgo(long time) {
        return (int) ((System.currentTimeMillis() - time) / (60000));
    }


    private static boolean isYestYesterday(Date date) {
        Date now = new Date();
        return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth()) && (date.getDate() + 1 == now
                .getDate());
    }

    private static boolean isThisWeek(Date date) {
        Date now = new Date();
        if ((date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())) {
            if (now.getDay() - date.getDay() < now.getDay() && now.getDate() - date.getDate() > 0 && now.getDate() -
                    date.getDate() < 7) {
                return true;
            }
        }
        return false;
    }

    private static boolean isThisYear(Date date) {
        Date now = new Date();
        return date.getYear() == now.getYear();
    }

    /**
     * 毫秒转化成相应类型的日期
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        Date date = new Date(time);
        return sf.format(date);
    }

    /**
     * 去掉时间的时分秒
     *
     * @param time
     * @return
     */
    public static String splitTime(String time) {
        return time.split(" ")[0];
    }

    /**
     * 获取前一天的日期
     *
     * @return
     */
    public static String getBeforeDay() {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 把date转成年月日时分秒
     *
     * @param date
     * @return
     */
    public static String formatDateHMS(Date date) {
        return format(date, DATE_FORMAT_YMDHMS);
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return 0相等   1大于  -1小于
     */
    public static int isDateOneBigger(String str1, String str2) {
        int flag = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMDHMS);
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            flag = 1;
        } else if (dt1.getTime() < dt2.getTime()) {
            flag = -1;
        }
        return flag;
    }

}
