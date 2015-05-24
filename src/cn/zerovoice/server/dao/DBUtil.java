package cn.zerovoice.server.dao;
import java.sql.*;

public class DBUtil {
	private static DBUtil dbutil;
	private DBUtil(){
		
	}
	public synchronized static DBUtil getDBUtil(){
		if(dbutil==null){
			dbutil=new DBUtil();
		}
		
		return dbutil;
	}
	
	public Connection getConnection(){
		try {
			//加载SQLServerDriver类
//			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//连接SQLServer数据库
//			Connection conn=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1\\SQLEXPRESS:1433;databaseName=yq",
//					"sa","123");
			//加载MySQL Driver类
			Class.forName("com.mysql.jdbc.Driver");
			//连接MySQL数据库,DatabaseName is 
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ActionDB",
					"zerovoice","zero00");
//			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ActionDB?user=zerovoice&password=zero00&useUnicode=true&characterEncoding=utf-8");
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
