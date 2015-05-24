/**
 * 管理客户端连接的类
 */
package cn.zerovoice.server.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class ServerConClientManager {
	public static HashMap<Integer, ServerConClientThread> hm=new HashMap<Integer,ServerConClientThread>();
	
	//添加一个客户端通信线程
	public static void addClientThread(int account, ServerConClientThread cc){
		hm.put(account,cc);
	}
	//得到一个客户端通信线程
	public static ServerConClientThread getClientThread(int i){
		return (ServerConClientThread)hm.get(i);
	}
	//返回当前在线人的情况
	public static List<Integer> getAllOnLineUserid(){
		List<Integer> list=new ArrayList<Integer>();
		//使用迭代器完成
		Iterator<Integer> it=hm.keySet().iterator();
		while(it.hasNext()){
			list.add((int) it.next());
		}
		return list;
	}
	
	public static boolean isOnline(int a){
		//使用迭代器完成
		Iterator<Integer> it=hm.keySet().iterator();
		while(it.hasNext()){
			int account=(int) it.next();
			if(a==account){
				return true;
			}
		}
		return false;
	}
}
