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

package com.zeroapp.parking.message;

/**
 * <p>
 * Title: TODO.
 * </p>
 * <p>
 * Description: TODO.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-5-28.
 * @version $Id$
 */
public class MessageConst {

    public class MessageResult {

        public static final int MSG_RESULT_FAIL = 0;
        public static final int MSG_RESULT_SUCCESS = 1;
    }

    public class MessageType {

        // Local message
        public static final int MESSAGE_UI = 1;
        // Message types sent to server
        public static final int MESSAGE_TO_SERVER = 2;
        // Message types sent from server
        public static final int MESSAGE_FROM_SERVER = 3;

        // Message types sent from Client by admin
        public static final int MSG_TYPE_ADMIN_SIGN_IN = 1000;

        // Message types sent from Client by company
        public static final int MSG_TYPE_COMPANY_SIGN_IN = 2000;

        // Message types sent from Client by normal user
        public static final int MSG_TYPE_USER_SIGN_IN = 3000;
        public static final int MSG_TYPE_USER_SIGN_UP = 3001;
        public static final int MSG_TYPE_USER_LIST_MONEY = 3002;
        public static final int MSG_TYPE_USER_LIST_BIDDING = 3003;
        public static final int MSG_TYPE_USER_SELECT_BIDDING = 3004;
        public static final int MSG_TYPE_USER_DROP_BIDDING = 3005;
        public static final int MSG_TYPE_USER_SEND_PARK_INFO = 3006;
        public static final int MSG_TYPE_USER_LIST_MYCARS = 3007;

        // Message types sent from the BluetoothChatService Handler
        public static final int MSG_TYPE_BLUETOOTH_STATE_CHANGE = 11;
        public static final int MSG_TYPE_BLUETOOTH_READ = 12;
        public static final int MSG_TYPE_BLUETOOTH_WRITE = 13;
        public static final int MSG_TYPE_BLUETOOTH_DEVICE_NAME = 14;
        public static final int MSG_TYPE_BLUETOOTH_TOAST = 15;
        public static final int MSG_TYPE_UI_SHOW_USER_INFO = 16;


    }

}
