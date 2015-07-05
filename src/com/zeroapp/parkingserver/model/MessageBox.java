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


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.parkingserver.dao.DBCPBean;
import com.zeroapp.parkingserver.factory.Worker;
import com.zeroapp.utils.Log;

/**
 * <p>
 * Title: MessageBox.
 * </p>
 * <p>
 * Description: MessageBox used to receiver ClientServerMessage form Client and
 * send ClientServerMessage to Client.
 * </p>
 * 
 * @author Alex(zeroapp@126.com) 2015-6-4.
 * @version $Id$
 */

public class MessageBox extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext mctx;
    private DBCPBean dbcpBean = new DBCPBean();

    public MessageBox() {
//        new MessagePool(MessageBox.this).startLooping();
    }

    public void sendMessage(ClientServerMessage m) {
        if (mctx != null) {
            mctx.writeAndFlush(m);
        }

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.i("");
        mctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg != null) {
            ClientServerMessage m = (ClientServerMessage) msg;
            Log.i("" + m.getMessageContent());
            Worker w = new Worker(MessageBox.this,dbcpBean.getConn());
            w.deal(m);
        } else {
            Log.w("null");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }

}
