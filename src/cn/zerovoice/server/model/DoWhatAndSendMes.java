package cn.zerovoice.server.model;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import cn.zerovoice.common.ActionMessage;
import cn.zerovoice.common.ActionMessageType;
import cn.zerovoice.common.Config;
import cn.zerovoice.common.MyTime;
import cn.zerovoice.server.dao.GroupDao;
import cn.zerovoice.server.dao.UserDao;

import com.mysql.jdbc.log.Log;

public class DoWhatAndSendMes {
	static UserDao udao=new UserDao();
	static GroupDao gdao=new GroupDao();
	
	public static void sendMes(ActionMessage m){
		try{
			//取得接收人的通信线程
			ServerConClientThread scc=ServerConClientManager.getClientThread(m.getReceiver());
			System.out.println("收件人["+m.getReceiver()+"]");
			//TODO socket may be closed,do store message
			ObjectOutputStream oos=new ObjectOutputStream(scc.socket.getOutputStream());
			//向接收人发送消息
			oos.writeObject(m);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void sendBuddyList(ActionMessage m){
		try{
			//操作数据库，返回好友列表，顺带群列表
//			String res=udao.getBuddy(m.getSender())+","+gdao.getGroup();
			String res=udao.getBuddy(m.getSender());
			//发送好友列表到客户端
			ServerConClientThread scc=ServerConClientManager.getClientThread(m.getSender());
			ObjectOutputStream oos=new ObjectOutputStream(scc.socket.getOutputStream());
			ActionMessage ms=new ActionMessage();
			ms.setType(ActionMessageType.RET_ONLINE_FRIENDS);
			ms.setContent(res);
			oos.writeObject(ms);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void sendQuanList(ActionMessage m){
		try{
			//操作数据库，返回好友列表，顺带群列表
			String res=gdao.getQuans();
			//发送好友列表到客户端
			ServerConClientThread scc=ServerConClientManager.getClientThread(m.getSender());
			ObjectOutputStream oos=new ObjectOutputStream(scc.socket.getOutputStream());
			ActionMessage ms=new ActionMessage();
			ms.setType(ActionMessageType.RET_QUANS);
			ms.setContent(res);
			oos.writeObject(ms);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void delBuddy(ActionMessage m){
		try{
			if(udao.delBuddy(m.getSender(), m.getReceiver())){
				ServerConClientThread scc=ServerConClientManager.getClientThread(m.getSender());
				ObjectOutputStream oos=new ObjectOutputStream(scc.socket.getOutputStream());
				ActionMessage ms=new ActionMessage();
				ms.setType(ActionMessageType.SUCCESS);
				oos.writeObject(ms);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void sendGroupMes(ActionMessage m){
		
		try{
			List<Integer> list=gdao.getGroupMember(m.getReceiver());
			for(int raccount:list){
				//暂只支持向在线的群成员发送消息
				if(ServerConClientManager.isOnline(raccount)){
					ServerConClientThread scc=ServerConClientManager.getClientThread(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(scc.socket.getOutputStream());
					//只需改变接收者和发送者信息
					m.setSender(m.getReceiver());
					m.setReceiver(raccount);
					oos.writeObject(m);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void addBuddy(ActionMessage m) {
		try{
			if(udao.addBuddy(m.getSender(), m.getReceiver())){
				ServerConClientThread scc=ServerConClientManager.getClientThread(m.getSender());
				ObjectOutputStream oos=new ObjectOutputStream(scc.socket.getOutputStream());
				ActionMessage ms=new ActionMessage();
				ms.setType(ActionMessageType.SUCCESS);
				oos.writeObject(ms);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static boolean savePic(ActionMessage m) {
		boolean b = false;
		try {
			ServerConClientThread scc=ServerConClientManager.getClientThread(m.getSender());
			InputStream is = scc.socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			ActionMessage actionMessage=(ActionMessage) ois.readObject();
			byte[] stream = actionMessage.getStream();
			File file = byte2File(stream,m.getSender());
			if(file == null){
				b = false;
				System.out.println("fail to create file");
			}else{
				System.out.println("[file size]"+file.length()+";;;;[file path]"+file.getAbsolutePath());
				if(udao.updateAvatar(m.getSender(), file.getAbsolutePath())){
					b = udao.updateTag(m.getSender());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return b;
	}

	private static File byte2File(byte[] stream,int account) {
		BufferedOutputStream bos = null;  
        FileOutputStream fos = null;
        File file = null;
//        if(Config.mkFilePath(Config.ACTIONSERVER_HOME+"userdata\\"+account)){
//	        file = new File(Config.ACTIONSERVER_HOME+"userdata\\"+account+"\\"+MyTime.getStringTime()+".png");
        if(Config.mkFilePath(Config.ACTIONSERVER_HOME+"userdata\\")){
	        file = new File(Config.ACTIONSERVER_HOME+"userdata\\"+account+"-"+MyTime.getStringTime()+".png");   
	        try {
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);  
				bos.write(stream);
			} catch (Exception e) {
				e.printStackTrace();
			}  finally {  
	            if (bos != null) {  
	                try {  
	                    bos.close();  
	                } catch (IOException e1) {  
	                    e1.printStackTrace();  
	                }  
	            }  
	            if (fos != null) {  
	                try {  
	                    fos.close();  
	                } catch (IOException e1) {  
	                    e1.printStackTrace();  
	                }  
	            }  
	        } 
        }
		return file;
	}
}
