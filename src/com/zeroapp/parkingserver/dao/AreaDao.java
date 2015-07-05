package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.Area;


public class AreaDao {
	private Connection conn;
	public AreaDao(Connection connection){
		this.conn = connection;
	}
    
    public ArrayList<Area> areaDetailsArrayList(int citycode){
    		ArrayList<Area> al = new ArrayList<Area>();
    	try {
            String sql = "select * from parking.areas_details where city=?";
//            if(conn == null){
//			conn = DBUtil.getDBUtil().getConnection();
//            }
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
	public String getAreaName(int areaId){
		String sql = "select area from parking.areas_details where Id_p=?";
        if(conn == null){
//		conn = DBUtil.getDBUtil().getConnection();
        }
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaId);
			ResultSet res = ps.executeQuery();
			if(res !=null){
				while (res.next()) {
					return res.getString("area");
				}
			}
			return MessageConst.MessageResult.SQL_QUERY_FAILURE;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_QUERY_FAILURE;
		}
	}
	public String getAreaCoordinates(int areaId){
			String sql = "select coordinates from parking.areas_details where city=?";
			try {
	            if(conn == null){
//	    			conn = DBUtil.getDBUtil().getConnection();
	                }
			PreparedStatement ps;
			ps = conn.prepareCall(sql);
				ps.setInt(1, areaId);
				ResultSet res = ps.executeQuery();
				if(res!=null){
					while(res.next()){
					return res.getString("coordinates");
					}
				}
					return MessageConst.MessageResult.MSG_RESULT_FAIL_STRIGN;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return MessageConst.MessageResult.SQL_QUERY_FAILURE;
			}
	} 
	public Area getAreaDetails(int areaId){
		String sql = "select * from parking.areas_details where Id_p=?";
        if(conn == null){
//		conn = DBUtil.getDBUtil().getConnection();
        }
		PreparedStatement ps;
		Area aa =  new Area();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, areaId);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
				aa.setAreaId(res.getInt("Id_p"));
				aa.setAreaName(res.getString("area"));
				aa.setCityId(res.getInt("city"));
				aa.setBmapPoints(res.getString("coordinates"));
				}
				return aa;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public void createArea(Area a){
		String sql = "insert into parking.areas_details values(null,?,?,?)";
        if(conn == null){
//		conn = DBUtil.getDBUtil().getConnection();
        }
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getCityId());
			ps.setString(2, a.getAreaName());
			ps.setString(3, a.getBmapPoints());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    public ArrayList<Area> areaIdArrayList(int citycode){
		ArrayList<Area> areaList = new ArrayList<Area>();
	try {
        String sql = "select * from parking.areas_details where city=?";
        if(conn == null){
//		conn = DBUtil.getDBUtil().getConnection();
        }
        PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1,citycode );
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			while(rs.next()){
				Area area = new Area();
				area.setAreaId(rs.getInt("Id_p"));
				area.setAreaName(rs.getString("area"));
				area.setCityId(rs.getInt("city"));
//				area.setBmapPoints("coordinates");
				areaList.add(area);
			}
		}
		return areaList;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}
}
