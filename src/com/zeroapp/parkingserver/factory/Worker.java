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

import java.util.List;

import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.CarInfo;
import com.zeroapp.parkingserver.common.User;
import com.zeroapp.parkingserver.dao.CarDao;
import com.zeroapp.parkingserver.dao.UserDao;
import com.zeroapp.parkingserver.model.MessageBox;
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

    public Worker(MessageBox box) {
        mBox = box;
    }

    public void deal(ClientServerMessage m) {
        if (m == null || m.getMessageType() == -1) {
            return;
        }
        Log.i("" + m.getMessageType());
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
                // TODO
                break;
            case MessageConst.MessageType.MSG_TYPE_USER_SEND_PARK_INFO:
                // TODO
                break;
            case MessageConst.MessageType.MSG_TYPE_USER_LIST_MYCARS:
                listCars(m);
                break;

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
        UserDao ud = new UserDao();
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
        UserDao ud = new UserDao();
        User u = ContentToObj.getUser(m.getMessageContent());
        int result = ud.signUp(u);
        Log.i("back-->Result: " + result);
        m.setMessageResult(result);
        if (result == 1) {
            u = ud.getUserInfo(u.getAccount());
            String content = ObjToContent.getContent(u);
            m.setMessageContent(content);
            Log.i("back-->Content: " + content);
            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
        }else {
        	m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
		}
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
        CarDao d = new CarDao();
        User u = ContentToObj.getUser(m.getMessageContent());
        List<CarInfo> cars = d.getCars(u.getUserID());

        if (cars != null) {
            Log.i("back-->Result: " + MessageConst.MessageResult.MSG_RESULT_SUCCESS);
            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
            String content = ObjToContent.getContent(cars);
            m.setMessageContent(content);
            Log.i("back-->Content: " + content);
        } else {
            Log.i("back-->Result: " + MessageConst.MessageResult.MSG_RESULT_FAIL);
            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
        }
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
            Log.i("back-->Result: " + MessageConst.MessageResult.MSG_RESULT_SUCCESS);
            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
        }

//        CarDao d = new CarDao();
//        User u = ContentToObj.getUser(m.getMessageContent());
//        List<CarInfo> cars = d.getCars(u.getUserID());
//
//        if (cars != null) {
//            Log.i("back-->Result: " + MessageConst.MessageResult.MSG_RESULT_SUCCESS);
//            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
//            String content = ObjToContent.getContent(cars);
//            m.setMessageContent(content);
//            Log.i("back-->Content: " + content);
//        } else {
//            Log.i("back-->Result: " + MessageConst.MessageResult.MSG_RESULT_FAIL);
//            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_FAIL);
//        }
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
        if (m.getMessageContent().equals("money")) {
            Log.i("back-->Result: " + MessageConst.MessageResult.MSG_RESULT_SUCCESS);
            m.setMessageResult(MessageConst.MessageResult.MSG_RESULT_SUCCESS);
        }
        mBox.sendMessage(m);

    }
}
