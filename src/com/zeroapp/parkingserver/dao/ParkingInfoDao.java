package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.zeroapp.parking.message.MessageConst;

public class ParkingInfoDao {
	public int creatParkingInfo(String carNum, String longitude,
			String latitude, String timeStart, String timeEnd, int businessId,
			String moneyEarning, String moneyCost) {
		String sql = "insert into parking.parking_info values(null,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carNum);
			ps.setString(2, longitude);
			ps.setString(3, latitude);
			ps.setString(4, timeStart);
			ps.setString(5, timeEnd);
			ps.setInt(6, businessId);
			ps.setString(7, moneyEarning);
			ps.setString(8, moneyCost);
			int res = ps.executeUpdate();
			if(res>0){
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}else {
				return MessageConst.MessageResult.MSG_RESULT_FAIL;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_FAILURE_INT;
		}
	}
}
