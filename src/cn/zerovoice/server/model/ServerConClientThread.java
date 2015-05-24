/**
 * ��������ĳ���ͻ��˵�ͨ���߳�
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
				// �Դӿͻ���ȡ�õ���Ϣ���������жϣ�����Ӧ�Ĵ���
				if (m.getType().equals(ActionMessageType.COM_MES)) {// �������ͨ��Ϣ��
					DoWhatAndSendMes.sendMes(m);
				} else if (m.getType().equals(ActionMessageType.GROUP_MES)) { // �����Ⱥ��Ϣ
					DoWhatAndSendMes.sendGroupMes(m);
				} else if (m.getType().equals(ActionMessageType.GET_ONLINE_FRIENDS)) {// �������������б�
					DoWhatAndSendMes.sendBuddyList(m);
				} else if (m.getType().equals(ActionMessageType.DEL_BUDDY)) { // �����ɾ������
					DoWhatAndSendMes.delBuddy(m);
				} else if (m.getType().equals(ActionMessageType.ADD_BUDDY)) { // �������Ӻ���
					DoWhatAndSendMes.addBuddy(m);
				} else if (m.getType().equals(ActionMessageType.GET_QUANS)) { // ���������Ȧ���б�
					DoWhatAndSendMes.sendQuanList(m);
				} else if (m.getType().equals(ActionMessageType.UPLOAD_PIC)) { // ������������ͼƬ
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
