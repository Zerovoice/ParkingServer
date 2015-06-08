package com.zeroapp.parkingserver.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.zeroapp.parkingserver.common.User;
import com.zeroapp.tools.Usera;
import com.zeroapp.utils.Config;



public class UserDao {

    public int login(User u) {
		try {
            String sql = "select * from user_info where Account=? and Password=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getAccount());
            ps.setString(2, u.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next() == true) {
                return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return 0;
	}
	
	public boolean register(Usera u) {
		try {
			String sql = "insert into Action_user values(?,?,?,?,?,?,?,?,?,?,?)";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getAccount());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getNick());
			ps.setString(4, u.getAvatar());
			ps.setString(5, u.getTrends());
			ps.setString(6, u.getSex());
			ps.setInt(7, u.getAge());
			ps.setInt(8, u.getLev());
			ps.setInt(9, 0);  //�����û��Ƿ�����
			ps.setString(10, u.getTime());
			ps.setInt(11, u.getTag());
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

    private boolean delBuddy(int myAccount, int dfAccount) {
		try {
			String sql = "delete  from Action_buddy where BUDDY_ACCOUNT=? and BUDDY_BUDDY=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, myAccount);
			ps.setInt(2, dfAccount);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
    private boolean addBuddy(int sender, int receiver) {
		try {
			//TODO ��Ӻ���
			String sql = "insert into Action_Buddy values(null,?,?)";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sender);
			ps.setInt(2, receiver);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
    private String getBuddy(int account) {
		String myFriends="";
		try {
			String sql = "select * from Action_buddy where BUDDY_ACCOUNT="+account;
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String myFriendInfo="";
				String sql2 = "select * from Action_user where USER_ACCOUNT="+rs.getInt("BUDDY_BUDDY");
				Connection conn2 = DBUtil.getDBUtil().getConnection();
				PreparedStatement ps2 = conn2.prepareStatement(sql2);
				ResultSet you = ps2.executeQuery();
				while(you.next()){
					myFriendInfo=you.getInt("USER_ACCOUNT")+"_"+you.getString("USER_NICKNAME")+"_"
							+you.getString("USER_AVATAR")+"_"+you.getString("USER_TREND")+"_"+you.getInt("USER_ISONLINE")+"_"+you.getInt("USER_TAG")+";";
				}
				myFriends+=myFriendInfo;	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myFriends;
	}

    public String getUser(String account) {
		String res="";
		try {
//            String sql = "select * from user_info where Account=" + account;
            String sql = "select * from user_info where Account=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
                res = res + rs.getString("Name") + "_"
//						+rs.getString("USER_AVATAR")+"_"+rs.getString("USER_TREND")+"_"
//						+rs.getString("USER_SEX")+"_"+rs.getInt("USER_AGE")+"_"+rs.getInt("USER_LEV")+"_"+rs.getInt("USER_TAG")
                ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean changeState(int account,int state){
		try {
			String sql = "update Action_user set USER_ISONLINE=? where USER_ACCOUNT=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, state);
			ps.setInt(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
    private boolean updateAvatar(int account, String filePath) {
		try {
			String sql = "update Action_user set USER_AVATAR=? where USER_ACCOUNT=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println("local filepath [" + filePath
                    + "]");
			String s = "D:\\Develop\\apache-tomcat-6.0.35\\webapps\\ActionServer\\userdata\\";
			System.out.println("s length [" + s.length()
                    + "]");
			String filename = filePath.substring(62);
            String url = "http://" + Config.HOST_ADRESS + ":8080/ActionServer/userdata/" + filename;
			System.out.println("url is [" + url
                    + "]");
			ps.setString(1, url);
			ps.setInt(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
    private boolean updateTag(int account) {
		try {
			int tag = 1;
			String sql = "select USER_TAG from Action_user where USER_ACCOUNT="+account;
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				tag = (-1)*rs.getInt("USER_TAG");
				System.out.println("new tag :"+tag);
			}
			String sql2 = "update Action_user set USER_TAG=? where USER_ACCOUNT=?";
			Connection conn2 = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps2 = conn2.prepareStatement(sql2);
			ps2.setInt(1, tag);
			ps2.setInt(2, account);
			int r = ps2.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
}