package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.ParkingInfo;
import com.zeroapp.tools.BmapPoint;
import com.zeroapp.tools.GraphTool;

public class ParkingInforDao {
//	public String isParkingAreaValid(BmapPoint bp,int userId){
//		
//	}
public String createParingInfor(ParkingInfo pf){
	String sql = "insert into parking.parking_infor values(?,?,?,?,?)";
	Connection conn =  DBUtil.getDBUtil().getConnection();
	PreparedStatement ps;
	try {
		ps = conn.prepareStatement(sql);
		ps.setString(1, pf.getCarNum());
		ps.setString(2, pf.getLocationLatitude());
		ps.setString(3, pf.getLocationLongitude());
		ps.setString(4, pf.getTimeStart());
		ps.setString(5, pf.getTimeEnd());
		if(ps.executeUpdate()>0){
			return MessageConst.MessageResult.SQL_QUERY_SUCCESS;
		} else {
			return MessageConst.MessageResult.SQL_QUERY_FAILURE;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return MessageConst.MessageResult.SQL_QUERY_EXCPTION;
	}
}
}
