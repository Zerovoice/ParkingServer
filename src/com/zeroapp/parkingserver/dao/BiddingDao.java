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
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.Bidding;
import com.zeroapp.tools.CalculateTimeUtils;

/**
 * <p>
 * Title: BiddingDao.
 * </p>
 * <p>
 * Description: Bidding表格访问接口.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-6-8.
 * @version $Id$
 */

public class BiddingDao {

	/**
	 * <p>
	 * Title: getAvailableBusiness.
	 * </p>
	 * <p>
	 * Description: 获得当前时间段内可用的Bidding列表.
	 * </p>
	 * 
	 * @param time
	 * @return
	 */
	public List<Integer> getAvailableBusiness(long time, String city) {
		List<Integer> res = new ArrayList<Integer>();
		try {
			String sql = "select * from bidding";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {// TODO 数据类型需要修改
				if (time >= rs.getLong("TimeStart")
						&& time <= rs.getLong("TimeEnd")) {
					res.add(rs.getInt("BiddingID"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean createBid(Bidding b) {
		try {
			String sql = "insert into bidding(businessid,userid,timestart,timeend) values(?,?,?,?)";// TODO
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, b.getBusinessID());
			ps.setInt(2, b.getUserID());
			ps.setString(3, b.getTimeStart());
			ps.setString(4, b.getTimeEnd());
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public double getBiddingProfit(String businessTimeStart,
			String businessTimeEnd, String userTimeStart, String userTimeEnd,
			double unitEarning) {

		if (CalculateTimeUtils.isEndTimeBiggerThanStartTime(userTimeEnd,
				businessTimeStart)
				&& CalculateTimeUtils.isEndTimeBiggerThanStartTime(
						userTimeStart, userTimeStart)) {
			return MessageConst.MessageResult.MSG_RESULT_FAIL;

		} else {
			return (unitEarning)
					* (CalculateTimeUtils.getTimeDiff(userTimeStart,
							userTimeEnd));

		}
	}

	public ArrayList<Bidding> getUserBiddings(int userId) {
		ArrayList<Bidding> biddingArrayList = new ArrayList<Bidding>();
		String sql = "select * from parking.bidding where userid=?";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					Bidding bidding = new Bidding();
					bidding.setBiddingID(res.getInt("biddingid"));
					bidding.setUserID(userId);
					bidding.setBusinessID(res.getInt("businessid"));
					bidding.setTimeStart(res.getString("timestart"));
					bidding.setTimeEnd(res.getString("timeend"));
					biddingArrayList.add(bidding);
				}
				return biddingArrayList;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	public ArrayList<Integer> getUserBusinessIdList(int userId){
		ArrayList<Integer> businessIdArrayList = new ArrayList<Integer>();
		ArrayList<Bidding> biddingArrayList = getUserBiddings(userId);
		for(Bidding b:biddingArrayList){
			 businessIdArrayList.add(b.getBusinessID());
		}
		return businessIdArrayList;
	}
	public int getBusinessIdFromBidding(int biddingid) {
		String sql = "select businessid from parking.bidding where biddingid=?";
		try {
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, biddingid);
			ResultSet res = ps.executeQuery();
			if (res != null) {
				while (res.next()) {
					return	res.getInt("businessid");
				}
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}

	}
}
