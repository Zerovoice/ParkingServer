package com.zeroapp.parkingserver.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.utils.Config;


public class ParkingServer {
	

	public ParkingServer(){
		ServerSocket ss = null;
		try {
            ss = new ServerSocket(Config.HOST_PORT);
			System.out.println("������������ in "+new Date());
			while(true){
				Socket socket=ss.accept();
				//���ܿͻ��˷�������Ϣ
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				ClientServerMessage mm= (ClientServerMessage) ois.readObject();
				System.out.println("["+mm.toString()+"]");
				System.out.println("["+mm.getMessageContent()+"]");
				System.out.println("["+mm.getMessageType()+"]");
				ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
				mm.setMessageContent("back");
				oos.writeObject(mm);
//				oos.flush();
//				
//				User u=(User) ois.readObject();
//				
//				
//				
//				
//				ActionMessage m=new ActionMessage();
//		        if(u.getOperation().equals("login")){ //��¼
//		        	int account=u.getAccount();
//		        	System.out.println("["+u.getAccount()+"]u.getAccount()��");
//		        	System.out.println("["+u.getPassword()+"]u.getPassword()��");
//		        	UserDao udao=new UserDao();
//		        	boolean b=udao.login(account, u.getPassword());//������ݿ���֤�û�
//					if(b){
//						System.out.println("["+account+"]�����ˣ�");
//						//�����ݿ��û�״̬
//						udao.changeState(account, 1);
//						//�õ�������Ϣ
//						String user=udao.getUser(account);
//						//����һ���ɹ���½����Ϣ����������Ϣ
//						m.setType(ActionMessageType.SUCCESS);
//						m.setContent(user);
//						oos.writeObject(m);
//						ServerConClientThread cct=new ServerConClientThread(socket);//����һ���̣߳��ø��߳���ÿͻ��˱�������
//						ServerConClientManager.addClientThread(u.getAccount(),cct);
//						cct.start();//������ÿͻ���ͨ�ŵ��߳�
//						
//					}else{
//						m.setType(ActionMessageType.FAIL);
//						oos.writeObject(m);
//					}
//		        }else if(u.getOperation().equals("register")){
//		        	UserDao udao=new UserDao();
//		        	if(udao.register(u)){
//		        		//����һ��ע��ɹ�����Ϣ��
//						m.setType(ActionMessageType.SUCCESS);
//						oos.writeObject(m);
//		        	}
//		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
/*�̳߳�ʵ��
ThreadPool.submit(new Runnable() {
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
				System.out.println("ois in " + ois);
				m = (ActionMessage) ois.readObject();
				// �Դӿͻ���ȡ�õ���Ϣ���������жϣ�����Ӧ�Ĵ���
				if (m.getType().equals(ActionMessageType.COM_MES)) {// �������ͨ��Ϣ��
					DoWhatAndSendMes.sendMes(m);
				} else if (m.getType().equals(ActionMessageType.GROUP_MES)) { // �����Ⱥ��Ϣ
					DoWhatAndSendMes.sendGroupMes(m);
				} else if (m.getType().equals(ActionMessageType.GET_ONLINE_FRIENDS)) {// �������������б�
					DoWhatAndSendMes.sendBuddyList(m);
				} else if (m.getType().equals(ActionMessageType.DEL_BUDDY)) { // �����ɾ�����
					DoWhatAndSendMes.delBuddy(m);
				} else if (m.getType().equals(ActionMessageType.ADD_BUDDY)) { // �����ɾ�����
					DoWhatAndSendMes.addBuddy(m);
				} 
			} catch (Exception e) {
//				try {
//					socket.close();
//					ois.close();
//				} catch (IOException e1) {
//				}
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
});*/
