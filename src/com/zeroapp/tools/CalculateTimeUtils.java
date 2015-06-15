package com.zeroapp.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalculateTimeUtils {
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
}
