package com.zeroapp.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class CalculateTimeUtils {
	 // 短日期格式
	 public static String DATE_FORMAT = "yyyy-MM-dd";
	 
	 // 长日期格式
	 public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	 
	public static Calendar convertTime(String sTime) {

		java.util.Calendar c1 = java.util.Calendar.getInstance();

		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try

		{

			c1.setTime(df.parse(sTime));
			return c1;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isEndTimeBiggerThanStartTime(String startTime,
			String endTime) {
		if (convertTime(startTime).compareTo(convertTime(endTime)) == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static long getTimeDiff(String startTime,String endTime){
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateStart = null;
		Date dateEnd = null;
		try {
			dateStart = df.parse(startTime);
			 dateEnd = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}
		return dateEnd.getTime() - dateStart.getTime();
	}
	
	public static long convert2long(String date) {
		  try {
		   if (StringUtils.isNotBlank(date)) {
		    SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
		    return sf.parse(date).getTime();
		   }
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		  return 0l;
		 }
		 
		 /**
		  * 将长整型数字转换为日期格式的字符串
		  * 
		  * @param time
		  * @param format
		  * @return
		  */
		 public static String convert2String(long time) {
		  if (time > 0l) {
		   SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
		   Date date = new Date(time);
		   return sf.format(date);
		  }
		  return "";
		 }
}
