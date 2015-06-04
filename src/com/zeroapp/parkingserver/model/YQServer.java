package com.zeroapp.parkingserver.model;

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
                    Log.i("successfully.");
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
	private int index = 1;
    private ClientServerMessage msg;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
        Log.i("");
		// super.channelRead(ctx, msg);
        this.msg = (ClientServerMessage) msg;

		try {
            Log.i(this.msg.getMessageContent());

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
        Log.i("");
	}
}
//
// public YQServer(){
// ServerSocket ss = null;
// try {
// //String insql="insert into user(userid,username,password) values(?,?,?)";
// String insql = "insert into states values(?,?,null)";
// ss=new ServerSocket(Config.HOST_PORT);
// System.out.println("服务器已启动 in "+new Date());
//
// java.sql.Connection conn = DBUtil.getDBUtil().getConnection();
// PreparedStatement ps = conn.prepareStatement(insql);
// ps.setInt(1,64);
// ps.setString(2, "test1");
// ps.executeUpdate();
// while(true){
// Socket socket=ss.accept();
// //接受客户端发来的信息
// ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
// ClientServerMessage mm= (ClientServerMessage) ois.readObject();
// System.out.println("["+mm.toString()+"]");
// System.out.println("["+mm.getMessageContent()+"]");
// System.out.println("["+mm.getMessageType()+"]");
//
//
//
// ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
// mm.setMessageContent("back");
// oos.writeObject(mm);
// oos.flush();
//
//
// User u=(User) ois.readObject();
//
//
//
//
// ActionMessage m=new ActionMessage();
// if(u.getOperation().equals("login")){ //登录
// int account=u.getAccount();
// System.out.println("["+u.getAccount()+"]u.getAccount()！");
// System.out.println("["+u.getPassword()+"]u.getPassword()！");
// UserDao udao=new UserDao();
// boolean b=udao.login(account, u.getPassword());//连接数据库验证用户
// if(b){
// System.out.println("["+account+"]上线了！");
// //更改数据库用户状态
// udao.changeState(account, 1);
// //得到个人信息
// String user=udao.getUser(account);
// //返回一个成功登陆的信息包，并附带个人信息
// m.setType(ActionMessageType.SUCCESS);
// m.setContent(user);
// oos.writeObject(m);
// ServerConClientThread cct=new
// ServerConClientThread(socket);//单开一个线程，让该线程与该客户端保持连接
// ServerConClientManager.addClientThread(u.getAccount(),cct);
// cct.start();//启动与该客户端通信的线程
//
// }else{
// m.setType(ActionMessageType.FAIL);
// oos.writeObject(m);
// }
// }else if(u.getOperation().equals("register")){
// UserDao udao=new UserDao();
// if(udao.register(u)){
// //返回一个注册成功的信息包
// m.setType(ActionMessageType.SUCCESS);
// oos.writeObject(m);
// }
// }
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

/*
 * 线程池实现 ThreadPool.submit(new Runnable() { public void run() { while (true) {
 * ObjectInputStream ois = null; ActionMessage m = null; try { //TODO socket
 * closed?any problem? // if(socket.isClosed()) { //
 * System.out.println("socket is closed "); // return; // } ois = new
 * ObjectInputStream(socket.getInputStream()); System.out.println("ois in " +
 * ois); m = (ActionMessage) ois.readObject(); // 对从客户端取得的消息进行类型判断，做相应的处理 if
 * (m.getType().equals(ActionMessageType.COM_MES)) {// 如果是普通消息包
 * DoWhatAndSendMes.sendMes(m); } else if
 * (m.getType().equals(ActionMessageType.GROUP_MES)) { // 如果是群消息
 * DoWhatAndSendMes.sendGroupMes(m); } else if
 * (m.getType().equals(ActionMessageType.GET_ONLINE_FRIENDS)) {// 如果是请求好友列表
 * DoWhatAndSendMes.sendBuddyList(m); } else if
 * (m.getType().equals(ActionMessageType.DEL_BUDDY)) { // 如果是删除好友
 * DoWhatAndSendMes.delBuddy(m); } else if
 * (m.getType().equals(ActionMessageType.ADD_BUDDY)) { // 如果是删除好友
 * DoWhatAndSendMes.addBuddy(m); } } catch (Exception e) { // try { //
 * socket.close(); // ois.close(); // } catch (IOException e1) { // }
 * e.printStackTrace(); } // finally { // System.out.println("1111111111"); //
 * if (null != ois) { // try { // ois.close(); //
 * System.out.println("222222222222"); // } catch (IOException e) { //
 * e.printStackTrace(); // } // } // if (null != socket) { // try { //
 * socket.close(); // System.out.println("333333333333"); // } catch
 * (IOException e) { // e.printStackTrace(); // } // } // } }
 * 
 * 
 * } });
 */
