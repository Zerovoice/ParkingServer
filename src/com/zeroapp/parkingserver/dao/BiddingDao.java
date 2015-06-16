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

	public String getBiddingProfit(String businessArea, String userArea,
			String businessTimeStart, String businessTimeEnd,
			String userTimeStart, String userTimeEnd, String unitEarning) {
		if ((!businessArea.equals(userArea))
				&& CalculateTimeUtils.isEndTimeBiggerThanStartTime(userTimeEnd,
						businessTimeStart)
				&& CalculateTimeUtils.isEndTimeBiggerThanStartTime(
						userTimeStart, userTimeStart)) {
			return MessageConst.BIDDING_CONSTANSTS.BIDDING_FAIL;

		} else {
			return Long.toString((Double.valueOf(unitEarning).intValue())
					* (CalculateTimeUtils.getTimeDiff(userTimeStart,
							userTimeEnd)));

		}
	}

}
