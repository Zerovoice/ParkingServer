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
import com.zeroapp.tools.CalculateTimeUtils;
import com.zeroapp.tools.Tool;

public class ParkingInfoDao {
	private Connection conn;
	public ParkingInfoDao(Connection connection){
		this.conn = connection;
	}
	public int creatParkingInfo(String carNum, double longitude,
			double latitude, String timeStart, String timeEnd,
			double moneyEarning, double moneyCost, int userId) {
		String sql = "insert into parking.parking_info values(null,?,?,?,?,?,?,?,?)";
		try {
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carNum);
			ps.setDouble(2, longitude);
			ps.setDouble(3, latitude);
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
	public ArrayList<ParkingInfo> getParkingInfoDetails(int userId,String range){
		ArrayList<ParkingInfo> paArrayList =  new ArrayList<ParkingInfo>();
		String sql = "select * from parking.parking_info where userid=? limit ?,?";
		int intRange[];
		if(range!=null){
			intRange = Tool.getIntRangeFromString(range);
		}else{
			intRange = new int[2];
			intRange[0] = 0;
			intRange[1] = 5;
		}
		try {
//		Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, intRange[0]);
			ps.setInt(3, intRange[1]);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
					ParkingInfo paInfo = new ParkingInfo();
					paInfo.setCarNum(res.getString("carnum"));
					paInfo.setLocationLatitude(res.getDouble("locationLatitude"));
					paInfo.setLocationLongitude(res.getDouble("locationLongitude"));
					paInfo.setMoneyCost(res.getDouble("MoneyCost"));
					paInfo.setMoneyEarning(res.getDouble("MoneyEarning"));
					paInfo.setParkingID(res.getInt("parkingID"));
					paInfo.setTimeEnd(CalculateTimeUtils.convert2long(res.getString("timeEnd")));
					paInfo.setTimeStart(CalculateTimeUtils.convert2long(res.getString("timeStart")));
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
	public ArrayList<ParkingInfo> getParkingInfoDetails(String carN){
		ArrayList<ParkingInfo> paArrayList =  new ArrayList<ParkingInfo>();
		String sql = "select * from parking.parking_info where carnum=?";
		int intRange[];
		try {
		PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carN);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
					ParkingInfo paInfo = new ParkingInfo();
					paInfo.setCarNum(res.getString("carnum"));
					paInfo.setLocationLatitude(res.getDouble("locationLatitude"));
					paInfo.setLocationLongitude(res.getDouble("locationLongitude"));
					paInfo.setMoneyCost(res.getDouble("MoneyCost"));
					paInfo.setMoneyEarning(res.getDouble("MoneyEarning"));
					paInfo.setParkingID(res.getInt("parkingID"));
					paInfo.setTimeEnd(CalculateTimeUtils.convert2long(res.getString("timeEnd")));
					paInfo.setTimeStart(CalculateTimeUtils.convert2long(res.getString("timeStart")));
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
