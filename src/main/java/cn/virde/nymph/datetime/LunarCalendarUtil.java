package cn.virde.nymph.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 中国农历工具
 * @author Blacard
 * 2017年2月7日 下午4:04:44
 */
public class LunarCalendarUtil { 
		
	public Date toLunarDate(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String dateStr = LunarCalendarCalculate.sCalendarSolarToLundar(year, month, day);
		Date resultDate = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
		//偏移量
		resultDate = DateTime.addTime(resultDate, Calendar.DAY_OF_MONTH, +1);
		return resultDate;
	}
	
	public Date lunarDateTo(Date moonDate) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(moonDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String chineseDateStr = LunarCalendarCalculate.sCalendarLundarToSolar(year, month, day);
		Date resultDate = new SimpleDateFormat("yyyyMMdd").parse(chineseDateStr);
		//减去偏移量
		resultDate = DateTime.addTime(resultDate, Calendar.DAY_OF_MONTH, -1);
		return resultDate;
	}
}