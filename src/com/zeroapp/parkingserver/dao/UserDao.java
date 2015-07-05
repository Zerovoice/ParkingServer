package com.zeroapp.parkingserver.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.User;
import com.zeroapp.utils.Config;

public class UserDao {
	
	
	private Connection conn;
	public UserDao(Connection connection){
		this.conn = connection;
	}
	
	/**
	 * <p>
	 * Title: signIn.
	 * </p>
	 * <p>
	 * Description: 根据用户的账号密码是否匹配判断登录成功与否.
	 * </p>
	 * 
	 * @param u
	 * @return
	 */
	public int signIn(User u) {

		try {
			String sql = "select * from user_info where Account=? and Password=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
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

	public int signUp(User u) {
		try {

			// String sql =
			// "insert into user_info(Account,Password,Name,IdentityNum,Sex,PhoneNum,UserType,AccountBanlance) values(?,?,?,?,?,?,?,?)";
			String sql = "insert into user_info values(null,?,?,?,?,?,?,?,?)";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getAccount());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getName());
			ps.setString(4, u.getIdentityNum());
			ps.setInt(5, u.getSex());
			ps.setString(6, u.getPhoneNum());
			ps.setString(7, u.getUserType());
			ps.setDouble(8, u.getAccountBanlance());
			int r = ps.executeUpdate();
			if (r > 0) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * <p>
	 * Title: getUserInfo.
	 * </p>
	 * <p>
	 * Description: 获取用户详情,组织成User对象.
	 * </p>
	 * 
	 * @param account
	 * @return
	 */
	public User getUserInfo(String account) {
		User res = new User();
		try {
			String sql = "select * from user_info where Account=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setUserID(rs.getInt("UserID"));
				res.setAccount(account);
				res.setPassword(rs.getString("Password"));
				res.setName(rs.getString("Name"));
				res.setIdentityNum(rs.getString("IdentityNum"));
				res.setSex(rs.getInt("Sex"));
				res.setPhoneNum(rs.getString("PhoneNum"));
				res.setUserType(rs.getString("UserType"));
				res.setAccountBanlance(rs.getInt("AccountBanlance"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public User getUserInfoByPhoneNum(String pNum) {
		User res = new User();
		try {
			String sql = "select * from user_info where phoneNum=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, pNum);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				res.setUserID(rs.getInt("UserID"));
				res.setAccount(rs.getString("account"));
				res.setPassword(rs.getString("Password"));
				res.setName(rs.getString("Name"));
				res.setIdentityNum(rs.getString("IdentityNum"));
				res.setSex(rs.getInt("Sex"));
				res.setPhoneNum(rs.getString("PhoneNum"));
				res.setUserType(rs.getString("UserType"));
				res.setAccountBanlance(rs.getInt("AccountBanlance"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * <p>
	 * Title: changepassWord.
	 * </p>
	 * <p>
	 * Description: 修改账号密码.
	 * </p>
	 * 
	 * @param account
	 * @param newPassword
	 * @return
	 */
	public boolean changepassWord(String account, String newPassword) {
		try {
			String sql = "update user_info set Password=? where Account=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setString(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changePhoneNumber(String account, String newPhoneNumber) {
		try {
			String sql = "update user_info set PhoneNum=? where Account=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPhoneNumber);
			ps.setString(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeName(String account, String newName) {
		try {
			String sql = "update user_info set Name=? where Account=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newName);
			ps.setString(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeIdentityNumber(String account, String newIdentityNumber) {
		try {
			String sql = "update user_info set IdentityNum=? where Account=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newIdentityNumber);
			ps.setString(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeSex(String account, String sex) {
		try {
			String sql = "update user_info set Sex=? where Account=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			if (sex.equals("男")) {// TODO
				ps.setInt(1, 1);
			} else {
				ps.setInt(1, 0);
			}
			ps.setString(2, account);
			int r = ps.executeUpdate();
			if (r > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean changeAccountBalance(String account, String newBanlance) {
		try {
			String sql = "update user_info set AccountBalance=? where Account=?";
			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newBanlance);
			ps.setString(2, account);
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
//			Connection conn = DBUtil.getDBUtil().getConnection();
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
			String sql = "insert into Action_Buddy values(null,?,?)";
//			Connection conn = DBUtil.getDBUtil().getConnection();
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
		String myFriends = "";
		try {
			String sql = "select * from Action_buddy where BUDDY_ACCOUNT="
					+ account;
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String myFriendInfo = "";
				String sql2 = "select * from Action_user where USER_ACCOUNT="
						+ rs.getInt("BUDDY_BUDDY");
				Connection conn2 = DBUtil.getDBUtil().getConnection();
				PreparedStatement ps2 = conn2.prepareStatement(sql2);
				ResultSet you = ps2.executeQuery();
				while (you.next()) {
					myFriendInfo = you.getInt("USER_ACCOUNT") + "_"
							+ you.getString("USER_NICKNAME") + "_"
							+ you.getString("USER_AVATAR") + "_"
							+ you.getString("USER_TREND") + "_"
							+ you.getInt("USER_ISONLINE") + "_"
							+ you.getInt("USER_TAG") + ";";
				}
				myFriends += myFriendInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myFriends;
	}

	private boolean updateAvatar(int account, String filePath) {
		try {
			String sql = "update Action_user set USER_AVATAR=? where USER_ACCOUNT=?";
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			System.out.println("local filepath [" + filePath + "]");
			String s = "D:\\Develop\\apache-tomcat-6.0.35\\webapps\\ActionServer\\userdata\\";
			System.out.println("s length [" + s.length() + "]");
			String filename = filePath.substring(62);
			String url = "http://" + Config.HOST_ADRESS
					+ ":8080/ActionServer/userdata/" + filename;
			System.out.println("url is [" + url + "]");
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
			String sql = "select USER_TAG from Action_user where USER_ACCOUNT="
					+ account;
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tag = (-1) * rs.getInt("USER_TAG");
				System.out.println("new tag :" + tag);
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

	public int isAccountExist(String accountname) {
		String sql = "select account from parking.user_info where account=?";
		try {
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, accountname);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				return MessageConst.USER_CONSTANST.ALREADY_EXIST;
			}
			return MessageConst.USER_CONSTANST.NOT_EXIST;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}

	}

	public String getUserBanlance(int userId) {
		String sql = "select AccountBanlance from parking.user_info where userid=?";
		try {
//			Connection conn = DBUtil.getDBUtil().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet res = ps.executeQuery();
			while (res.next()) {
				return res.getString("accountbanlance");
			}
			return MessageConst.MessageResult.SQL_QUERY_FAILURE;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_STRING;
		}

	}
	public int setUserBanlance(double profit,int userid){
		String sql = "update parking.user_info set accountbanlance=accountbanlance+"+profit+" where userid=?";
		try {
//		Connection conn = DBUtil.getDBUtil().getConnection();
		PreparedStatement ps =  conn.prepareStatement(sql);
		ps.setInt(1, userid);
		int res =ps.executeUpdate();
		if(res>0){
		return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
		}else {
		return MessageConst.MessageResult.MSG_RESULT_FAIL;
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}
	}
	public User getUserInfosFormUserId(int userid){
		String sql = "select * from parking.user_info where userid=?";
		User u =  new User();
//		Connection conn = DBUtil.getDBUtil().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userid);
			ResultSet res = ps.executeQuery();
			if(res!=null){
				while(res.next()){
				u.setAccountBanlance(res.getDouble("accountbanlance"));
				}
			}
			return u;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public int updateUserAccountItem(double profit, int userid) {
//		Connection conn = DBUtil.getDBUtil().getConnection();
		String sql;
		try {
			if (profit == -1) {
				sql = "update parking.user_info set AccountBanlance=0 where userid=?";
			}else{
				sql = "update parking.user_info set AccountBanlance=AccountBanlance-" + profit
					+ "where userid=?";
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userid);
			int res = ps.executeUpdate();
			if (res > 0) {
				return MessageConst.MessageResult.MSG_RESULT_SUCCESS;
			}
			return MessageConst.MessageResult.MSG_RESULT_FAIL;
		} catch (SQLException e) {
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}
	}
	public int updateUserItems(User u) {
		String sql = "UPDATE `parking`.`user_info` SET `Account`=?, `Password`=?, `Name`=?, `Sex`=?, `PhoneNum`=?, `UserType`=?, `AccountBanlance`=? WHERE `UserID`='2'";
//		Connection conn = DBUtil.getDBUtil().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int resInt = ps.executeUpdate();
			return resInt;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_INT;
		}
	}
}