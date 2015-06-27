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
import com.zeroapp.parkingserver.common.Bidding;
import com.zeroapp.parkingserver.common.Business;
import com.zeroapp.tools.BmapPoint;

/**
 * <p>
 * Title: BusinessDao.
 * </p>
 * <p>
 * Description: Business表格访问接口.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-6-8.
 * @version $Id$
 */

public class BusinessDao {

	/**
	 * <p>
	 * Title: getAvailableBusiness.
	 * </p>
	 * <p>
	 * Description: 获得当前时间段内可用的Business列表.
	 * </p>
	 * 
	 * @param time
	 * @return
	 */
	public List<Bidding> getAvailableBusinessForCarOwner(int areaid, int userId) {
		List<Bidding> resList = new ArrayList<Bidding>();
		try {
			String sql = "select * from business where areaid=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, areaid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {// TODO 数据类型需要修改
				Bidding b = new Bidding();
				b.setBusinessID(rs.getInt("businessid"));
				b.setUserID(userId);
				resList.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resList;
	}

	public int createBusiness(String area, int maxuser, int maxtenderer,
			String cost, String earnings, String timestart, String timeend,
			int areaid) {
		String sql = "insert into business(MaxUserCount,MaxTendererCount,Cost,Earnings,TimeStart,TimeEnd,areaid) values(?,?,?,?,?,?,?)";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);

			ps.setInt(1, maxuser);
			ps.setInt(2, maxtenderer);
			ps.setString(3, cost);
			ps.setString(4, earnings);
			ps.setString(5, timestart);
			ps.setString(6, timeend);
			ps.setInt(7, areaid);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_FAILURE_INT;
		}
	}

	public int removeBusiness(int businessid) {
		String sql = "delete from parking.business where businessid=?";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, businessid);
			int rs = ps.executeUpdate();
			if (rs > 0) {
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_FAILURE_INT;
		}
	}

	public Business getBusinessDetails(int businessid) {

		String sql = "selecet * from  business where businessid=?";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, businessid);
			ResultSet res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					Business b = new Business();
					b.setAreaId(res.getInt("areaid"));
					b.setBusinessID(businessid);
					b.setCost(res.getDouble("cost"));
					b.setEarnings(res.getDouble("earnings"));
					b.setMaxtendererCount(res.getInt("maxtendererCount"));
					b.setMaxUserCount(res.getInt("MaxUserCount"));
					b.setTimeEnd(res.getString("timeend"));
					b.setTimeStart(res.getString("timestart"));
					return b;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public int getBusinessAreaId(int businessid) {
		String sql = "select areaid from parking.business where businessid=?";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, businessid);
			ResultSet res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					return res.getInt("areaid");
				}
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}

	}

	public int updateBusinessCostItem(double userProfit, int businessId) {
		Connection conn = DBUtil.getDBUtil().getConnection();
		String sql;
		try {
			if (userProfit == -1) {
				sql = "update parking.business set cost=0 where businessid=?";
			}
				sql = "update parking.business set cost=cost-" + userProfit
					+ "where businessid=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, businessId);
			int res = ps.executeUpdate();
			if (res > 0) {
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}
	}
}
