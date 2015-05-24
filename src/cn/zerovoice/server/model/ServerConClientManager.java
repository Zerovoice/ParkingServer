/**
 * ����ͻ������ӵ���
 */
package cn.zerovoice.server.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class ServerConClientManager {
	public static HashMap<Integer, ServerConClientThread> hm=new HashMap<Integer,ServerConClientThread>();
	
	//���һ���ͻ���ͨ���߳�
	public static void addClientThread(int account, ServerConClientThread cc){
		hm.put(account,cc);
	}
	//�õ�һ���ͻ���ͨ���߳�
	public static ServerConClientThread getClientThread(int i){
		return (ServerConClientThread)hm.get(i);
	}
	//���ص�ǰ�����˵����
	public static List<Integer> getAllOnLineUserid(){
		List<Integer> list=new ArrayList<Integer>();
		//ʹ�õ��������
		Iterator<Integer> it=hm.keySet().iterator();
		while(it.hasNext()){
			list.add((int) it.next());
		}
		return list;
	}
	
	public static boolean isOnline(int a){
		//ʹ�õ��������
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
