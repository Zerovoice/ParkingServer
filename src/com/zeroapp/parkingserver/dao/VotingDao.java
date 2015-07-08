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

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.Voting;


/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Alex(zeroapp@126.com) 2015-6-8.
 * @version $Id$
 */

public class VotingDao {
	private Connection conn;
	public VotingDao(Connection connection){
		this.conn = connection;
	}
	public Voting getVoting(String carN){
		String sql = "select * from parking.voting where CarNum=?";
		Voting voting = new Voting();
		try {
//		Connection conn =  DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, carN);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
					voting.setBiddingID(res.getInt("biddingid"));
					voting.setCarNum(carN);
					voting.setState(res.getString("state"));
					voting.setVotingID(res.getInt("votingID"));
				}
				return voting;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public Voting getVoting(int biddingid){

		String sql = "select * from parking.voting where biddingid=?";
		Voting voting = new Voting();
		try {
//		Connection conn =  DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, biddingid);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
					voting.setBiddingID(biddingid);
					voting.setCarNum(res.getString("carnum"));
					voting.setState(res.getString("state"));
					voting.setVotingID(res.getInt("votingID"));
				}
				return voting;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	
	}
	public int createVoting(Voting v){
		String sql = "insert into parking.voting values(null,?,?,?)";
		try {
//		Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, v.getBiddingID());
			ps.setString(2, v.getCarNum());
			ps.setString(3, v.getState());
			int res = ps.executeUpdate();
			if(res > 0){
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}
		
	}
}
