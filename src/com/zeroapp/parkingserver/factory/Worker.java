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

package com.zeroapp.parkingserver.factory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.Area;
import com.zeroapp.parkingserver.common.Bidding;
import com.zeroapp.parkingserver.common.BiddingContainer;
import com.zeroapp.parkingserver.common.Business;
import com.zeroapp.parkingserver.common.CarInfo;
import com.zeroapp.parkingserver.common.CommercialDetails;
import com.zeroapp.parkingserver.common.ParkingInfo;
import com.zeroapp.parkingserver.common.User;
import com.zeroapp.parkingserver.dao.AreaDao;
import com.zeroapp.parkingserver.dao.BiddingDao;
import com.zeroapp.parkingserver.dao.BusinessDao;
import com.zeroapp.parkingserver.dao.CarDao;
import com.zeroapp.parkingserver.dao.CityDao;
import com.zeroapp.parkingserver.dao.DBCPBean;
import com.zeroapp.parkingserver.dao.DBUtil;
import com.zeroapp.parkingserver.dao.ParkingInfoDao;
import com.zeroapp.parkingserver.dao.UserDao;
import com.zeroapp.parkingserver.dao.VotingDao;
import com.zeroapp.parkingserver.model.MessageBox;
import com.zeroapp.tools.BmapPoint;
import com.zeroapp.tools.CalculateTimeUtils;
import com.zeroapp.tools.GraphTool;
import com.zeroapp.tools.Tool;
import com.zeroapp.utils.Log;

/**
 * <p>
 * Title: Worker.
 * </p>
 * <p>
 * Description: 负责接收\处理客户端发过来消息请求,并向客户端返回处理结果的类.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-6-6.
 * @version $Id$
 */

public class Worker {

	private MessageBox mBox = null;
	private Connection conn= null;

	public Worker(MessageBox box,Connection connection) {
		this.mBox = box;
		if(conn == null){
			System.out.println("is conn null? "+conn);
		this.conn = connection;
		}
	}

