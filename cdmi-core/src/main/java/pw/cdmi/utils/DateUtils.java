/* 
 * 版权声明(Copyright Notice)：
 *     Copyright(C) 2017-2017 聚数科技成都有限公司。保留所有权利。
 *     Copyright(C) 2017-2017 www.cdmi.pw Inc. All rights reserved. 
 * 
 *     警告：本内容仅限于聚数科技成都有限公司内部传阅，禁止外泄以及用于其他的商业目
 */ 
package pw.cdmi.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;


/************************************************************
 * @Description:
 * <pre>
 * 提供了日期的各种处理方法.<br/>
 * </pre>
 * @author    伍伟
 * @version   3.0.1
 * @Project   Alpha CDMI Service Platform, cdmi-core Component. 2017年5月28日
 ************************************************************/
public final class DateUtils {
         
    /**
     * <p>
     * Sun Nov 6 08:49:37 1994 ; ANSI C's asctime() format
     * </p>
     */
    public static final String DATE_FORMAT_ANSIC = "EEE MMM d HH:mm:ss yyyy";
    
    /**
     * 默认的日期格式
     */
    public static final String DATE_FORMAT_MS_PATTERN = "yyyy-MM-dd HH:mm:ss sss";
    
    /**
     * 默认的日期格式
     */
    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";
    
    /**
     * <p>
     * Sunday, 06-Nov-94 08:49:37 GMT ; RFC 850, obsoleted by RFC 1036
     * </p>
     */
    public static final String DATE_FORMAT_RFC_850 = "EEEE, dd-MM-yy HH:mm:ss z";
    
    /**
     * <p>
     * Sun, 06 Nov 1994 08:49:37 GMT ; RFC 822, updated by RFC 1123
     * </p>
     */
    public static final String DATE_FORMAT_RFC_822 = "EEE, dd MMM yyyy HH:mm:ss z";
    
    /**
     * 日志名称格式
     */
    public static final String GLOBAL_LOG_FORMAT = "yyyy-MM-dd_HH-mm-ss";
    
    /** RFC 822 parser */
    public static final String RFC822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    // ISO 8601 format
    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    // Alternate ISO 8601 format without fractional seconds
    private static final String ALTERNATIVE_ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String SIMPLE_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm";
    
    public static String getToday()
    {
        return format(now());
    }
    
    public static Date parse(String strDate) throws ParseException
    {
        return StringUtils.isEmpty(strDate) ? null : parse(strDate, getDatePattern(), null);
    }
    
    
    public static String transferLongToDateString(long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    
    private static DateFormat getRfc822DateFormat() {
        SimpleDateFormat rfc822DateFormat =
                new SimpleDateFormat(RFC822_DATE_FORMAT, Locale.US);
        rfc822DateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));

