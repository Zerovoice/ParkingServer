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
    public List<String> getCars(String account) {
        List<String> res = new ArrayList<>();
        try {
            String sql = "select * from car_info where UserID=?";
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(rs.getString("CarNum"));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public boolean addCar(CarInfo car) {
        try {
            String sql = "insert into car_info values(?,?,?,?,?,?,?,?,?,?,?)";// TODO
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
//          ps.setInt(1, u.getAccount());
//          ps.setString(2, u.getPassword());
//          ps.setString(3, u.getNick());
//          ps.setString(4, u.getAvatar());
//          ps.setString(5, u.getTrends());
//          ps.setString(6, u.getSex());
//          ps.setInt(7, u.getAge());
//          ps.setInt(8, u.getLev());
//          ps.setInt(9, 0);  //�����û��Ƿ�����
//          ps.setString(10, u.getTime());
//          ps.setInt(11, u.getTag());
            int r = ps.executeUpdate();
            if (r > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeCarState(String carNum, String newState) {
        try {
            String sql = "update car_info set newState=? where CarNum=?";
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newState);
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

    public boolean vote(String carNum, String BiddingID) {
        try {
            String sql = "update car_info set BiddingID=? where CarNum=?";
            Connection conn = DBUtil.getDBUtil().getConnection();
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
