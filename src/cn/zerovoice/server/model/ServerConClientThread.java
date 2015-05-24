/**
 * 服务器和某个客户端的通信线程
 */
package cn.zerovoice.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import cn.zerovoice.common.ActionMessage;
import cn.zerovoice.common.ActionMessageType;

public class ServerConClientThread extends Thread {
	Socket socket;

	public ServerConClientThread(Socket s) {
		this.socket = s;
	}

	public void run() {
		while (true) {
			ObjectInputStream ois = null;
			ActionMessage m = null;
			try {
				//TODO socket closed?any problem?
//				if(socket.isClosed()) {
//					System.out.println("socket is closed ");
//					return;
//				}
				ois = new ObjectInputStream(socket.getInputStream());
				m = (ActionMessage) ois.readObject();
				// 对从客户端取得的消息进行类型判断，做相应的处理
				if (m.getType().equals(ActionMessageType.COM_MES)) {// 如果是普通消息包
					DoWhatAndSendMes.sendMes(m);
				} else if (m.getType().equals(ActionMessageType.GROUP_MES)) { // 如果是群消息
					DoWhatAndSendMes.sendGroupMes(m);
				} else if (m.getType().equals(ActionMessageType.GET_ONLINE_FRIENDS)) {// 如果是请求好友列表
					DoWhatAndSendMes.sendBuddyList(m);
				} else if (m.getType().equals(ActionMessageType.DEL_BUDDY)) { // 如果是删除好友
					DoWhatAndSendMes.delBuddy(m);
				} else if (m.getType().equals(ActionMessageType.ADD_BUDDY)) { // 如果是添加好友
					DoWhatAndSendMes.addBuddy(m);
				} else if (m.getType().equals(ActionMessageType.GET_QUANS)) { // 如果是请求圈子列表
					DoWhatAndSendMes.sendQuanList(m);
				} else if (m.getType().equals(ActionMessageType.UPLOAD_PIC)) { // 如果是请求更新图片
					System.out.println(m.getSender()+" save pic");
					if(DoWhatAndSendMes.savePic(m)){
						System.out.println(m.getSender()+" save pic success");
					}
				}
			} catch (Exception e) {
				try {
					socket.close();
					ois.close();
				} catch (IOException e1) {
				}
				e.printStackTrace();
			} 
//			  finally {
//				System.out.println("1111111111");
//				if (null != ois) {
//					try {
//						ois.close();
//						System.out.println("222222222222");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				if (null != socket) {
//					try {
//						socket.close();
//						System.out.println("333333333333");
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			} 
		}
	}
}
