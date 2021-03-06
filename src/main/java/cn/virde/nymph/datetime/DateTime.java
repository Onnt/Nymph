package cn.virde.nymph.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Blacard
 * 2016年12月30日 下午7:07:28
 */
public class DateTime {
	
	private Date date;

	/**
	 * 设置日期格式化
	 * 默认格式化为：yyyy-MM-dd HH:mm:ss
	 * @author Virde
	 * 2018年2月5日 14:02:48
	 */
	private final static String format = "yyyy-MM-dd HH:mm:ss";
	
	public DateTime(){}
	public DateTime(Date date){
		this.date = date;
	}
	public DateTime(String date) throws ParseException{
		this.date = toDate(date);
	}
	public DateTime setDate(Date date){
		this.date = date;
		return this;
	}
	public DateTime setDate(String date) throws ParseException{
		this.date = toDate(date);
		return this;
	}
	
	public String toString(){
		return toString(date);
	}
	/**
	 * 将给定时间转成字符串返回.
	 * @author Blacard
	 * 2016年12月30日 下午7:31:18
	 * @param date Date类型时间
	 * @return 返回 返回日期默认格式为：yyyy-MM-dd HH:mm:ss
	 */
	public static String toString(Date date){
		return toString(date,format);
	}
	/**
	 *  将给定时间转成字符串返回
	 * @author Blacard
	 * 2016年12月30日 下午7:31:30
	 * @param date Date类型时间
	 * @param format 格式
	 * @return 返回
	 */
	public static String toString(Date date,String format){
		SimpleDateFormat this_sdf = new SimpleDateFormat(format);
		return this_sdf.format(date);
	}
	
	/**
	 * @author Virde
	 * 2018年4月10日 下午5:02:34
	 * @param timestamp 时间戳
	 * @return 返回
	 */
	public static String toString(long timestamp) {
		Date d = toDate(timestamp);
		return toString(d);
	}
	/**
	 * 
	 * @author Virde
	 * 2018年2月5日 下午2:13:30
	 * @param date 日期
	 * @param format yyyy-MM-dd HH:mm:ss
	 * @return 返回
	 */
	public static String format(Date date,String format) {
		return toString(date, format);
	}
	public String format(String format){
		return toString(this.date,format);
	}
	public String format(){
		return format(format);
	}
	
	public Date toDate(){
		return date;
	}
	/**
	 * 将时间转成Date类型
	 * 目前能默认匹配的日期格式有五种
	 * <ul>
	 *   <li>yyyy年MM月dd日 HH:mm:ss</li>
	 *   <li>yyyy年MM月dd日</li>
	 *   <li>yyyy-MM-dd HH:mm:ss</li>
	 *   <li>yyyy-MM-dd</li>
	 *   <li>时间戳</li>
	 * </ul>
	 * @author Blacard
	 * 2016年12月30日 下午7:32:12
	 * @param date 日期
	 * @return 返回
	 * @throws ParseException  异常
	 */
	public static Date toDate(String date) throws ParseException{
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d = null ;
		
		try {
			d = sdf1.parse(date);
		} catch (ParseException e) {
			try {
				d = sdf2.parse(date);
			} catch (ParseException e1) {
				try {
					d = sdf3.parse(date);
				} catch (ParseException e2) {
					try {
						d = sdf4.parse(date);
					} catch (ParseException e3) {
						//尝试用时间戳转换
						try{
							d = toDate(Long.parseLong(date));
						}catch(Exception e4){
							throw new ParseException("NymTime - ERROR:输入的日期格式不被支持，日期："+date, 0);
						}
					}
				}
			}
		}
		return d;
	}
	/**
	 * 
	 * @author Blacard
	 * 2016年12月30日 下午7:32:29
	 * @param timestamp 时间戳
	 * @return 返回
	 */
	public static Date toDate(long timestamp){
		if((timestamp/100000000L)>1000){
			return new Date(timestamp);
		}else{
			return new Date(timestamp * 1000);
		}
	}
	/**
	 * 
	 * @author Blacard
	 * 2016年12月31日 下午7:09:58
	 * @param date 日期
	 * @param format 格式
	 * @return 返回
	 */
	public static Date toDate(String date,SimpleDateFormat format){
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	/**
	 * 将时间转换成format类型的时间
	 * @author Blacard
	 * 2016年12月30日 下午7:32:23
	 * @param date 日期
	 * @param format 格式
	 * @return 返回
	 */
	public static Date toDate(String date,String format){
		SimpleDateFormat this_sdf = new SimpleDateFormat(format);
		return toDate(date,this_sdf);
	}

	
	/**
	 * 
	 * @author Blacard
	 * 2016年12月30日 下午7:33:21
	 * @param date
	 * @return 返回
	 */
	public static long toTimestamp(Date date){
		return date.getTime()/1000;
	}

	public long toTimestamp(){
		return date.getTime()/1000;
	}
	/**
	 * 
	 * @author Blacard
	 * 2016年12月30日 下午8:47:09
	 * @param date
	 * @param UNIT
	 * @param add
	 * @return 返回
	 */
	public static Date addTime(Date date,int UNIT, int add){
		Calendar cal =Calendar.getInstance();
		cal.setTime(date);
		cal.add(UNIT, add);
		return cal.getTime();
	}
	
	public DateTime addTime(int unit, int add){
		this.date = addTime(date,unit,add);
		return this;
	}
	/**
	 * 
	 * @author Virde
	 * 2018年2月5日 下午2:07:24
	 * @param date
	 * @param hour
	 * @param minute
	 * @param second
	 * @return 返回
	 */
	@Deprecated
	public Date setTimeGetDate(Date date,int hour,int minute,int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	public static Date initTime(Date date,int hour,int minute,int second){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	public DateTime initTime(int hour,int minute,int second){
		this.date = initTime(date,hour,minute,second);
		return this;
	}
	public static Date timeToZero(Date date){
		return initTime(date,0,0,0);
	}
	public DateTime timeToZero(){
		date = timeToZero(date);
		return this;
	}
}
