package com.zeroapp.parkingserver.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static DBUtil dbutil = null;
    public static final String DB_ADRESS = "localhost";
    public static final String DB_PORT = "3306";
    public static final String DB_NAME = "parking";
    public static String DB_ADMIN_NAME = "root";
    public static String DB_ADMIN_PWD = "root";
	private DBUtil(){
	}
	public synchronized static DBUtil getDBUtil(){
		if(dbutil==null){
			dbutil=new DBUtil();
		}
		return dbutil;
	}

    public synchronized static DBUtil getDBUtil(String name, String pwd) {
        if (dbutil == null) {
            dbutil = new DBUtil();
        }
        DB_ADMIN_NAME = name;
        DB_ADMIN_PWD = pwd;
        return dbutil;
    }
	
	public Connection getConnection(){
		try {
            // 加载SQLServerDriver类
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // 连接SQLServer数据库
            // Connection
            // conn=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1\\SQLEXPRESS:1433;databaseName=yq","sa","123");
            // 加载MySQL Driver类
            Class.forName("com.mysql.jdbc.Driver");
            // 连接MySQL数据库,DatabaseName
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + DB_ADRESS + ":" + DB_PORT + "/" + DB_NAME, DB_ADMIN_NAME, DB_ADMIN_PWD);
            // Connection
            // conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ActionDB?user=zerovoice&password=zero00&useUnicode=true&characterEncoding=utf-8");
            return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public void closeConnection(Connection con){
		if(con!=null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
