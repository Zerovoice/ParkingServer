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

import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.parking.message.MessageConst;
import com.zeroapp.parkingserver.common.User;
import com.zeroapp.parkingserver.dao.UserDao;
import com.zeroapp.parkingserver.model.ParkingServer;
import com.zeroapp.utils.Log;



/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Alex(zeroapp@126.com) 2015-6-6.
 * @version $Id$
 */

public class Worker {

    public Worker() {
    }

    public static void deal(ClientServerMessage m) {
        if (m == null || m.getMessageType() == -1) {
            return;
        }
        Log.i("" + m.getMessageType());
        switch (m.getMessageType()) {
            case MessageConst.MessageType.MSG_TYPE_USER_SIGN_IN:
                signIn(m);
                break;

            default:
                break;
        }
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
    private static void signIn(ClientServerMessage m) {
        UserDao ud = new UserDao();
        User u = ContentToObj.getUser(m.getMessageContent());
        int result = ud.login(u);
        Log.i("back-->Result: " + result);
        m.setMessageResult(result);
        if (result == 1) {
            String content = ud.getUser(u.getAccount());
            m.setMessageContent(content);
            Log.i("back-->Content: " + content);
        }
        ParkingServer.getServer().getBox().sendMessage(m);
    }

}
