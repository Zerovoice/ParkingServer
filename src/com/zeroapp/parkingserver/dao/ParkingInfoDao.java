package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.ParkingInfo;
import com.zeroapp.tools.BmapPoint;

public class ParkingInfoDao {
	public int creatParkingInfo(String carNum, String longitude,
			String latitude, String timeStart, String timeEnd,
			double moneyEarning, double moneyCost, int userId) {
		String sql = "insert into parking.parking_info values(null,?,?,?,?,?,?,?,?)";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carNum);
			ps.setString(2, longitude);
			ps.setString(3, latitude);
			ps.setString(4, timeStart);
			ps.setString(5, timeEnd);
			ps.setDouble(6, moneyEarning);
			ps.setDouble(7, moneyCost);
			ps.setInt(8, userId);
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
	public ArrayList<ParkingInfo> getParkingInfoDetails(String carNum){
		ArrayList<ParkingInfo> paArrayList =  new ArrayList<ParkingInfo>();
		String sql = "select * from parking.parking_info where carnum=?";
		try {
		Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carNum);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
					ParkingInfo paInfo = new ParkingInfo();
					paInfo.setCarNum(carNum);
					paInfo.setLocationLatitude(res.getString("locationLatitude"));
					paInfo.setLocationLongitude(res.getString("locationLongitude"));
					paInfo.setMoney(res.getString("money"));
					paInfo.setParkingID(res.getInt("parkingID"));
					paInfo.setTimeEnd(res.getString("timeEnd"));
					paInfo.setTimeStart(res.getString("timeStart"));
					paArrayList.add(paInfo);
				}
				return paArrayList;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
