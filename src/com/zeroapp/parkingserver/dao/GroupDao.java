package com.zeroapp.parkingserver.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
	public String getQuans(){
		String g="";
		try {
			String sql = "select * from Action_group";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				g=g+rs.getInt("GROUP_ACCOUNT")+"_"+rs.getString("GROUP_NICKNAME")+"_"+rs.getString("GROUP_TREND")+" ";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}
	
	public String getGroupNick(int gaccount){
		String g="";
		try {
			String sql = "select * from Action_group where GROUP_ACCOUNT="+gaccount;
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				g=rs.getString("gnick");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return g;
	}
	
	public List<Integer> getGroupMember(int gaccount){
		List<Integer> res=new ArrayList<Integer>();
		try {
			String sql = "select * from Action_group_member where GROUP_ACCOUNT="+gaccount;
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				res.add(rs.getInt("GROUP_MEMBER"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
/*	public static void main(String[] args){
		String s=new GroupDao().getGroup();
		System.out.println(s);
	}*/
}