	public void deal(ClientServerMessage m) {
		if (m == null || m.getMessageType() == -1) {
			return;
		}
		Log.i("MessageType: " + m.getMessageType());
		switch (m.getMessageType()) {
		case MessageConst.MessageType.MSG_TYPE_USER_SIGN_IN:
			signIn(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_SIGN_UP:
			signup(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_LIST_MONEY:
			listParingRecord(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_LIST_BIDDING:
			listBiddings(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_SELECT_BIDDING:
			createVoting(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_SEND_PARK_INFO:
			setParkingProfit(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_LIST_MYCARS:
			listCars(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_ADD_CARS:
			addCars(m);
			break;
		// case MessageConst.MessageType.MSG_TYPE_USER_UPDATE_ADING:
		// setParkingProfit(m);
		// break;
		case MessageConst.MessageType.MSG_TYPE_COMPANY_CREATE_BIDDING:
			biddingsCreatedByCom(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_USER_CREATE_VOTING:
			createVoting(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_COMPANY_LIST_BUSINESS:
			getBusinessList(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_ADMIN_SEARCH_USER:
			getUserInfoForAdmin(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_ADMIN_UPFATE_USERINFO:// userid,name,pnum,idnum
			updateUserInfoByAdmin(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_ADMIN_UPFATE_CAR_STATE:
			updateUserCarState(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_COMPANY_LIST_COST:
			listCostForMoneyLord(m);
			break;
		case MessageConst.MessageType.MSG_TYPE_COMPANY_LIST_MY_BIDDING:
		default:
			break;
		}
	}

	/**
	 * <p>
	 * Title: signIn.
	 * </p>
	 * <p>
	 * Description: 用户登录,首先检查用户名密码是否匹配; 其次,组织用户信息详情返回给客户端.
	 * </p>
	 * 
	 */
	private void signIn(ClientServerMessage m) {
		UserDao ud = new UserDao(conn);
		User u = ContentToObj.getUser(m.getMessageContent());
		Log.i(u.getAccount());
		Log.i(u.getPassword());
		int result = ud.signIn(u);
		Log.i("back-->Result: " + result);
		m.setMessageResult(result);
		if (result == 1) {
			u = ud.getUserInfo(u.getAccount());
			String content = ObjToContent.getContent(u);
			m.setMessageContent(content);
			Log.i("back-->Content: " + content);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	/**
	 * <p>
	 * Title: signup.
	 * </p>
	 * <p>
	 * Description: 用户注册.
	 * </p>
	 * 
	 * @param m
	 */
	private void signup(ClientServerMessage m) {
		UserDao ud = new UserDao(conn);
		User u = ContentToObj.getUser(m.getMessageContent());
		if (ud.isAccountExist(u.getAccount()) != MessageConst.USER_CONSTANST.NOT_EXIST) {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
			m.setMessageContent("exist");
			mBox.sendMessage(m);
			return;
		}
		int result = ud.signUp(u);
		Log.i("back-->Result: " + result);
		m.setMessageResult(result);
		if (result == 1) {
			u = ud.getUserInfo(u.getAccount());
			String content = ObjToContent.getContent(u);
			m.setMessageContent(content);
			Log.i("back-->Content: " + content);
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
			m.setMessageContent("sqlexp");
		}
		closeConn(conn);
		mBox.sendMessage(m);

	}

	private void addCars(ClientServerMessage m) {
		CarDao cd = new CarDao(conn);
		String resString = cd.addCar(ContentToObj.getCarInfo(m
				.getMessageContent()));
		m.setMessageContent(resString);
		closeConn(conn);
		mBox.sendMessage(m);
	}

	/**
	 * <p>
	 * Title: TODO.
	 * </p>
	 * <p>
	 * Description: TODO.
	 * </p>
	 * 
	 * @param m
	 */
	private void listCars(ClientServerMessage m) {
		CarDao d = new CarDao(conn);
		User u = ContentToObj.getUser(m.getMessageContent());
		List<CarInfo> cars = d.getCars(u.getUserID());

		if (cars != null) {
			Log.i("back-->Result: "
					+ MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			String content = ObjToContent.getContent(cars);
			m.setMessageContent(content);
			Log.i("back-->Content: " + content);
		} else {
			Log.i("back-->Result: "
					+ MessageConst.MessageResult.MSG_RESULT_FAIL);
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
		closeConn(conn);
		mBox.sendMessage(m);

	}

	/**
	 * <p>
	 * Title: TODO.
	 * </p>
	 * <p>
	 * Description: TODO.
	 * </p>
	 * 
	 * @param m
	 */
	private void listBiddings(ClientServerMessage m) {
		if (m.getMessageContent().equals("qingdao")) {
			ArrayList<BiddingContainer> biddingArrayList = new ArrayList<BiddingContainer>();
			AreaDao areaDao = new AreaDao(conn);
			BusinessDao bd = new BusinessDao(conn);
			BiddingDao biddingDao = new BiddingDao(conn);
			CityDao cityDao = new CityDao(conn);
			int cityId = cityDao.getCityId("qingdao");
			ArrayList<CommercialDetails> bCommercialDetailsList = new ArrayList<CommercialDetails>();
			ArrayList<Area> areaList = areaDao.areaIdArrayList(cityId);
			for (Area area : areaList) {
				CommercialDetails bCommercialDetails = bd
						.getAvailableBusinessForClients(area);
				if (bCommercialDetails != null) {
					bCommercialDetailsList.add(bCommercialDetails);
				}
			}
			for (CommercialDetails b : bCommercialDetailsList) {
				biddingArrayList.add(biddingDao
						.getBiddingDetailsFormBusiness(b));
			}
			Log.i("back-->Result: "
					+ MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			m.setMessageContent(ObjToContent.getContent(biddingArrayList));
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}

		// CarDao d = new CarDao();
		// User u = ContentToObj.getUser(m.getMessageContent());
		// List<CarInfo> cars = d.getCars(u.getUserID());
		//
		// if (cars != null) {
		// Log.i("back-->Result: " +
		// MessageConst.MessageResult.MSG_RESULT_SUCCESS);
		// m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
		// String content = ObjToContent.getContent(cars);
		// m.setMessageContent(content);
		// Log.i("back-->Content: " + content);
		// } else {
		// Log.i("back-->Result: " +
		// MessageConst.MessageResult.MSG_RESULT_FAIL);
		// m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		// }
		closeConn(conn);
		mBox.sendMessage(m);

	}

	/**
	 * <p>
	 * Title: TODO.
	 * </p>
	 * <p>
	 * Description: TODO.
	 * </p>
	 * 
	 */
	private void listParingRecord(ClientServerMessage m) {
		ParkingInfoDao pDao = new ParkingInfoDao(conn);
		ArrayList<ParkingInfo> parkingList = pDao.getParkingInfoDetails(
				ContentToObj.getUser(m.getMessageContent()).getUserID(),
				m.getMessageParameters());
		if (parkingList != null) {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			m.setMessageContent(ObjToContent.getContent(parkingList));
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void setParkingProfit(ClientServerMessage m) {
		AreaDao ad = new AreaDao(conn);
		BiddingDao bd = new BiddingDao(conn);
		BusinessDao businessDao = new BusinessDao(conn);
		CarDao caD = new CarDao(conn);
		UserDao userD = new UserDao(conn);
		ParkingInfoDao pId = new ParkingInfoDao(conn);
		VotingDao votingDao = new VotingDao(conn);
		int areaId = -1;
		int biddingId;
		ParkingInfo userBp = ContentToObj.getParkingInfo(m.getMessageContent());
		Log.w("user gps value: " + userBp.getLocationLongitude() + "hhhhh"
				+ userBp.getLocationLatitude());
		BmapPoint pBmapPoint = new BmapPoint(userBp.getLocationLongitude(),
				userBp.getLocationLatitude());
		biddingId = votingDao.getVoting(userBp.getCarNum()).getBiddingID();
		int businessId = bd.getBusinessIdFromBidding(biddingId);
		int userComId = bd.getBiddingDetailsFormBiddingId(biddingId)
				.getUserID();
		String bmapPointsCoordinatesS = ad.getAreaCoordinates(businessDao
				.getBusinessAreaId(businessId));
		Log.w("Bmap points coordinates: " + bmapPointsCoordinatesS);
		Business bs = businessDao.getBusinessDetails(businessId);
		User userCom = userD.getUserInfosFormUserId(userComId);
		BmapPoint[] bmapPointsCoordinatesGPS = ContentToObj
				.getCoordinatesOfArea(bmapPointsCoordinatesS);

		long businessTimes = CalculateTimeUtils.convert2long(bs.getTimeStart());
		long businessTimeE = CalculateTimeUtils.convert2long(bs.getTimeEnd());
		if (!Tool.isParkingTimeAvailable(businessTimes, businessTimeE,
				userBp.getTimeStart(), userBp.getTimeEnd())) {
			{
				m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
				m.setMessageContent(MessageConst.PARKING_CONSTANST.UNAVAILABLE_PARKING);
			}
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_PARKING_NOT_AVAILABLE);
			m.setMessageContent(MessageConst.PARKING_CONSTANST.NO_ENOUGH_MONEY_PARKING);
			mBox.sendMessage(m);
			return;
		}
		if (GraphTool.isPointInPolygon(pBmapPoint, bmapPointsCoordinatesGPS)) {
			areaId = businessDao.getBusinessAreaId(businessId);

			double profitUser = Tool.getBiddingProfit(businessTimes,
					businessTimeE, userBp.getTimeStart(), userBp.getTimeEnd(),
					bs.getEarnings(), 3600000);
			double profitCompany = Tool.getBiddingProfit(businessTimes,
					businessTimeE, userBp.getTimeStart(), userBp.getTimeEnd(),
					bs.getCost(), 3600000);
			if (userCom.getAccountBanlance() <= 0) {
				m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
				m.setMessageContent(MessageConst.BUSINESS_CONSTANST.MONEY_EXPENDED);
			} else if (userCom.getAccountBanlance() <= profitCompany) {
				userD.setUserBanlance(userCom.getAccountBanlance(),
						userBp.getUserId());
				m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
				m.setMessageContent(MessageConst.BUSINESS_CONSTANST.MONEY_EXPENDED);
				userD.updateUserAccountItem(-1, userComId);
			} else {
				userD.updateUserAccountItem(profitCompany, userComId);
				userD.setUserBanlance(profitUser, userBp.getUserId());
				m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
				m.setMessageContent(ObjToContent.getContent(ad
						.getAreaName(areaId)
						+ "|"
						+ userD.getUserBanlance(userBp.getUserId())));
			}
			pId.creatParkingInfo(userBp.getCarNum(),
					userBp.getLocationLongitude(),
					userBp.getLocationLatitude(),
					CalculateTimeUtils.convert2String(userBp.getTimeStart()),
					CalculateTimeUtils.convert2String(userBp.getTimeEnd()),
					profitUser, profitCompany, userBp.getUserId());
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
			m.setMessageContent(MessageConst.AREA_CONSTANST.NO_THIS_AREA);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	public void createBid(ClientServerMessage m) {
		BiddingDao bd = new BiddingDao(conn);
		if (bd.createBid(ContentToObj.getBidding(m.getMessageContent()))) {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	// public int setBiddingId2Car(ClientServerMessage m){
	// CarDao cd = new CarDao();
	// cd.set
	// }
	private void createVoting(ClientServerMessage m) {
		// TODO 查询car state，更改cat state;
		VotingDao vDao = new VotingDao(conn);
		int res = vDao.createVoting(ContentToObj.getVoting(m
				.getMessageContent()));

		m.setMessageResult(res);
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void getBusinessList(ClientServerMessage m) {
		if (m.getMessageContent().equals("qingdao")) {
			CityDao cityDao = new CityDao(conn);
			AreaDao areaDao = new AreaDao(conn);
			int cityId = cityDao.getCityId("qingdao");
			ArrayList<CommercialDetails> bCommercialDetailsList = new ArrayList<CommercialDetails>();
			ArrayList<Area> areaList = areaDao.areaIdArrayList(cityId);
			BusinessDao bd = new BusinessDao(conn);
			for (Area area : areaList) {
				CommercialDetails bCommercialDetails = bd
						.getAvailableBusinessForClients(area);
				if (bCommercialDetails != null) {
					bCommercialDetailsList.add(bCommercialDetails);
				}
			}
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			m.setMessageContent(ObjToContent.getContent(bCommercialDetailsList));
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void biddingsCreatedByCom(ClientServerMessage m) {
		BiddingDao bd = new BiddingDao(conn);
		boolean resBoolean = bd.createBid(ContentToObj.getBidding(m
				.getMessageContent()));
		if (resBoolean) {
			m.setMessageType(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
		} else {
			m.setMessageType(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void getUserInfoForAdmin(ClientServerMessage m) {
		UserDao ud = new UserDao(conn);
		User user = new User();
		user = ud.getUserInfoByPhoneNum(ContentToObj.getUser(
				m.getMessageContent()).getPhoneNum());
		if (user != null) {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
			m.setMessageContent(ObjToContent.getContent(user));
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void updateUserCarState(ClientServerMessage m) {
		CarInfo carIn = ContentToObj.getCarInfo(m.getMessageContent());
		CarDao ca = new CarDao(conn);
		int res = ca.changeCarState(carIn.getCarNum(), carIn.getCarState());
		if (res > 0) {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
		} else {
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void listCostForMoneyLord(ClientServerMessage m) {
		VotingDao votingdao = new VotingDao(conn);
		ParkingInfoDao parkingInfoDao = new ParkingInfoDao(conn);
		BiddingDao biddingDao = new BiddingDao(conn);
		User u = ContentToObj.getUser(m.getMessageContent());
		ArrayList<Bidding> biddingList = biddingDao.getUserBiddings(u
				.getUserID());
		ArrayList<ParkingInfo> parkingInfoList = new ArrayList<ParkingInfo>();
//		ArrayList<String> carNumArrayList = new ArrayList<String>();
		String sql = "select carnum from parking.voting where biddingid=?";
//		Connection conn = DBUtil.getDBUtil().getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			if (biddingList != null) {
				for (Bidding b : biddingList) {
					ps.setInt(1, b.getBiddingID());
					ResultSet rs = ps.executeQuery();
					if (rs != null) {
						while (rs.next()) {
							ArrayList<ParkingInfo> pList = new ArrayList<ParkingInfo>();
							String carN = votingdao.getVoting(b.getBiddingID()).getCarNum();
							if(carN == null){
								m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
								m.setMessageContent(MessageConst.SQL_CONSTANST.SQL_EXP);
							}
							pList = parkingInfoDao.getParkingInfoDetails(carN);
							parkingInfoList.addAll(pList);
						}
					}
				}

			}
			m.setMessageContent(ObjToContent.getContent(parkingInfoList));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
			m.setMessageContent(MessageConst.SQL_CONSTANST.SQL_EXP);
			e.printStackTrace();
		}
		if(parkingInfoList.size()>0){
			m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}

	private void updateUserInfoByAdmin(ClientServerMessage m) {
		UserDao ud = new UserDao(conn);
		int rsInt = ud.updateUserItems(ContentToObj.getUser(m
				.getMessageContent()));
		m.setMessageResult(rsInt);
		if (rsInt == MessageConst.MessageResult.MSG_RESULT_FAIL) {
			m.setMessageContent(MessageConst.USER_CONSTANST.UPDATE_FAILE);
		} else {
			m.setMessageContent(MessageConst.MessageResult.SQL_OPREATION_EXCEPTION_STRING);
		}
		closeConn(conn);
		mBox.sendMessage(m);
	}
	private void closeConn(Connection conn){
		try {
			conn.commit();
			conn.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
