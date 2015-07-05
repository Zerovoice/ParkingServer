package com.zeroapp.parkingserver.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.zeroapp.parking.message.MessageConst;

public class CityDao {
	private Connection conn;
	public CityDao(Connection connection){
		this.conn = connection;
	}
public String getCityName(int cityid){
	String sql = "select name from parking.city where id=?";
//	Connection conn = DBUtil.getDBUtil().getConnection();
	PreparedStatement ps;
	try {
		ps = conn.prepareStatement(sql);
		ps.setInt(1, cityid);
		ResultSet res = ps.executeQuery();
		while (res.next()) {
			return res.getString("name");
		}
		return null;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return MessageConst.MessageResult.SQL_QUERY_FAILURE;
	}
}
public int getCityId(String city) {

	try {
        String sql = "select id,name,countrycode,district from world.city where name=?";
//		Connection conn = DBUtil.getDBUtil().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, city);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			while(rs.next()){
				System.out.println(rs.getInt("id"));
				return rs.getInt("id");
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
    return 0;
}
}
