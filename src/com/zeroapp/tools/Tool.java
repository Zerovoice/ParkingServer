package com.zeroapp.tools;

import java.io.File;
import java.math.BigDecimal;

import com.zeroapp.parking.message.MessageConst;

public class Tool {
	public static final String ACTIONSERVER_HOME = "D:\\Develop\\apache-tomcat-6.0.35\\webapps\\ActionServer\\";
//	public static final String ACTIONSERVER_HOME = "192.168.1.222"+":8080/ActionServer/";

	
	
	 /**
     * <p>Title:mkdir</p>
     * <p>Description: ����pathָ��Ŀ¼.</p>
     *
     * @param path
     */

    public static boolean mkFilePath(String path) {
        File fPath = new File(path);

        if (fPath.exists()) {
            return true;
        }else{
            if (fPath.mkdirs()) {
                	 System.out.println("create directory [" + fPath.getAbsolutePath()
                             + "] success!!!");
                return true;
            } else {
            	System.out.println( "create directory [" + fPath.getAbsolutePath()
                        + "] failed!!!");
                return false;
            } 
        }
    }
	public static double getBiddingProfit(long businessTimeStart,
			long businessTimeEnd, long userTimeStart, long userTimeEnd,
			double unitEarning,int timeUnit) {
			double profit;
			if ((userTimeStart < businessTimeStart)&&(userTimeEnd < businessTimeEnd)) {
			profit = (unitEarning)
			* (CalculateTimeUtils.getTimeDiff(businessTimeStart,
					userTimeEnd));
		}else if ((userTimeStart > businessTimeStart)&&(userTimeEnd > businessTimeEnd)) {
			profit = (unitEarning)
			* (CalculateTimeUtils.getTimeDiff(userTimeStart,
					businessTimeEnd));
		}else {
			profit = (unitEarning)
			* (CalculateTimeUtils.getTimeDiff(userTimeStart,
					userTimeEnd));
		}
		return convertToFinalProfit(profit/timeUnit);
	}
	public static double convertToFinalProfit(double p){
		BigDecimal bg = new BigDecimal(p);  
		double finalProfit = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return finalProfit;
	}
	public static boolean isParkingTimeAvailable(long businessTimeStart,
			long businessTimeEnd, long userTimeStart, long userTimeEnd){
		return ((userTimeStart > businessTimeEnd)||(userTimeEnd < businessTimeStart))? false:true;
	}
	public static int[] getIntRangeFromString(String s){
		try {
			int[] intR = new int[s.split(",").length];
			int rangeStart = Integer.parseInt(s.split(",")[0]);
			int rangeEnd = Integer.parseInt(s.split(",")[1]);
			intR[0]=rangeStart;
			intR[1]=rangeEnd;
			return intR;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
