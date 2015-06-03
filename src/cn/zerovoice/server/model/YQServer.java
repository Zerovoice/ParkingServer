package cn.zerovoice.server.model;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.util.ReferenceCountUtil;

import com.zeroapp.parking.message.ClientServerMessage;
import com.zeroapp.utils.Log;

public class YQServer {

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);

            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder(), new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)), new MyHandler());
                    Log.d("successfully.");
                }

            });

            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture cfuture = bootstrap.bind(8080).sync();
            cfuture.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}

class MyHandler extends ChannelInboundHandlerAdapter {

    private ClientServerMessage msg = null;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // super.channelRead(ctx, msg);
        this.msg = (ClientServerMessage) msg;
        try {

            Log.d(this.msg.getMessageContent());
            // Log.d(((ByteBuf) msg).readableBytes());
            // System.out.println(((ByteBuf)msg).toString());

//            Log.d(((ByteBuf) msg).toString(io.netty.util.CharsetUtil.US_ASCII));
//            ByteBufToBytes read = new ByteBufToBytes();
////            Object obj = ByteBufToBytes.getObjectFromBytes(read.read((ByteBuf) msg));
//            ClientServerMessage obj = (ClientServerMessage) ByteBufToBytes.readMessage(msg);
//            Log.d(obj.getMessageContent());
            // ctx.write(msg);s
            // ctx.flush();

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        msg = new ClientServerMessage();
        msg.setMessageContent("back");
        ctx.writeAndFlush(msg);
    }
}

//	public YQServer(){
//		ServerSocket ss = null;
//		try {
//			ss=new ServerSocket(Config.HOST_PORT);
//			System.out.println("������������ in "+new Date());
//			while(true){
//				Socket socket=ss.accept();
//				//���ܿͻ��˷�������Ϣ
//				ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
//				ClientServerMessage mm= (ClientServerMessage) ois.readObject();
//				System.out.println("["+mm.toString()+"]");
//				System.out.println("["+mm.getMessageContent()+"]");
//				System.out.println("["+mm.getMessageType()+"]");
//				ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
//				mm.setMessageContent("back");
//				oos.writeObject(mm);
////				oos.flush();
////				
////				User u=(User) ois.readObject();
////				
////				
////				
////				
////				ActionMessage m=new ActionMessage();
////		        if(u.getOperation().equals("login")){ //��¼
////		        	int account=u.getAccount();
////		        	System.out.println("["+u.getAccount()+"]u.getAccount()��");
////		        	System.out.println("["+u.getPassword()+"]u.getPassword()��");
////		        	UserDao udao=new UserDao();
////		        	boolean b=udao.login(account, u.getPassword());//������ݿ���֤�û�
////					if(b){
////						System.out.println("["+account+"]�����ˣ�");
////						//�����ݿ��û�״̬
////						udao.changeState(account, 1);
////						//�õ�������Ϣ
////						String user=udao.getUser(account);
////						//����һ���ɹ���½����Ϣ����������Ϣ
////						m.setType(ActionMessageType.SUCCESS);
////						m.setContent(user);
////						oos.writeObject(m);
////						ServerConClientThread cct=new ServerConClientThread(socket);//����һ���̣߳��ø��߳���ÿͻ��˱�������
////						ServerConClientManager.addClientThread(u.getAccount(),cct);
////						cct.start();//������ÿͻ���ͨ�ŵ��߳�
////						
////					}else{
////						m.setType(ActionMessageType.FAIL);
////						oos.writeObject(m);
////					}
////		        }else if(u.getOperation().equals("register")){
////		        	UserDao udao=new UserDao();
////		        	if(udao.register(u)){
////		        		//����һ��ע��ɹ�����Ϣ��
////						m.setType(ActionMessageType.SUCCESS);
////						oos.writeObject(m);
////		        	}
////		        }
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
//	}

//}
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
