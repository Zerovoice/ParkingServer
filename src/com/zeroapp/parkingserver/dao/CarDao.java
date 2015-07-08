/* 
 * Copyright (C) 2015 Alex. 
 * All Rights Reserved.
 *
 * ALL RIGHTS ARE RESERVED BY Alex. ACCESS TO THIS
 * SOURCE CODE IS STRICTLY RESTRICTED UNDER CONTRACT. THIS CODE IS TO
 * BE KEPT STRICTLY CONFIDENTIAL.
 *
 * UNAUTHORIZED MODIFICATION OF THIS FILE WILL VOID YOUR SUPPORT CONTRACT
 * WITH Alex(zeroapp@126.com). IF SUCH MODIFICATIONS ARE FOR THE PURPOSE
 * OF CIRCUMVENTING LICENSING LIMITATIONS, LEGAL ACTION MAY RESULT.
 */

package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.CarInfo;

/**
 * <p>
 * Title: CarDao.
 * </p>
 * <p>
 * Description: 访问car_info表并操作得到数据.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-6-8.
 * @version $Id$
 */

public class CarDao {
	private Connection conn;
	public CarDao(Connection connection){
		this.conn = connection;
	}
	/**
	 * <p>
	 * Title: getCars.
	 * </p>
	 * <p>
	 * Description: 获得账号下所有车的车牌列表.
	 * </p>
	 * 
	 * @param account
	 * @return
	 */
	public List<CarInfo> getCars(int userID) {
		List<CarInfo> res = new ArrayList<>();
		try {
			String sql = "select * from car_info where UserID=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CarInfo car = new CarInfo();
				car.setCarNum(rs.getString("CarNum"));
				car.setUserID(userID);
				car.setBiddingID(rs.getInt("BiddingID"));
				car.setCarState(rs.getString("State"));
				car.setCarType(rs.getString("cartype"));
				car.setCarValue(rs.getInt("carvalue"));
				res.add(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}

	/**
	 * <p>
	 * Title: addCar.
	 * </p>
	 * <p>
	 * Description: 为用户添加一辆Car信息.
	 * </p>
	 * 
	 * @param u
	 * @return
	 */
	public String addCar(CarInfo car) {
		try {
			String sql = "insert into car_info values(?,?,?,?,?,?,?)";// TODO
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, car.getCarNum());
			ps.setInt(2, car.getUserID());
			ps.setInt(3, car.getBiddingID());
			ps.setString(4, car.getCarState());
			ps.setInt(5, car.getCarValue());
			ps.setString(6,car.getCarType());
			ps.setString(7, car.getParkingArea());
			int rs = ps.executeUpdate();
			if (rs > 0) {
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS_STRING;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL_STRIGN;
		} catch (Exception e) {
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_STRING;
		}
	}

	public int changeCarState(String carNum, String newState) {
		try {
			String sql = "update car_info set newState=? where CarNum=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newState);
			ps.setString(2, carNum);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MessageConst.MessageResult.SQL_OPREATION_FAILURE_INT;
	}

	public int getCarBidding(String carN){
		String sql = "select bidding from parking.car_info where carnum=?";
//		Connection conn = DBUtil.getDBUtil().getConnection();
		int biddingId = -1; 
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carN);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
					biddingId = res.getInt("biddingid");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return biddingId;
	}

	public boolean vote(String carNum, String BiddingID) {
		try {
			String sql = "update car_info set BiddingID=? where CarNum=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, BiddingID);
			ps.setString(2, carNum);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
