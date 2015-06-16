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

import com.zeroapp.parkingserver.common.Bidding;
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
    public List<Bidding> getAvailableBusinessForCarOwner(String areanname,int cityid,long time,int userID) {
        List<Bidding> resList = new ArrayList<Bidding>();
        try {
            String sql = "select * from business where area=?";
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,areanname);
            ResultSet rs = ps.executeQuery();
            if(rs.getString("area").equals(areanname)){
            while (rs.next()) {// TODO 数据类型需要修改
            	Bidding b = new Bidding();
            	b.setBusinessID(rs.getInt("businessid"));
            	b.setUserID(userID);
            	resList.add(b);
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resList;
    }
    
    public int createBusiness(String area,int maxuser,int maxtenderer,String cost,String earnings,String timestart,String timeend,int areaid ){
    	String sql = "insert into business(Area,MaxUserCount,MaxTendererCount,Cost,Earnings,TimeStart,TimeEnd,areaid) values(?,?,?,?,?,?,?,?)";
    	try {
    	Connection conn = DBUtil.getDBUtil().getConnection();
    	PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, area);
			ps.setInt(2,maxuser);
			ps.setInt(3, maxtenderer);
			ps.setString(4, cost);
			ps.setString(5, earnings);
			ps.setString(6, timestart);
			ps.setString(7, timeend);
			ps.setInt(8, areaid);
			int rs = ps.executeUpdate();
				return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    }
    public int removeBusiness(int businessid){
    	String sql = "delete from parking.business where businessid=?";
    	try {
    	Connection conn= DBUtil.getDBUtil().getConnection();
    	PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, businessid);
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
    	
    	
    }
}
