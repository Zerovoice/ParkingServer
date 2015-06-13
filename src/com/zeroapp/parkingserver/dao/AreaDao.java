package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.zeroapp.parkingserver.common.Area;


public class AreaDao {
    public int getCityId(String city) {

		try {
            String sql = "select id,name,countrycode,district from world.city where name=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, city);
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while(rs.next()){
					System.out.println(rs.getInt("id"));
					return rs.getInt("id");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return 0;
	}
    
    public ArrayList<Area> areaDetailsArrayList(int citycode){
    		ArrayList<Area> al = new ArrayList<Area>();
    	try {
            String sql = "select * from parking.areas_details where city=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,citycode );
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while(rs.next()){
					System.out.println(rs.getInt("city"));
					al.add(new Area(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return al;
    }
	
}
