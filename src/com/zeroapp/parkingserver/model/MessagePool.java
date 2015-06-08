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

package com.zeroapp.parkingserver.model;


import java.util.concurrent.LinkedBlockingQueue;

import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.parkingserver.factory.Worker;
import com.zeroapp.utils.Log;

/**
 * <p>Title: TODO.</p>
 * <p>Description: TODO.</p>
 *
 * @author Alex(zeroapp@126.com) 2015-6-6.
 * @version $Id$
 */

public class MessagePool {

    private static final boolean DEBUG = false;
    private static LinkedBlockingQueue<ClientServerMessage> messageQueue = new LinkedBlockingQueue<ClientServerMessage>();

    public MessagePool() {

    }

    public void startLooping() {
        new Thread(new Looper()).start();

    }
    public static void inlet(ClientServerMessage buffer) {
        if (DEBUG)
            Log.i("inlet---buffer: " + buffer);
        if (buffer != null) {
            synchronized (messageQueue) {
                messageQueue.add(buffer);
            }
            if (DEBUG)
                Log.i("inlet---messageQueue size: " + messageQueue.size());
        }
    }

    public static ClientServerMessage outlet() {
        synchronized (messageQueue) {
            if (DEBUG)
                Log.i("outlet---messageQueue size: " + messageQueue.size());
            return messageQueue.poll();
        }
    }

    public class Looper implements Runnable {

        private boolean looping = true;

        @Override
        public void run() {
            while (looping) {
                ClientServerMessage m = MessagePool.outlet();
//                Log.i("" + m);
                if (m != null) {
                    Worker.deal(m);
                }else{
                }
            }

        }

    }
}
