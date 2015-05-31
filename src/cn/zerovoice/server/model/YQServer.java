package cn.zerovoice.server.model;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import com.zeroapp.parking.message.ClientServerMessage;

import cn.zerovoice.common.ActionMessage;
import cn.zerovoice.common.ActionMessageType;
import cn.zerovoice.common.User;
import cn.zerovoice.server.dao.Config;
import cn.zerovoice.server.dao.UserDao;


public class YQServer {
	

	public YQServer(){
		ServerSocket ss = null;
		try {
			ss=new ServerSocket(Config.HOST_PORT);
			System.out.println("服务器已启动 in "+new Date());
			while(true){
				Socket socket=ss.accept();
				//接受客户端发来的信息
				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
				ClientServerMessage mm= (ClientServerMessage) ois.readObject();
				System.out.println("["+mm.toString()+"]");
				System.out.println("["+mm.getMessageContent()+"]");
				System.out.println("["+mm.getMessageType()+"]");
				ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
				mm.setMessageContent("back");
				oos.writeObject(mm);
				oos.flush();
				
				
				User u=(User) ois.readObject();
				
				
				
				
				ActionMessage m=new ActionMessage();
		        if(u.getOperation().equals("login")){ //登录
		        	int account=u.getAccount();
		        	System.out.println("["+u.getAccount()+"]u.getAccount()！");
		        	System.out.println("["+u.getPassword()+"]u.getPassword()！");
		        	UserDao udao=new UserDao();
		        	boolean b=udao.login(account, u.getPassword());//连接数据库验证用户
					if(b){
						System.out.println("["+account+"]上线了！");
						//更改数据库用户状态
						udao.changeState(account, 1);
						//得到个人信息
						String user=udao.getUser(account);
						//返回一个成功登陆的信息包，并附带个人信息
						m.setType(ActionMessageType.SUCCESS);
						m.setContent(user);
						oos.writeObject(m);
						ServerConClientThread cct=new ServerConClientThread(socket);//单开一个线程，让该线程与该客户端保持连接
						ServerConClientManager.addClientThread(u.getAccount(),cct);
						cct.start();//启动与该客户端通信的线程
						
					}else{
						m.setType(ActionMessageType.FAIL);
						oos.writeObject(m);
					}
		        }else if(u.getOperation().equals("register")){
		        	UserDao udao=new UserDao();
		        	if(udao.register(u)){
		        		//返回一个注册成功的信息包
						m.setType(ActionMessageType.SUCCESS);
						oos.writeObject(m);
		        	}
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
/*线程池实现
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
				// 对从客户端取得的消息进行类型判断，做相应的处理
				if (m.getType().equals(ActionMessageType.COM_MES)) {// 如果是普通消息包
					DoWhatAndSendMes.sendMes(m);
				} else if (m.getType().equals(ActionMessageType.GROUP_MES)) { // 如果是群消息
					DoWhatAndSendMes.sendGroupMes(m);
				} else if (m.getType().equals(ActionMessageType.GET_ONLINE_FRIENDS)) {// 如果是请求好友列表
					DoWhatAndSendMes.sendBuddyList(m);
				} else if (m.getType().equals(ActionMessageType.DEL_BUDDY)) { // 如果是删除好友
					DoWhatAndSendMes.delBuddy(m);
				} else if (m.getType().equals(ActionMessageType.ADD_BUDDY)) { // 如果是删除好友
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
