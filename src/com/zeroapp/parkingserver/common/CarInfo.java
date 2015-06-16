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

package com.zeroapp.parkingserver.common;


/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Alex(zeroapp@126.com) 2015-6-4.
 * @version $Id$
 */

public class CarInfo {

    /**
     * 车牌号
     */
    private String carNum = "";
    /**
     * 用户ID
     */
    private int userID = -1;
    private int biddingID = -1;
    /**
     * 该车当前的状态,分为两部分
     * _之前标示该车是否可以抢广告;
     * _之后标示该车是否可以收入Money;
     */
    private String carState = "";

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the carNum.
     */
    private String parkingAreaString;
    private String carTypeString;
    private String carValueString;
    
    public String getParkingArea(){
    	return parkingAreaString;
    }
    public void setParkingArea(String parkingA){
    	this.parkingAreaString = parkingA;
    }
    public String getCarType(){
    	return carTypeString;
    }
    public void setCarType(String carT){
    	this.carTypeString = carT;
    }
    public String getCarValue(){
    	return carValueString;
    }
    public void setCarValue(String carValue){
    	this.carValueString = carValue;
    }
    public String getCarNum() {
        return carNum;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param carNum
     *            the carNum to set.
     */
    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the userID.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param userID
     *            the userID to set.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the biddingID.
     */
    public int getBiddingID() {
        return biddingID;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param biddingID
     *            the biddingID to set.
     */
    public void setBiddingID(int biddingID) {
        this.biddingID = biddingID;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the carState.
     */
    public String getCarState() {
        return carState;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param carState
     *            the carState to set.
     */
    public void setCarState(String carState) {
        this.carState = carState;
    }


}
