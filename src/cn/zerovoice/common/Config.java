package cn.zerovoice.common;

import java.io.File;

public class Config {
	public static final String ACTIONSERVER_HOME = "D:\\Develop\\apache-tomcat-6.0.35\\webapps\\ActionServer\\";
//	public static final String ACTIONSERVER_HOME = "192.168.1.222"+":8080/ActionServer/";

	
	
	 /**
     * <p>Title:mkdir</p>
     * <p>Description: 创建path指定目录.</p>
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
}
