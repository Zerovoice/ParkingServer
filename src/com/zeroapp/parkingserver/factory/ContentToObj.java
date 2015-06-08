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

import com.zeroapp.parkingserver.common.User;
import com.zeroapp.utils.Log;


/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Alex(zeroapp@126.com) 2015-6-8.
 * @version $Id$
 */

public class ContentToObj {

    /**
     * <p>
     * Title: TODO.
     * </p>
     * <p>
     * Description: TODO.
     * </p>
     * 
     * @param messageContent
     * @return
     */
    public static User getUser(String messageContent) {
        Log.i("messageContent: " + messageContent);
        User u = new User();
        // TODO test code
        u.setAccount("zxb");
        u.setPassword("123");
        // test code
        return u;
    }

}