        return rfc822DateFormat;
    }

    private static DateFormat getAlternativeIso8601DateFormat() {
        SimpleDateFormat df = new SimpleDateFormat(ALTERNATIVE_ISO8601_DATE_FORMAT, Locale.US);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df;
    }
    
    
    /**
     * 在日期上增加数个整月
     * 
     * @param date 时间
     * @param n 增加的月数
     * @return Date 转换后的Date对象
     */
    public static Date addMonth(Date date, int n)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    
    /**
     * 找出时间字符串中包含格式，如果不在已经定义的格式中返回null
     * 
     * @param date
     * @return
     */
    public static Date convertDateHeader(String date)
    {
        if (StringUtils.isBlank(date))
        {
            return null;
        }
        Date dateTime = null;
        try
        {
            dateTime = stringToDate(DateUtils.RFC822_DATE_FORMAT, date, "GMT");
        }
        catch (ParseException e)
        {
            try
            {
                dateTime = stringToDate(DateUtils.DATE_FORMAT_RFC_850, date, "GMT");
            }
            catch (ParseException e1)
            {
                try
                {
                    dateTime = stringToDate(DateUtils.DATE_FORMAT_ANSIC, date, "GMT");
                }
                catch (ParseException e2)
                {
                    dateTime = null;
                }
            }
        }
        return dateTime;
    }
    
    public static String converTimeStampToString(Timestamp ts)
    {
        
        return new SimpleDateFormat().format(ts);
    }
    
    /**
     * Date型转化到Calendar型
     * 
     * @param date 要转换的Date对象
     * @return Calendar 返回Calendar
     */
    public static Calendar date2Cal(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }
    
    /**
     * 按照预设格式返回指定时间的字符串
     * 
     * @param d 时间
     * @return Date 转换后的时间字符串
     */
    public static String dateToString(Date d)
    {
        SimpleDateFormat lenientDateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        if (d == null)
        {
            return null;
        }
        return lenientDateFormat.format(d);
    }
    
    /**
     * 按照预设格式返回指定时间的字符串
     * 
     * @param d 时间
     * @param pattern 格式
     * @return Date 转换后的时间字符串
     */
    public static String dateToString(Date d, String pattern)
    {
        if (d == null || StringUtils.isBlank(pattern))
        {
            return null;
        }
        
        SimpleDateFormat lenientDateFormat = new SimpleDateFormat(pattern);
        
        return lenientDateFormat.format(d);
    }
    
    /**
     * 格式化时间
     * 
     * @param pattern string类型
     * @param d 日期
     * @param timeZone Sting类型
     * @return sDate 格式化后的时间
     */
    public static String dateToString(String pattern, Date d, String timeZone)
    {
        DateFormat dFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        if (timeZone != null && timeZone.length() > 0)
        {
            Calendar calendar = Calendar.getInstance(new SimpleTimeZone(0, timeZone));
            dFormat.setCalendar(calendar);
        }
        String sDate = dFormat.format(d);
        return sDate;
    }
    
    /**
     * 格式化时间
     * 
     * @param pattern string类型
     * @param d 日期
     * @param timeZone Sting类型
     * @return sDate 格式化后的时间
     */
    public static String dateToString(String pattern, Date d)
    {
        DateFormat dFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        String sDate = dFormat.format(d);
        return sDate;
    }
    
    /**
     * 得到指定格式的Date字符串
     * 
     * @author calvin
     * @param cal 时间
     * @param pattern 格式
     * @return String 指定格式的字符串
     */
    public static String format(Calendar cal, String pattern)
    {
        return cal == null ? "" : new SimpleDateFormat(pattern).format(cal.getTime());
    }
    
    /**
     * 使用预设Format格式化Date成字符串
     * 
     * @author calvin
     * @param date 时间
     * @return String 预设格式的字符串
     */
    public static String format(Date date)
    {
        return date == null ? "" : format(date, getDatePattern());
    }
    
    /**
     * 得到指定格式的Date字符串
     * 
     * @author calvin
     * @param date 时间
     * @param pattern 格式
     * @return String 指定格式的字符串
     */
    public static String format(Date date, String pattern)
    {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }
    
    /**
     * 返回的标准时间字符串
     * 
     * @param cal 时间
     * @return String 转换后的时间字符串
     */
    public static String formatDefault(Calendar cal)
    {
        return cal == null ? "" : format(cal.getTime(), DATE_FORMAT_PATTERN);
    }
    
    /**
     * 得到预设Format的当前日期字符串
     * 
     * @return
     */
    public static String getDate()
    {
        return format(now());
    }
    
    /**
     * 获得默认的日期格式
     * 
     * @return String 默认的日期格式
     */
    public static String getDatePattern()
    {
        return DEFAULT_DATE_PATTERN;
    }
    
    public static long getDateTime(Date date)
    {
        if (date == null)
        {
            return 0L;
        }
        return date.getTime();
    }
    
    /**
     * 得到系统当前时间
     * 
     * @return
     */
    public static Date now()
    {
        return nowCal().getTime();
    }
    
    /**
     * 得到当前时间
     * 
     * @return String 返回当前时间
     */
    public static Calendar nowCal()
    {
        return Calendar.getInstance();
    }
    
    /**
     * 使用指定格式将字符串转为Date
     * 
     * @param strDate 时间字符串
     * @param pattern 时间格式
     * @return Date 转换后的Date对象
     * @throws ParseException 发生异常时抛出
     */
    public static Date parse(String strDate, String pattern, String timeZone) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        if (StringUtils.isNotEmpty(timeZone))
        {
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        
        return StringUtils.isEmpty(strDate) ? null : format.parse(strDate);
    }
    
    /**
     * 将常见的字符串时间转换成日历对象,供数据库操作使用 字符串格式必须严格满足,否则抛出异常
     * 
     * @param inDate 时间
     * @return Calendar 转换后的Calendar
     * @throws ParseException 发生异常时抛出
     */
    public static Calendar stringToCalendarDefault(String inDate) throws ParseException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        
        calendar.setTime(new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(inDate));
        return calendar;
    }
    
    /**
     * 转换字符串到日期类
     * 
     * @param pattern 日期显示模式
     * @param d 日期类
     * @param timeZone 时区，如果为空或者空字符串，则显示当前时区
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String pattern, String sTime, String timeZone) throws ParseException
    {
        DateFormat dFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        
        if (StringUtils.isNotBlank(timeZone))
        {
            Calendar calendar = Calendar.getInstance(new SimpleTimeZone(0, timeZone));
            dFormat.setCalendar(calendar);
        }
        Date date = dFormat.parse(sTime);
        return date;
    }
    
    /**
     * 获取N天以前的时间
     * 
     * @param now
     * @param days
     * @return
     */
    public static Date getDateBefore(Date now, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, -days);
        return cal.getTime();
    }
    
    /**
     * 得到N天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date now, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }
    
    
    /**
     * 获取多少分钟前的时间
     * @param now
     * @param minute
     * @return
     */
    public static Date getDateBeforeMinute(Date now, int minute)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MINUTE, -minute);
        return cal.getTime();
    }
    
    /** 
     * 获取N秒后的时间
     * 
     * @param now
     * @param seconds
     * @return 
     */
    public static Date getDateAfterSeconds(Date now, int seconds)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }
    
    /**----------------------------------------**/
    
    /** ISO 8601 parser */
    protected final SimpleDateFormat iso8601DateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    
    /** Alternate ISO 8601 parser without fractional seconds */
    protected final SimpleDateFormat alternateIo8601DateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    
    /** RFC 822 parser */
    protected final SimpleDateFormat rfc822DateParser = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    
    private static final Locale Default_Locale = Locale.ENGLISH;
    private static final String Default_TimeZone = "GMT";
    
	/**
	 * 短日期格式
	 */
	public final static int SHORT_DATEFORMAT	= 1;		//短日期格式
	/**
	 * 短日期格式（但有前导0,如：2004-08-08 形式)
	 */
	public final static int SHORT_DATEFORMAT_FULL = 5;
	/**
	 * 长日期格式
	 */
	public final static int LONG_DATEFORMAT 	= 2;		//长日期格式
	/**
	 * 短日期格式,2000年01月01日
	 */
	public final static int SHORT_DATEFORMAT_CN	= 3;		//短日期格式,2000年01月01日
	/**
	 * 长日期格式,2000年01月01日01:01:01
	 */
	public final static int LONG_DATEFORMAT_CN	= 4;		//长日期格式,2000年01月01日01:01:01
	
    public static void main(String args[]) {
        System.out.println(DATE_FORMAT_RFC_822.length());
        System.out.println(DATE_FORMAT_RFC_850.length());
        System.out.println(DATE_FORMAT_ANSIC.length());
    }    

    
    /**
     * 转换日期类到字符串
     * 
     * @param pattern
     *            日期显示模式
     * @param d
     *            日期类
     * @param timeZone
     *            时区，如果为空或者空字符串，则显示当前时区
     * @return
     */
    public static String dataToString(String pattern, Date d, String timeZone) {
        DateFormat dFormat = new SimpleDateFormat(pattern, Default_Locale);
        Calendar calendar  = null;
        if (!StringUtils.isBlank(timeZone)) {
            calendar = Calendar.getInstance(new SimpleTimeZone(0, timeZone));
        } else {
            calendar = Calendar.getInstance(new SimpleTimeZone(0, Default_TimeZone));
        }

        dFormat.setCalendar(calendar);
        String sDate = dFormat.format(d);
        return sDate;
    }
    
    /**
     * 转换日期类到字符串
     * 
     * @param pattern
     *            日期显示模式
     * @param d
     *            日期类
     * @param timeZone
     *            时区，如果为空或者空字符串，则显示当前时区
     * @return
     */
    public static String dataToString(String pattern, Date d) {
        return dataToString(pattern, d, Default_TimeZone);
    }
    
    /**
     * Constructs a new DateUtils object, ready to parse/format dates.
     */
    public DateUtils() {
        iso8601DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
        rfc822DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
        alternateIo8601DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }
    
    /**
     * 找出时间字符串中包含格式，如果不在已经定义的格式中就报错
     * 
     * @param date
     * @return
     * @throws ParseException 
     */
    public static Date stringToDate(String date) throws ParseException {

        if (StringUtils.isBlank(date)) {
            return null;
        } 
        
        switch(date.length()){
            case 27:
                return StringToDate(DateUtils.DATE_FORMAT_RFC_822, date, "GMT");
            case 25:
                return StringToDate(DateUtils.DATE_FORMAT_RFC_850, date, "GMT");
            case 23:
                return StringToDate(DateUtils.DATE_FORMAT_ANSIC, date, "GMT");
            default:
                return null;
        }
    }
    
    /**
     * 转换字符串到日期类
     * 
     * @param pattern
     *            日期显示模式
     * @param d
     *            日期类
     * @param timeZone
     *            时区，如果为空或者空字符串，则显示当前时区
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String pattern, String sTime,
            String timeZone) throws ParseException {
        DateFormat dFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);

        if (timeZone != null && !StringUtils.isBlank(timeZone)) {
            Calendar calendar = Calendar.getInstance(new SimpleTimeZone(0,
                    timeZone));
            dFormat.setCalendar(calendar);
        }
        Date date = dFormat.parse(sTime);
        return date;
    }

    /**
     * Formats Date to GMT string.
     */
    public static String formatRfc822Date(Date date) {
        return getRfc822DateFormat().format(date);
    }

    /**
     * Parses a GMT-format string.
     */
    public static Date parseRfc822Date(String dateString) throws ParseException {
        return getRfc822DateFormat().parse(dateString);
    }


    public static String formatIso8601Date(Date date) {
        return getIso8601DateFormat().format(date);
    }

    public static String formatAlternativeIso8601Date(Date date) {
        return getAlternativeIso8601DateFormat().format(date);
    }
    
    private static DateFormat getIso8601DateFormat() {
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.US);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df;
    }
    
	/**
	 * 提取当前时间,并进行格式化显示
	 * @param dateFormat 日期格式，参考日期格式常量
	 * @param 日期字符串
	 */
	public static String getDateWithFormat(int dateFormat)
	{
		String stringDate = "";
		
		if (dateFormat==0)
			dateFormat = DateUtils.SHORT_DATEFORMAT;

		Calendar date = Calendar.getInstance();
		int year	= date.get(Calendar.YEAR);
		int month	= date.get(Calendar.MONTH)+1;
		int day		= date.get(Calendar.DATE);
		int hours	= date.get(Calendar.HOUR_OF_DAY);
		int minute	= date.get(Calendar.MINUTE);
		int seconds	= date.get(Calendar.SECOND);
				
		if (dateFormat==DateUtils.SHORT_DATEFORMAT)
			stringDate = year + "-" + month + "-" + day;
		if (dateFormat==DateUtils.SHORT_DATEFORMAT_FULL){
			String monthStr="",dayStr="";
			if(month<10)
				monthStr="0";
			if(day<10)
				dayStr="0";
			stringDate = year + "-" + monthStr + month + "-" + dayStr + day;							
		}
		if (dateFormat==DateUtils.LONG_DATEFORMAT)
			stringDate = year+"-"+month+"-"+day+" "+hours+":"+minute+":"+seconds;
		if (dateFormat==DateUtils.SHORT_DATEFORMAT_CN)
			stringDate = year + "年" + month + "月" + day + "日";
		if (dateFormat==DateUtils.LONG_DATEFORMAT_CN)
			stringDate = year+"年"+month+"月"+day+"日"+hours+":"+minute+":"+seconds;
		
		return stringDate;
	}

	
	/**
	 * 对指定日期进行格式化输出
	 * @param date 原指定日期
	 * @param dateFormat 日期格式
	 * @return 日期字符串
	 */
	public static String getDateWithFormat(Date date,int dateFormat)
	{
		String stringDate = "";
		if (date == null)
			return stringDate;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		if (dateFormat==0)
			dateFormat = DateUtils.SHORT_DATEFORMAT;

		int year	= calendar.get(Calendar.YEAR);
		int month	= calendar.get(Calendar.MONTH)+1;
		int day		= calendar.get(Calendar.DATE);
		int hours	= calendar.get(Calendar.HOUR_OF_DAY);
		int minutes	= calendar.get(Calendar.MINUTE);
		int seconds	= calendar.get(Calendar.SECOND);
		
		if (dateFormat==DateUtils.SHORT_DATEFORMAT)
			stringDate = year + "-" + month + "-" + day;
		if (dateFormat==DateUtils.LONG_DATEFORMAT)
			stringDate = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
		if (dateFormat==DateUtils.SHORT_DATEFORMAT_CN)
			stringDate = year + "年" + month + "月" + day + "日";
		if (dateFormat==DateUtils.LONG_DATEFORMAT_CN)
			stringDate = year+"年"+month+"月"+day+"日"+hours+":"+minutes+":"+seconds;
			
		return stringDate;
	}
	
	/**
	 * 字符串转换成时间
	 * @param dateString 日期字符串
	 * @return Date 日期对象
	 */
	public static Date convertStringToDate(String dateString)
	{
		Date date = new Date();
		if(StringUtils.isEmpty(dateString))
		{
			Calendar calendar = Calendar.getInstance();
			date = calendar.getTime();
			return date;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		if(dateString.indexOf(":")!=-1)
			dateFormat.applyPattern("yyyy-MM-dd hh:mm:ss");
		else
			dateFormat.applyPattern("yyyy-MM-dd");
			
		try
		{
			//com.yysoft.util.Debug.println("dateString:" + dateString);
			date = dateFormat.parse(dateString);
			//com.yysoft.util.Debug.println("date:" + date);
		}
		catch(Exception ex)
		{
		    //FIXME 
//			DebugUtil.println("DateOperator.turnStringToDate():" + ex.toString());
		}
		
		return date;
	}
	/**
	 * 字符串转换成时间
	 * @param dateString 日期字符串
	 * @return Date 日期对象
	 */
	public static Date convertStringToDate(String dateString,String formatStr)
	{
		Date date = new Date();
		if(StringUtils.isEmpty(dateString))
		{
			Calendar calendar = Calendar.getInstance();
			date = calendar.getTime();
			return date;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat();

			dateFormat.applyPattern(formatStr);
			
		try
		{
			//com.yysoft.util.Debug.println("dateString:" + dateString);
			date = dateFormat.parse(dateString);
			//com.yysoft.util.Debug.println("date:" + date);
		}
		catch(Exception ex)
		{
		    //FIXME 
//			DebugUtil.println("DateOperator.turnStringToDate():" + ex.toString());
		}
		
		return date;
	}
	
//	/**
//	 * 把指定日期转换成sql.Date格式
//	 * @param date 要转换的日期对象
//	 * @return sql 日期对象
//	 */
//	public static java.sql.Date convertDateToSqlDate(Date date)
//	{
//		if(date==null)
//			return null;
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//
//		int year	= calendar.get(Calendar.YEAR);
//		int month	= calendar.get(Calendar.MONTH)+1;
//		int day		= calendar.get(Calendar.DATE);
//		
//		return new java.sql.Date(year,month,day);
//	}

	/**
	 * 取得当前时间的星期数
	 * @return 周数值。星期数:星期日=7;星期一=1;星期二=2;星期三=3;星期四=4;星期五=5;星期六=6;
	 */
	public static int getWeek()
	{
		Calendar calendar = Calendar.getInstance();
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		
		week = week -1;
		if(week==0)
			week = 7;

		return week;
	}

	/**
	 * 提取当前年
	 * @return 当前年
	 */
	public static int getCurrentYear()
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year;		
	}
	
    /**
     * 取当前时间毫秒数
     */
    public static String getMilliTime()
    {
    	Calendar date = Calendar.getInstance();
    	long milliTime = date.getTime().getTime();
    	return new Long(milliTime).toString();
    }
    
    public static int getYear()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);      
        
    }
    
	/**
	 * 提取当前月份
	 * @return 月份
	 */
	public static int getCurrentMonth()
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.MONTH) + 1;
		if(year<10)
		{
			String strYear = "0" + year;
			year = new Integer(strYear).intValue();
		}
		return year;		
	}
    
	public static String getMonth()
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.MONTH) + 1;
		if(year<10)
		{
			String strYear = "0" + year;
			return strYear;
		}
		else
		{
			return Integer.toString(year);
		}		
	}
	
	/**
	 * 提取当前日期
	 * @return 当前日期
	 */
	public static int getCurrentDay()
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		if(day<10)
		{
			String strDay = "0" + day;
			day = new Integer(strDay).intValue();
		}
		return day;		
	}
    
	public static String getDay()
	{
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DATE);
		if(day<10)
		{
			String strDay = "0" + day;
			return strDay;
		}
		else
		{
			return Integer.toString(day);
		}		
	}
	
	/**
	 * 取得当前时间
	 * @return 当前时间，如:23点,0点,1点等
	 */
	public static int getCurrentHour()
	{
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		return hour;
	}

	/**
	 * 比较两面个日期对象,年份是否相等,如果相等则返回0,
	 * 如果date1年份大于date2年份,返回1,否则返回-1
	 * @parma date1 日期对象
	 * @param date2 日期对象
	 * @return 0：两个年相等；1：date1年份大于date2年份；-1：date1年份小于date2年份
	 */
	public static int compareYear(Date date1,Date date2)
	{
		int compare = 0;

		/*创建日历对象*/
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		/*提取年份*/
		int year1 = calendar.get(Calendar.YEAR);
		calendar.setTime(date2);
		/*提取年份*/
		int year2 = calendar.get(Calendar.YEAR);

		/*进行比较*/
		if(year1==year2)		//相等
			compare = 0;
		else if(year1>year2)	//date1比date2前
			compare = 1;
		else if(year1<year2)	//date1比date2后
			compare = -1;
		else					//比较出错
			compare = -2;

		return compare;
	}

	/**
	 * 比较两面个日期对象,月份是否相等,如果相等则返回0,
	 * 如果date1月份大于date2月份,返回1,否则返回-1
	 * @parma date1 日期对象
	 * @param date2 日期对象
	 * @return 0：两个月相等；1：date1月份大于date2年份；-1：date1月份小于date2月份
	 */
	public static int compareMonth(Date date1,Date date2)
	{
		int compare = 0;
		/*先比较年份,如果年份相等则比较月份*/
		int year = compareYear(date1,date2);
		if(year==0)
		{
			/*创建日历*/
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			/*提取月份*/
			int month1 = calendar.get(Calendar.MONTH);
			calendar.setTime(date2);
			/*提取月份*/
			int month2 = calendar.get(Calendar.MONTH);

			/*进行比较*/
			if(month1==month2)		//相等
				compare = 0;
			else if(month1>month2)	//date1比date2先
				compare = 1;
			else if(month1<month2)	//date1比date2后
				compare = -1;
			else
				compare = -2;		//比较出错
		}
		else					
		{
			return year;
		}

		return compare;
	}

	/**
	 * 比较两面个日期对象,日期是否相等,如果相等则返回0,
	 * 如果date1日期大于date2日期,返回1,否则返回-1
	 * @parma date1 日期对象
	 * @param date2 日期对象
	 * @return 0：两个日期相等；1：date1日期大于date2日期；-1：date1日期小于date2日期
	 */
	public static int compareDay(Date date1,Date date2)
	{
		int compare = 0;
		/*先比较月分是否相等*/
		int month = compareMonth(date1,date2);
		if(month==0)
		{
			/*创建日历*/
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			/*提取日期*/
			int day1 = calendar.get(Calendar.DATE);
			calendar.setTime(date2);
			/*提取日期*/
			int day2 = calendar.get(Calendar.DATE);

			/*进行比较*/
			if(day1==day2)
				compare = 0;
			else if(day1>day2)
				compare = 1;
			else if(day1<day2)
				compare = -1;
			else
				compare = -2;
		}
		else
		{
			return month;
		}

		return compare;
	}

	/**
	 * 比较两面个日期对象,时间是否相等,如果相等则返回0,
	 * 如果date1时间大于date2时间,返回1,否则返回-1
	 * @parma date1 日期对象
	 * @param date2 日期对象
	 * @return 0：两个时间相等；1：date1时间大于date2时间；-1：date1时间小于date2时间
	 */
	public static int compareHour(Date date1,Date date2)
	{
		int hour = 0;

		/*先比较日期是否相同*/
		int day = compareDay(date1,date2);
		if(day==0)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date1);
			int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
			calendar.setTime(date2);
			int hour2 = calendar.get(Calendar.HOUR_OF_DAY);
						
			if(hour1==hour2)
				hour=0;
			else if(hour1>hour2)
				hour=1;
			else if(hour1<hour2)
				hour=-1;
			else 
				hour=-2;
		}
		else
		{
			hour = -2;
		}

		return hour;
	}
	
	/**
	 * 取得两个日期对象的天差数
	 * @param date1 日期对象
	 * @param date2 日期对象
	 * @return int 天差数
	 */
	public static int getDateDispersion(Date date1,Date date2)
	{
		/*如果其中一为空,则返回*/
		if(date1==null || date2==null)
			return 0;
		
		/*提取毫秒*/
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		
		long dispersion = time1 - time2;
		
		/*转化成天数*/
		Long longValue = new Long(dispersion/(60*1000*60*24));
		/*返回整数值*/
		return longValue.intValue();
	}
	
	/**
	 * 取得两个日期对象相差的分钟数
	 * @param date1 日期对象
	 * @param date2 日期对象
	 * @return int 相差分钟数
	 */	
	public static int getMinuteDispersion(Date date1,Date date2)
	{
		/*如果其中一为空,则返回*/
		if(date1==null || date2==null)
			return 0;
		
		/*提取毫秒*/
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		
		long dispersion = time1 - time2;
		
		/*转化成分钟*/
		Long longValue = new Long(dispersion/(60*1000));
		/*返回整数值*/
		return longValue.intValue();
	}
	
	/**
	 * 提取当前时间的前一天或数天的年、月、日，并以数组形式还回。
	 * 数组0为年；1为月；2为日.
	 * @param year 当前年
	 * @param month 当前月 
	 * @param day 当前日期
	 * @param days 相差天数
	 * @return 年、月、日数组
	 */
	public static int[] getPreviewDay(int year,int month,int day,int days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month-1,day);
		
		long longDate = (calendar.getTime()).getTime() - (60*1000*60*24*days);
		Date date = new Date(longDate);
		calendar.setTime(date);
		
		int[] rtn = new int[3];
		rtn[0] = calendar.get(Calendar.YEAR);
		rtn[1] = calendar.get(Calendar.MONTH)+1;
		rtn[2] = calendar.get(Calendar.DATE);
		
		return rtn;
	}

	/**
	 * 提取当前时间的后一天或数天的年、月、日，并以数组形式还回。
	 * 数组0为年；1为月；2为日.
	 * @param year 当前年
	 * @param month 当前月 
	 * @param day 当前日期
	 * @param days 相差天数
	 * @return 年、月、日数组
	 */
	public static int[] getNextDay(int year,int month,int day,int days)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month-1,day);
		
		long longDate = (calendar.getTime()).getTime() + (60*1000*60*24*days);
		Date date = new Date(longDate);
		calendar.setTime(date);
		
		int[] rtn = new int[3];
		rtn[0] = calendar.get(Calendar.YEAR);
		rtn[1] = calendar.get(Calendar.MONTH)+1;
		rtn[2] = calendar.get(Calendar.DATE);
		
		return rtn;
	}
	
	/**
	 * 获取今天（某一天）是星期几/周几
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 返回星期的中文称谓，如星期日、是星期一、是星期二、是星期三、是星期四、是星期五、是星期六
	 */
	public static String getCNDayOfTheWeek(int year,int month,int day)
	{
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  

		Calendar calendar = Calendar.getInstance();
//		calendar.set(year,month-1,day);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE,day);
		
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
        if(week_index<0){  
            week_index = 0;  
        }
        return weeks[week_index];
	}
	
	/**
	 * 获取今天（某一天）是星期几/周几
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 返回星期的英文简称
	 */
	public static String getENDayOfTheWeek(int year,int month,int day)
	{
		Calendar calendar = Calendar.getInstance();
//		calendar.set(year,month-1,day);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE,day);
		
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
        if(week_index<0){  
            week_index = 0;  
        }
        WeekDay weekday = WeekDay.fromValue(calendar.get(Calendar.DAY_OF_WEEK));
        return weekday.getShortName();
	}
	
	/**
	 * 获取今天（某一天）是星期几/周几
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
	 */
	public static int getDayOfTheWeek(int year,int month,int day)
	{
		Calendar calendar = Calendar.getInstance();
//		calendar.set(year,month-1,day);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE,day);
		
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
    
	/**
	 * 获取今天（某一天）是星期几/周几
	 * @param time 指定的一个日期时间
	 * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
	 */
	public static int getDayOfTheWeek(Date time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		return calendar.get(Calendar.DAY_OF_WEEK); 
	}
	
	/**
	 * 获取今天（某一天）是星期几/周几
	 * @param time 指定的一个日期时间
	 * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
	 */
	public static int getDayOfTheWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		return calendar.get(Calendar.DAY_OF_WEEK); 
	}
	
	/**
	 * 获得某一天是这一年中的第几个星期，如果指定的这天对应的周跨年，则该周为新年的第一个周
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 指定日期是一年中的第几个星期
	 */
	public static int getWeekOfTheYear(int year,int month,int day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month-1,day);			
		return calendar.get(Calendar.WEEK_OF_YEAR);	
	}
	
	/**
	 * 获得某一天是这一年中的第几个星期，如果指定的这天对应的周跨年，则该周为新年的第一个周
	 * @param time 指定的一个日期时间
	 * @return 指定日期是一年中的第几个星期
	 */
	public static int getWeekOfTheYear(Date time)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);			
		return calendar.get(Calendar.WEEK_OF_YEAR);		
	}
	
	/**
	 * 获得今天是这一年中的第几个星期，如果指定的这天对应的周跨年，则该周为新年的第一个周
	 * @param time 指定的一个日期时间
	 * @return 指定日期是一年中的第几个星期
	 */
	public static int getWeekOfTheYear()
	{
		Calendar calendar = Calendar.getInstance();		
		return calendar.get(Calendar.WEEK_OF_YEAR);		
	}
	
	/**
	 * 获得某年的某个月有多少天
	 * @param year 年
	 * @param month 月
	 * @return 已知年份和月份，取得该月有多少天
	 */
	public static int getDaysInMonth(int year, int month){
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.YEAR, year);  
        calendar.set(Calendar.MONTH, month-1);  
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
	}
	
	/**
	 * 获得两个时间之间的间隔时长
	 * @param start 早的那一个时间
	 * @param end 晚的那一个时间
	 * @return 两时间之间的间隔时长
	 */
    public static String getInterval(Date start, Date end) {
        Long intervalTime = end.getTime() - start.getTime();
        return getInterval(intervalTime);
    }

    /**
     * 将两个时间的时间间隔转化为字符表达
     * @param intervalTime 两个时间的间隔时间
     * @return 间隔时长
     */
    public static String getInterval(long intervalTime) {
        String result = "";
        long day = intervalTime / (24 * 60 * 60 * 1000);
        long hour = (intervalTime / (60 * 60 * 1000) - day * 24);
        long min = ((intervalTime / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long second = (intervalTime / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long millisecond = intervalTime - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - second
                * 1000;
        if (day > 0) {
            result += day + "天";
        }
        if (hour > 0) {
            result += hour + "小时";
        }
        if (min > 0) {
            result += min + "分";
        }
        if (second > 0) {
            result += second + "秒";
        }
        if (millisecond > 0) {
            result += millisecond + "毫秒";
        }
        return result;
    }
}
