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
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: 单次停车的信息.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-6-4.
 * @version $Id$
 */

public class ParkingInfo {

    /**
     * 该次停车记录的ID
     */
    private int parkingID = -1;
    /**
     * 车牌号
     */
    private String carNum = "";
    /**
     * 该次停车记录点的经度
     */
    private String locationLongitude = "";
    /**
     * 该次停车记录点的纬度
     */
    private String locationLatitude = "";
    /**
     * 该次停车记录的起始时间
     */
    private String timeStart = "0";
    /**
     * 该次停车记录的终止时间
     */
    private String timeEnd = "0";
    /**
     * 该次停车记录的收入
     */
    private String money = "0";

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the parkingID.
     */
    public int getParkingID() {
        return parkingID;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param parkingID
     *            the parkingID to set.
     */
    public void setParkingID(int parkingID) {
        this.parkingID = parkingID;
    }

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
     * @return the locationLongitude.
     */
    public String getLocationLongitude() {
        return locationLongitude;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param locationLongitude
     *            the locationLongitude to set.
     */
    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the locationLatitude.
     */
    public String getLocationLatitude() {
        return locationLatitude;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param locationLatitude
     *            the locationLatitude to set.
     */
    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the timeStart.
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param timeStart
     *            the timeStart to set.
     */
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the timeEnd.
     */
    public String getTimeEnd() {
        return timeEnd;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param timeEnd
     *            the timeEnd to set.
     */
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @return the money.
     */
    public String getMoney() {
        return money;
    }

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param money
     *            the money to set.
     */
    public void setMoney(String money) {
        this.money = money;
    }

}
