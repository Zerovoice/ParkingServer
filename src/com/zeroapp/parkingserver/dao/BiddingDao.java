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
    public List<Integer> getAvailableBusiness(long time) {
        List<Integer> res = new ArrayList<Integer>();
        try {
            String sql = "select * from bidding";
            Connection conn = DBUtil.getDBUtil().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {// TODO 数据类型需要修改
                if (time >= rs.getLong("TimeStart") && time <= rs.getLong("TimeEnd")) {
                    res.add(rs.getInt("BiddingID"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
