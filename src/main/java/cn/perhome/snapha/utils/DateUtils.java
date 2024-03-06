package cn.perhome.snapha.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public  class DateUtils {

    private static final String DATA_FORMAT_DATA_SHORT_SYMBOL_X = "yyyy-MM-dd";
    private static final String DATA_FORMAT_DATA_SHORT_SYMBOL_Y = "yyyy-MM-dd HH:mm:ss";

    public static String getToday() {
        Date             date       = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String plusDate(String strDate, int offsetDays) {
        LocalDate date = LocalDate.parse(strDate);
        date = date.plusDays(offsetDays);
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static Date getMonthLastDay(Date hitDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hitDate);
        calendar.set(Calendar.DAY_OF_MONTH,0);
        calendar.add(Calendar.MONTH,1);
        return calendar.getTime();
    }

    public static String minusDate(String strDate, int offsetDays) {
        LocalDate date = LocalDate.parse(strDate);
        date = date.minusDays(offsetDays);
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static Date offsetsDate(Date date, int offsetDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, offsetDays);
        return cal.getTime();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param form
     *          开始时间
     * @param to
     *          结束时间
     * @return
     *          相差天数
     */
    public static int differentDays(Date form, Date to) {
        return (int) ((to.getTime() - form.getTime()) / (1000*3600*24));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param form
     *          开始时间
     * @param to
     *          结束时间
     * @return
     *          相差小时数
     */
    public static int differentHours(Date form, Date to) {
        return (int) ((to.getTime() - form.getTime()) / (1000*3600));
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param form
     *          开始时间
     * @param to
     *          结束时间
     * @return
     *          相差分钟数
     */
    public static int differentMinute(Date form, Date to) {
        return (int) ((to.getTime() - form.getTime()) / (1000*60));
    }

    /**
     * 判断两个时间相差多少个月
     * @param form
     *          开始时间
     * @param to
     *          结束时间
     * @return
     *          相差月数
     */
    public static int differentMonth(Date form, Date to){
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(form);
        aft.setTime(to);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 把日期格式化为字符串
     * @param date
     *          日期
     * @param format
     *          格式
     * @return
     *          返回格式化之后的字符串
     */
    public static String dateToString(Date date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 把日期格式化为字符串
     * @param date
     *          日期
     * @param format
     *          格式
     * @return
     *          返回格式化之后的字符串
     */
    public static Date stringToDate(String date,String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 通过传入的日期加指定的天数
     * @param date
     *          日期
     * @param day
     *          天数
     * @return
     *          相加后的天数
     */
    public static Date getNextDay(Date date,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,day);
        return calendar.getTime();
    }

    /**
     * 通过传入的日期加指定的分钟数
     * @param date
     *          日期
     * @param minute
     *          天数
     * @return
     *          相加后的天数
     */
    public static Date getNextMinute(Date date,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 通过传入的日期加指定的天数
     * @param date
     *          日期
     * @param day
     *          天数
     * @return
     *          相加后的天数
     */
    public static String getNextDay(String date ,int day,String format){
        return dateToString(getNextDay(stringToDate(date,format),day),format);
    }

    /**
     * 通过传入的日期加指定的年数
     * @param date
     *          日期
     * @param year
     *          年数
     * @return
     *          计算后的日期
     */
    public static Date getNextYear(Date date,int year){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,year);
        return calendar.getTime();
    }
    /**
     * 通过传入的日期加指定的月数
     * @param date
     *          日期
     * @param month
     *          月数
     * @return
     *          计算后的日期
     */
    public static Date getNextMonth(Date date,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,month);
        return calendar.getTime();
    }


    /**
     * 获取当前的时间
     * @return
     *          返回当前的时间
     */
    public static Date getNowDate(){
        return new Date();
    }

    /**
     * 获取当前的时间（yyyy-MM-dd）
     * @return
     *          返回当前的时间
     */
    public static String getNowDayString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_DATA_SHORT_SYMBOL_X);
        return dateFormat.format(getNowDate());
    }
    /**
     * 获取当前的时间（yyyy-MM-dd HH:mm:ss）
     * @return
     *          返回当前的时间
     */
    public static String getNowTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_DATA_SHORT_SYMBOL_Y);
        return dateFormat.format(getNowDate());
    }

    /**
     * 获取当前的时间
     * @return
     *          返回当前的时间
     */
    public static Date getNowDayDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_DATA_SHORT_SYMBOL_X);
        return stringToDate(dateFormat.format(getNowDate()),DATA_FORMAT_DATA_SHORT_SYMBOL_X);
    }
    /**
     * 获得当前时间前几天的日期
     * @param i 天数
     * @return
     */
    public static Date getBeforeDayDate(int i){
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -i);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT_DATA_SHORT_SYMBOL_X);
        return stringToDate(dateFormat.format(cal.getTime()),DATA_FORMAT_DATA_SHORT_SYMBOL_X);
    }
    /**
     * 获得某天23:59:59点时间
     * @return
     */
    public static Date getTimesNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得某天0点时间
     * @return
     */
    public static Date getTimesMorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @Description 是否为当天24h内
     * @author guo
     * @param inputJudgeDate 要判断是否在当天24h内的时间
     * @return
     * boolean
     */
    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 把日期格式化为字符串
     * @param date
     *          日期
     * @return
     *          返回格式化之后的字符串
     */
    public static Date stringToDateFormat(String date){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间戳转date
     * @return
     */
    public static Date timeToDate (Long time) {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(time);
        Date date= null;
        try {
            date = format.parse(d);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

