package com.zeroapp.tools;

import java.io.File;

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
	public static double getBiddingProfit(String businessTimeStart,
			String businessTimeEnd, String userTimeStart, String userTimeEnd,
			double unitEarning) {

		if (CalculateTimeUtils.isEndTimeBiggerThanStartTime(userTimeEnd,
				businessTimeStart)
				&& CalculateTimeUtils.isEndTimeBiggerThanStartTime(
						userTimeStart, userTimeStart)) {
			return MessageConst.MessageResult.MSG_RESULT_FAIL;

		} else {
			return (unitEarning)
					* (CalculateTimeUtils.getTimeDiff(userTimeStart,
							userTimeEnd));

		}
	}
}
